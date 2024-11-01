package ru.gnekki4.linkshortener.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import ru.gnekki4.linkshortener.dto.CreateLinkInfoRequest;
import ru.gnekki4.linkshortener.exception.NotFoundException;
import ru.gnekki4.linkshortener.repository.impl.LinkInfoRepositoryImpl;
import ru.gnekki4.linkshortener.service.LinkInfoService;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static ru.gnekki4.linkshortener.utils.Constants.SHORT_LINK_LENGTH;

@TestInstance(Lifecycle.PER_CLASS)
class LinkInfoServiceImplTest {

    private final Random random = new Random();
    private LinkInfoService linkInfoService;

    @BeforeAll
    void setUp() {
        linkInfoService = new LinkInfoServiceImpl(new LinkInfoRepositoryImpl());
    }

    @Test
    void whenCreateShortLinkCalled_doesNotThrow() {
        final var request = CreateLinkInfoRequest.builder()
                .link(UUID.randomUUID().toString())
                .active(random.nextBoolean())
                .description(UUID.randomUUID().toString())
                .endTime(LocalDateTime.now())
                .build();

        final var shortened = linkInfoService.createLinkInfo(request);

        assertNotNull(shortened);
        assertEquals(SHORT_LINK_LENGTH, shortened.getShortLink().length());
    }

    @Test
    void whenGetByShortLinkCalled_doesNotThrow() {
        var createLinkInfoRequest = CreateLinkInfoRequest.builder()
                .link(UUID.randomUUID().toString())
                .description(UUID.randomUUID().toString())
                .endTime(LocalDateTime.now())
                .build();
        var response = linkInfoService.createLinkInfo(createLinkInfoRequest);

        assertNotNull(linkInfoService.getByShortLink(response.getShortLink()));
    }

    @Test
    void whenGetByShortLinkCalledByNonExistentShortLink_throwsNotFoundException() {
        var createLinkInfoRequest = CreateLinkInfoRequest.builder()
                .link(UUID.randomUUID().toString())
                .description(UUID.randomUUID().toString())
                .endTime(LocalDateTime.now())
                .build();
        linkInfoService.createLinkInfo(createLinkInfoRequest);

        var nonExistentShortLink = UUID.randomUUID().toString();

        assertThrows(NotFoundException.class, () -> linkInfoService.getByShortLink(nonExistentShortLink));
    }

    @Test
    void whenFindByShortLinkCalled_doesNotThrow() {
        var requestsAmount = 5;

        for (var i = 0; i < requestsAmount; i++) {
            var createLinkInfoRequest = CreateLinkInfoRequest.builder()
                    .link(UUID.randomUUID().toString())
                    .description(UUID.randomUUID().toString())
                    .endTime(LocalDateTime.now().plusDays(i))
                    .build();
            linkInfoService.createLinkInfo(createLinkInfoRequest);
        }

        var responses = linkInfoService.findByFilter();

        assertNotNull(responses);
        assertEquals(requestsAmount, responses.size());
    }
}