package ru.gnekki4.linkshortener.service;

import ru.gnekki4.linkshortener.dto.CreateLinkInfoRequest;
import ru.gnekki4.linkshortener.dto.FilterLinkInfoRequest;
import ru.gnekki4.linkshortener.dto.UpdateLinkInfoRequest;
import ru.gnekki4.linkshortener.model.LinkInfoResponse;

import java.util.List;
import java.util.UUID;

public interface LinkInfoService {

    LinkInfoResponse createLinkInfo(CreateLinkInfoRequest createLinkInfoRequest);

    LinkInfoResponse getByShortLink(String shortLink);

    List<LinkInfoResponse> findByFilter(FilterLinkInfoRequest filterLinkInfoRequest);

    void delete(UUID id);

    LinkInfoResponse updateLinkInfo(UpdateLinkInfoRequest request);
}
