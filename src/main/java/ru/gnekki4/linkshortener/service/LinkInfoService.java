package ru.gnekki4.linkshortener.service;

import ru.gnekki4.linkshortener.dto.CreateLinkInfoRequest;
import ru.gnekki4.linkshortener.model.LinkInfoResponse;

import java.util.List;

public interface LinkInfoService {

    LinkInfoResponse createLinkInfo(CreateLinkInfoRequest createLinkInfoRequest);

    LinkInfoResponse getByShortLink(String shortLink);

    List<LinkInfoResponse> findByFilter();
}
