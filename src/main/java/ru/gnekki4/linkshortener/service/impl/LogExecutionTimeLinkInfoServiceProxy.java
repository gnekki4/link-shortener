package ru.gnekki4.linkshortener.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import ru.gnekki4.linkshortener.dto.CreateLinkInfoRequest;
import ru.gnekki4.linkshortener.dto.UpdateLinkInfoRequest;
import ru.gnekki4.linkshortener.model.LinkInfoResponse;
import ru.gnekki4.linkshortener.service.LinkInfoService;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
public class LogExecutionTimeLinkInfoServiceProxy implements LinkInfoService {

    private static final String LOG_FORMAT = "Время выполнения метода {}: {}";

    private final LinkInfoService linkInfoService;

    @Override
    public LinkInfoResponse createLinkInfo(CreateLinkInfoRequest request) {
        var watch = new StopWatch();

        try {
            watch.start();
            return linkInfoService.createLinkInfo(request);
        } finally {
            watch.stop();
            log.info(LOG_FORMAT, "createLinkInfo", watch.getTotalTimeMillis());
        }
    }

    @Override
    public LinkInfoResponse getByShortLink(String shortLink) {
        var watch = new StopWatch();
        try {
            watch.start();
            return linkInfoService.getByShortLink(shortLink);
        } finally {
            watch.stop();
            log.info(LOG_FORMAT, "getByShortLink", watch.getTotalTimeMillis());
        }
    }

    @Override
    public List<LinkInfoResponse> findByFilter() {
        var watch = new StopWatch();

        try {
            watch.start();
            return linkInfoService.findByFilter();
        } finally {
            watch.stop();
            log.info(LOG_FORMAT, "findByFilter", watch.getTotalTimeMillis());
        }
    }

    @Override
    public void delete(UUID id) {
        var watch = new StopWatch();

        try {
            watch.start();
            linkInfoService.delete(id);
        } finally {
            watch.stop();
            log.info(LOG_FORMAT, "delete", watch.getTotalTimeMillis());
        }
    }

    @Override
    public LinkInfoResponse updateLinkInfo(UpdateLinkInfoRequest request) {
        var watch = new StopWatch();

        try {
            watch.start();
            return linkInfoService.updateLinkInfo(request);
        } finally {
            watch.stop();
            log.info(LOG_FORMAT, "updateLinkInfo", watch.getTotalTimeMillis());
        }
    }
}
