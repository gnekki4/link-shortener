package ru.gnekki4.linkshortener.controller;

import jakarta.validation.Valid;
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

        return CommonResponse.<List<LinkInfoResponse>>builder()
                .id(UUID.randomUUID())
                .body(linkInfoResponses)
                .build();
    }

    @PostMapping
    public CommonResponse<LinkInfoResponse> createLinkInfo(
            @Valid @RequestBody CommonRequest<CreateLinkInfoRequest> request) {
        var linkInfoResponse = linkInfoService.createLinkInfo(request.getBody());

        return CommonResponse.<LinkInfoResponse>builder()
                .id(UUID.randomUUID())
                .body(linkInfoResponse)
                .build();
    }

    @PatchMapping
    public CommonResponse<LinkInfoResponse> updateLinkInfo(
            @Valid @RequestBody CommonRequest<UpdateLinkInfoRequest> request) {
        var linkInfoResponse = linkInfoService.updateLinkInfo(request.getBody());

        return CommonResponse.<LinkInfoResponse>builder()
                .id(UUID.randomUUID())
                .body(linkInfoResponse)
                .build();
    }

    @DeleteMapping("/{id}")
    public CommonResponse<Object> deleteLinkInfo(@PathVariable UUID id) {
        linkInfoService.delete(id);

        return CommonResponse.builder()
                .id(UUID.randomUUID())
                .build();
    }
}
