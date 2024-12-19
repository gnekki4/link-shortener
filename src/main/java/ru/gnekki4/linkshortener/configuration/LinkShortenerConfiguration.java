package ru.gnekki4.linkshortener.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
public class LinkShortenerConfiguration {

    @Bean
    public String notFoundPage() throws IOException {
        return Files.readString(Path.of(ResourceUtils.getFile("classpath:templates/error/404.html").getPath()));
    }
}
