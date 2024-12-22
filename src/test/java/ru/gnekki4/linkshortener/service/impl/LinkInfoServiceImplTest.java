package ru.gnekki4.linkshortener.service.impl;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.gnekki4.linkshortener.dto.CreateLinkInfoRequest;
import ru.gnekki4.linkshortener.dto.UpdateLinkInfoRequest;
import ru.gnekki4.linkshortener.exception.NotFoundException;
import ru.gnekki4.linkshortener.service.LinkInfoService;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static ru.gnekki4.linkshortener.LinkShortenerTestData.mockedFilterLinkInfoRequest;

@SpringBootTest
@AllArgsConstructor
@TestInstance(Lifecycle.PER_METHOD)
class LinkInfoServiceImplTest {

    private final Random random = new Random();

    @Autowired
    private LinkInfoService linkInfoService;

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
        assertEquals(8, shortened.getShortLink().length());
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

        var responses = linkInfoService.findByFilter(mockedFilterLinkInfoRequest);

        assertNotNull(responses);
        assertEquals(requestsAmount, responses.size());
    }

    @Test
    void whenDeleteCalled_doesNotThrow() {
        var requestsAmount = 5;

        for (var i = 0; i < requestsAmount; i++) {
            var createLinkInfoRequest = CreateLinkInfoRequest.builder()
                    .link(UUID.randomUUID().toString())
                    .description(UUID.randomUUID().toString())
                    .endTime(LocalDateTime.now().plusDays(i))
                    .build();
            var response = linkInfoService.createLinkInfo(createLinkInfoRequest);
            assertNotNull(linkInfoService.getByShortLink(response.getShortLink()));
            assertDoesNotThrow(() -> linkInfoService.delete(response.getId()));
        }
    }

    @Test
    void whenUpdateCalled_doesNotThrow() {
        var request = CreateLinkInfoRequest.builder()
                .link(UUID.randomUUID().toString())
                .description(UUID.randomUUID().toString())
                .endTime(LocalDateTime.now())
                .build();

        var response = linkInfoService.createLinkInfo(request);
        assertEquals(1, linkInfoService.findByFilter(mockedFilterLinkInfoRequest).size());

        var updateRequest = UpdateLinkInfoRequest.builder()
                .id(response.getId())
                .link("UPDATED " + response.getLink())
                .endTime(response.getEndTime().plusDays(1))
                .description("UPDATED " + response.getDescription())
                .active(!response.getActive())
                .build();

        var updated = linkInfoService.updateLinkInfo(updateRequest);

        assertTrue(updated.getLink().startsWith("UPDATED"));
        assertTrue(updated.getDescription().startsWith("UPDATED"));
        assertEquals(updated.getEndTime(), response.getEndTime().plusDays(1));
        assertEquals(updated.getActive(), !response.getActive());
    }
}