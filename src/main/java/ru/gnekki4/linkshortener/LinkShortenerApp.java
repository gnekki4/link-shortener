package ru.gnekki4.linkshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static ru.gnekki4.loggingstarter.LoggingStarterAutoConfiguration.println;

@SpringBootApplication
public class LinkShortenerApp {

    public static void main(String[] args) {
        SpringApplication.run(LinkShortenerApp.class, args);
        println("Hello world!");
    }
}