package ru.gnekki4.linkshortener.service;

import ru.gnekki4.linkshortener.dto.CreateLinkInfoRequest;

public interface LinkInfoService {

    String createShortLink(CreateLinkInfoRequest createLinkInfoRequest);
}
