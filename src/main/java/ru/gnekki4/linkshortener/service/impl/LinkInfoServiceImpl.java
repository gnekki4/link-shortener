package ru.gnekki4.linkshortener.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Service;
import ru.gnekki4.linkshortener.annotation.LogExecutionTime;
import ru.gnekki4.linkshortener.dto.CreateLinkInfoRequest;
import ru.gnekki4.linkshortener.dto.UpdateLinkInfoRequest;
import ru.gnekki4.linkshortener.exception.NotFoundException;
import ru.gnekki4.linkshortener.mapper.LinkInfoMapper;
import ru.gnekki4.linkshortener.model.LinkInfo;
import ru.gnekki4.linkshortener.model.LinkInfoResponse;
import ru.gnekki4.linkshortener.property.LinkInfoProperty;
import ru.gnekki4.linkshortener.repository.LinkInfoRepository;
import ru.gnekki4.linkshortener.service.LinkInfoService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static ru.gnekki4.linkshortener.util.Constants.GENERATOR_SELECTION;

@Service
@RequiredArgsConstructor
public class LinkInfoServiceImpl implements LinkInfoService {

    private final LinkInfoMapper linkInfoMapper;
    private final LinkInfoProperty linkInfoProperty;
    private final LinkInfoRepository linkInfoRepository;

    private final RandomStringGenerator generator = new RandomStringGenerator.Builder()
            .selectFrom(GENERATOR_SELECTION.toCharArray())
            .build();

    @Override
    @LogExecutionTime
    public LinkInfoResponse createLinkInfo(CreateLinkInfoRequest createLinkInfoRequest) {
        var shortLink = generator.generate(linkInfoProperty.getShortLinkLength());
        var linkInfo = linkInfoMapper.fromCreateRequest(createLinkInfoRequest, shortLink); // упс

        return linkInfoMapper.toResponse(linkInfoRepository.save(linkInfo));
    }

    @Override
    @LogExecutionTime
    public LinkInfoResponse getByShortLink(String shortLink) {
        return linkInfoRepository.findActiveByShortLink(shortLink, LocalDateTime.now())
                .map(linkInfoMapper::toResponse)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Entity with short link %s is missing", shortLink)));
    }

    @Override
    @LogExecutionTime
    public List<LinkInfoResponse> findByFilter() {
        return linkInfoRepository.findAll().stream()
                .map(linkInfoMapper::toResponse)
                .toList();
    }

    @Override
    @LogExecutionTime
    public void delete(UUID id) {
        linkInfoRepository.delete(id);
    }

    @Override
    @LogExecutionTime
    public LinkInfoResponse updateLinkInfo(UpdateLinkInfoRequest request) {
        return linkInfoRepository.findById(request.getId())
                .map(linkInfo -> update(linkInfo, request))
                .map(linkInfoRepository::save)
                .map(linkInfoMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("LinkInfo entity not found with id: " + request.getId()));
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
