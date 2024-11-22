package ru.gnekki4.linkshortener.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gnekki4.linkshortener.property.LinkInfoProperty;
import ru.gnekki4.linkshortener.repository.LinkInfoRepository;
import ru.gnekki4.linkshortener.service.LinkInfoService;
import ru.gnekki4.linkshortener.service.impl.LinkInfoServiceImpl;
import ru.gnekki4.linkshortener.service.impl.LogExecutionTimeLinkInfoServiceProxy;

@Configuration
public class LinkShortenerConfiguration {

    @Bean
    public LinkInfoService linkInfoService(LinkInfoProperty linkInfoProperty, LinkInfoRepository linkInfoRepository) {
        var linkInfoService = new LinkInfoServiceImpl(linkInfoProperty, linkInfoRepository);

        return new LogExecutionTimeLinkInfoServiceProxy(linkInfoService);
    }
}
