package ru.gnekki4.linkshortener.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import ru.gnekki4.linkshortener.dto.CreateLinkInfoRequest;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(Lifecycle.PER_CLASS)
class LinkServiceInfoImplTest {

    private final Random random = new Random();
    private LinkServiceInfoImpl linkServiceInfo;

    @BeforeAll
    void setUp() {
        linkServiceInfo = new LinkServiceInfoImpl();
    }

    @Test
    void whenCreateShortLinkCalled_doesNotThrow() {
        final var request = CreateLinkInfoRequest.builder()
                .link(UUID.randomUUID().toString())
                .active(random.nextBoolean())
                .description(UUID.randomUUID().toString())
                .endTime(LocalDateTime.now())
                .build();

        final var shortened = linkServiceInfo.createShortLink(request);

        assertNotNull(shortened);
    }
}