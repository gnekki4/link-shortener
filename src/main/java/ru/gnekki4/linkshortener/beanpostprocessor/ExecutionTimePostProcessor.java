package ru.gnekki4.linkshortener.beanpostprocessor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;
import ru.gnekki4.linkshortener.annotation.LogExecutionTime;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.reflect.Proxy.newProxyInstance;
import static ru.gnekki4.linkshortener.util.Constants.LOG_TIME_FORMAT;

@Slf4j
@Component
@ConditionalOnProperty(prefix = "link-shortener.logging", value = "enable-log-execution-time", havingValue = "true")
public class ExecutionTimePostProcessor implements BeanPostProcessor {

    private final Map<String, BeanMethodsData> beanMethodDataMap = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, @NonNull String beanName) throws BeansException {
        var annotatedMethods = Arrays.stream(bean.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(LogExecutionTime.class))
                .toList();

        if (!CollectionUtils.isEmpty(annotatedMethods)) {
            beanMethodDataMap.put(beanName, new BeanMethodsData(bean.getClass(), annotatedMethods));
        }

        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        var beanMethodsData = beanMethodDataMap.get(beanName);

        if (beanMethodsData == null) {
            return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
        }

        var loader = beanMethodsData.getClazz().getClassLoader();
        var methods = beanMethodsData.getAnnotatedMethods();
        var interfaces = bean.getClass().getInterfaces();

        return newProxyInstance(loader, interfaces, (proxy, method, args) -> {
            if (methods.stream().anyMatch(annotated -> equalsSemantically(annotated, method))) {
                var watch = new StopWatch();
                try {
                    watch.start();
                    return method.invoke(bean, args);
                } catch (Exception e) {
                    throw e.getCause();
                } finally {
                    watch.stop();
                    log.info(LOG_TIME_FORMAT, method.getName(), watch.getTotalTimeMillis());
                }
            }

            try {
                return method.invoke(bean, args);
            } catch (Exception e) {
                throw e.getCause();
            }
        });
    }

    boolean equalsSemantically(Method method1, Method method2) {
        if (!method1.getName().equals(method2.getName())) {
            return false;
        }
        if (!Arrays.equals(method1.getParameterTypes(), method2.getParameterTypes())) {
            return false;
        }

        return method1.getReturnType().equals(method2.getReturnType());
    }

    @Data
    @AllArgsConstructor
    public static class BeanMethodsData {
        private Class<?> clazz;
        private List<Method> annotatedMethods;
    }

}
