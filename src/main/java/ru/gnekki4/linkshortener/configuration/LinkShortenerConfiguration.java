package ru.gnekki4.linkshortener.configuration;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
public class LinkShortenerConfiguration {

    @Bean
    @SneakyThrows
    public String notFoundPage() {
        return Files.readString(Path.of(ResourceUtils.getFile("classpath:templates/error/404.html").getPath()));
    }
}
