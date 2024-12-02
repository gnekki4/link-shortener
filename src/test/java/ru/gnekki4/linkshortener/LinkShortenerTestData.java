package ru.gnekki4.linkshortener;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.gnekki4.linkshortener.dto.CreateLinkInfoRequest;
import ru.gnekki4.linkshortener.dto.UpdateLinkInfoRequest;
import ru.gnekki4.linkshortener.model.LinkInfo;
import ru.gnekki4.linkshortener.model.LinkInfoResponse;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LinkShortenerTestData {

    private static final Random random = new Random();

    public static final LinkInfo mockedLinkInfo = LinkInfo.builder()
            .description(UUID.randomUUID().toString())
            .shortLink(UUID.randomUUID().toString())
            .link(UUID.randomUUID().toString())
            .openingCount(random.nextLong())
            .active(random.nextBoolean())
            .endTime(LocalDateTime.now())
            .id(UUID.randomUUID())
            .build();

    public static final CreateLinkInfoRequest mockedCreateLinkInfoRequest = CreateLinkInfoRequest.builder()
            .description(UUID.randomUUID().toString())
            .link(UUID.randomUUID().toString())
            .endTime(LocalDateTime.now())
            .active(random.nextBoolean())
            .build();

    public static final UpdateLinkInfoRequest mockedUpdateLinkInfoRequest = UpdateLinkInfoRequest.builder()
            .description(UUID.randomUUID().toString())
            .link(UUID.randomUUID().toString())
            .endTime(LocalDateTime.now())
            .active(random.nextBoolean())
            .id(UUID.randomUUID())
            .build();

    public static final LinkInfoResponse mockedLinkInfoResponse = LinkInfoResponse.builder()
            .description(UUID.randomUUID().toString())
            .shortLink(UUID.randomUUID().toString())
            .link(UUID.randomUUID().toString())
            .openingCount(random.nextLong())
            .active(random.nextBoolean())
            .endTime(LocalDateTime.now())
            .id(UUID.randomUUID())
            .build();

}
