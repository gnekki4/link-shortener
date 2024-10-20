package ru.gnekki4.linkshortener.service;

import ru.gnekki4.linkshortener.dto.CreateLinkInfoRequest;

public interface LinkInfoService {

    Integer LIMIT = 10;
    String PREFIX = "https://shortener/";

    /**
     * Сокращает ссылку {@link CreateLinkInfoRequest#getLink()} до {@link this#LIMIT} символов
     */
    String shortenLink(CreateLinkInfoRequest createLinkInfoRequest);
}
