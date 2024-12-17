package ru.gnekki4.linkshortener.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.gnekki4.linkshortener.dto.CommonRequest;
import ru.gnekki4.linkshortener.dto.CommonResponse;
import ru.gnekki4.linkshortener.dto.CreateLinkInfoRequest;
import ru.gnekki4.linkshortener.dto.UpdateLinkInfoRequest;
import ru.gnekki4.linkshortener.model.LinkInfoResponse;
import ru.gnekki4.linkshortener.service.LinkInfoService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/link-info")
public class LinkInfoController {

    private final LinkInfoService linkInfoService;

    @GetMapping
    public CommonResponse<List<LinkInfoResponse>> getLinkInfo() {
        var linkInfoResponses = linkInfoService.findByFilter();
        log.info("Successfully retrieved linkInfoResponses: {}", linkInfoResponses);

        return new CommonResponse<>(UUID.randomUUID(), linkInfoResponses);
    }

    @PostMapping
    public CommonResponse<LinkInfoResponse> createLinkInfo(@RequestBody CommonRequest<CreateLinkInfoRequest> request) {
        var linkInfoResponse = linkInfoService.createLinkInfo(request.getBody());
        log.info("Successfully created linkInfo: {}", linkInfoResponse);

        return new CommonResponse<>(UUID.randomUUID(), linkInfoResponse);
    }

    @PatchMapping
    public CommonResponse<LinkInfoResponse> updateLinkInfo(@RequestBody CommonRequest<UpdateLinkInfoRequest> request) {
        var linkInfoResponse = linkInfoService.updateLinkInfo(request.getBody());
        log.info("Successfully updated linkInfo: {}", linkInfoResponse);

        return new CommonResponse<>(UUID.randomUUID(), linkInfoResponse);
    }

    @DeleteMapping("/{id}")
    public CommonResponse<Object> deleteLinkInfo(@PathVariable UUID id) {
        linkInfoService.delete(id);
        log.info("Successfully deleted linkInfo with id: {}", id);

        return CommonResponse.builder().id(UUID.randomUUID()).build();
    }
}
