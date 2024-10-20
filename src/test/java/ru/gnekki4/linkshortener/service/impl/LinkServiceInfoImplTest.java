package ru.gnekki4.linkshortener.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import ru.gnekki4.linkshortener.dto.CreateLinkInfoRequest;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.gnekki4.linkshortener.service.LinkInfoService.LIMIT;
import static ru.gnekki4.linkshortener.service.LinkInfoService.PREFIX;

@TestInstance(Lifecycle.PER_CLASS)
class LinkServiceInfoImplTest {

    private final Random random = new Random();
    private LinkServiceInfoImpl linkServiceInfo;

    @BeforeAll
    void setUp() {
        linkServiceInfo = new LinkServiceInfoImpl();
    }

    @Test
    void whenShortenLinkCalled_doesNotThrow() {
        final var request = CreateLinkInfoRequest.builder()
                .link(UUID.randomUUID().toString())
                .active(random.nextBoolean())
                .description(UUID.randomUUID().toString())
                .endTime(LocalDateTime.now())
                .build();

        final var shortened = linkServiceInfo.shortenLink(request);

        assertTrue(shortened.startsWith(PREFIX));
        assertEquals(LIMIT, shortened.substring(PREFIX.length()).length());
    }
}