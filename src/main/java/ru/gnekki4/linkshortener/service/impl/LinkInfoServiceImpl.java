package ru.gnekki4.linkshortener.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import ru.gnekki4.linkshortener.dto.CreateLinkInfoRequest;
import ru.gnekki4.linkshortener.dto.FilterLinkInfoRequest;
import ru.gnekki4.linkshortener.dto.Page;
import ru.gnekki4.linkshortener.dto.UpdateLinkInfoRequest;
import ru.gnekki4.linkshortener.exception.NotFoundException;
import ru.gnekki4.linkshortener.exception.NotFoundShortLinkException;
import ru.gnekki4.linkshortener.mapper.LinkInfoMapper;
import ru.gnekki4.linkshortener.model.LinkInfo;
import ru.gnekki4.linkshortener.model.LinkInfoResponse;
import ru.gnekki4.linkshortener.property.LinkInfoProperty;
import ru.gnekki4.linkshortener.repository.LinkInfoRepository;
import ru.gnekki4.linkshortener.service.LinkInfoService;
import ru.gnekki4.loggingstarter.annotation.LogExecutionTime;

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
        var linkInfo = linkInfoMapper.fromCreateRequest(createLinkInfoRequest, shortLink);

        return linkInfoMapper.toResponse(linkInfoRepository.save(linkInfo));
    }

    @Override
    @LogExecutionTime
    public LinkInfoResponse getByShortLink(String shortLink) {
        var linkInfo = linkInfoRepository.findActiveShortLink(shortLink, LocalDateTime.now())
                .orElseThrow(() -> new NotFoundShortLinkException(String.format("Entity with short link %s is missing",
                        shortLink)));

        linkInfoRepository.incrementOpeningCount(shortLink);

        return linkInfoMapper.toResponse(linkInfo);
    }

    @LogExecutionTime
    public List<LinkInfoResponse> findByFilter(FilterLinkInfoRequest filterLinkInfoRequest) {
        return linkInfoRepository.findByFilter(
                        filterLinkInfoRequest.getLinkPart(),
                        filterLinkInfoRequest.getEndTimeFrom(),
                        filterLinkInfoRequest.getEndTimeTo(),
                        filterLinkInfoRequest.getDescription(),
                        filterLinkInfoRequest.getActive(),
                        getPageable(filterLinkInfoRequest.getPage())
                ).stream()
                .map(linkInfoMapper::toResponse)
                .toList();
    }

    @Override
    @LogExecutionTime
    public void delete(UUID id) {
        linkInfoRepository.deleteById(id);
    }

    @Override
    @LogExecutionTime
    public LinkInfoResponse updateLinkInfo(UpdateLinkInfoRequest request) {
        var linkInfo = linkInfoRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException("LinkInfo entity not found with id: " + request.getId()));
        update(linkInfo, request);
        linkInfo = linkInfoRepository.save(linkInfo);

        return linkInfoMapper.toResponse(linkInfo);
    }

    private Pageable getPageable(Page page) {
        var sorting = page.getSorts().stream()
                .map(s -> new Order(Direction.valueOf(s.getDirection()), s.getField()))
                .toList();

        return PageRequest.of(page.getNumber() - 1, page.getSize(), Sort.by(sorting));

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
