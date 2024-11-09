package ru.gnekki4.linkshortener.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Service;
import ru.gnekki4.linkshortener.dto.CreateLinkInfoRequest;
import ru.gnekki4.linkshortener.exception.NotFoundException;
import ru.gnekki4.linkshortener.model.LinkInfo;
import ru.gnekki4.linkshortener.model.LinkInfoResponse;
import ru.gnekki4.linkshortener.repository.LinkInfoRepository;
import ru.gnekki4.linkshortener.service.LinkInfoService;

import java.util.List;

import static ru.gnekki4.linkshortener.utils.Constants.GENERATOR_SELECTION;
import static ru.gnekki4.linkshortener.utils.Constants.SHORT_LINK_LENGTH;

@Service
@RequiredArgsConstructor
public class LinkInfoServiceImpl implements LinkInfoService {

    private final LinkInfoRepository linkInfoRepository;

    private final RandomStringGenerator generator = new RandomStringGenerator.Builder()
            .selectFrom(GENERATOR_SELECTION.toCharArray())
            .build();

    @Override
    public LinkInfoResponse createLinkInfo(CreateLinkInfoRequest createLinkInfoRequest) {
        var shortLink = generator.generate(SHORT_LINK_LENGTH);

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
}
