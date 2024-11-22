package ru.gnekki4.linkshortener.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.text.RandomStringGenerator;
import ru.gnekki4.linkshortener.dto.CreateLinkInfoRequest;
import ru.gnekki4.linkshortener.dto.UpdateLinkInfoRequest;
import ru.gnekki4.linkshortener.exception.NotFoundException;
import ru.gnekki4.linkshortener.model.LinkInfo;
import ru.gnekki4.linkshortener.model.LinkInfoResponse;
import ru.gnekki4.linkshortener.property.LinkInfoProperty;
import ru.gnekki4.linkshortener.repository.LinkInfoRepository;
import ru.gnekki4.linkshortener.service.LinkInfoService;

import java.util.List;
import java.util.UUID;

import static ru.gnekki4.linkshortener.utils.Constants.GENERATOR_SELECTION;

@RequiredArgsConstructor
public class LinkInfoServiceImpl implements LinkInfoService {

    private final LinkInfoProperty linkInfoProperty;
    private final LinkInfoRepository linkInfoRepository;

    private final RandomStringGenerator generator = new RandomStringGenerator.Builder()
            .selectFrom(GENERATOR_SELECTION.toCharArray())
            .build();

    @Override
    public LinkInfoResponse createLinkInfo(CreateLinkInfoRequest createLinkInfoRequest) {
        var shortLink = generator.generate(linkInfoProperty.getShortLinkLength());

        var linkInfo = LinkInfo.builder()
                .description(createLinkInfoRequest.getDescription())
                .endTime(createLinkInfoRequest.getEndTime())
                .active(createLinkInfoRequest.getActive())
                .link(createLinkInfoRequest.getLink())
                .shortLink(shortLink)
                .openingCount(0L)
                .build();

        return fromLinkInfo(linkInfoRepository.save(linkInfo));
    }

    @Override
    public LinkInfoResponse getByShortLink(String shortLink) {
        return linkInfoRepository.findByShortLink(shortLink)
                .map(this::fromLinkInfo)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Entity with short link %s is missing", shortLink)));
    }

    @Override
    public List<LinkInfoResponse> findByFilter() {
        return linkInfoRepository.findAll().stream()
                .map(this::fromLinkInfo)
                .toList();
    }

    @Override
    public void delete(UUID id) {
        linkInfoRepository.delete(id);
    }

    @Override
    public LinkInfoResponse updateLinkInfo(UpdateLinkInfoRequest request) {
        return linkInfoRepository.findById(request.getId())
                .map(linkInfo -> update(linkInfo, request))
                .map(linkInfoRepository::save)
                .map(this::fromLinkInfo)
                .orElseThrow(() -> new NotFoundException("LinkInfo entity not found with id: " + request.getId()));
    }

    private LinkInfoResponse fromLinkInfo(LinkInfo linkInfo) {
        return LinkInfoResponse.builder()
                .id(linkInfo.getId())
                .link(linkInfo.getLink())
                .shortLink(linkInfo.getShortLink())
                .openingCount(linkInfo.getOpeningCount())
                .active(linkInfo.getActive())
                .description(linkInfo.getDescription())
                .endTime(linkInfo.getEndTime())
                .build();
    }

    private LinkInfo update(LinkInfo linkInfo, UpdateLinkInfoRequest request) {
        if (request.getLink() != null) {
            linkInfo.setLink(request.getLink());
        }
        if (request.getDescription() != null) {
            linkInfo.setDescription(request.getDescription());
        }
        if (request.getActive() != null) {
            linkInfo.setDescription(request.getDescription());
        }

        linkInfo.setEndTime(request.getEndTime());

        return linkInfo;
    }
}
