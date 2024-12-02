package ru.gnekki4.linkshortener.controller;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import ru.gnekki4.linkshortener.exception.NotFoundException;
import ru.gnekki4.linkshortener.property.LinkInfoProperty;
import ru.gnekki4.linkshortener.repository.LinkInfoRepository;
import ru.gnekki4.linkshortener.service.LinkInfoService;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static ru.gnekki4.linkshortener.LinkShortenerTestData.mockedLinkInfo;
import static ru.gnekki4.linkshortener.LinkShortenerTestData.mockedLinkInfoResponse;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShortLinkControllerTest {

    @Mock
    private LinkInfoService linkInfoService;
    @Mock
    private LinkInfoProperty linkInfoProperty;
    @Mock
    private LinkInfoRepository linkInfoRepository;

    private AutoCloseable closeable;
    private ShortLinkController shortLinkController;


    @BeforeAll
    void beforeAll() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void beforeEach() {
        shortLinkController = new ShortLinkController(linkInfoService);
    }

    @AfterEach
    void afterEach() {
        Mockito.reset(linkInfoService, linkInfoProperty, linkInfoRepository);
    }

    @AfterAll
    void afterAll() throws Exception {
        closeable.close();
    }

    @Test
    void getByShortLink_found() {
        var shortLink = UUID.randomUUID().toString();
        when(linkInfoService.getByShortLink(shortLink)).thenReturn(mockedLinkInfoResponse);
        when(linkInfoRepository.findByShortLink(shortLink)).thenReturn(Optional.of(mockedLinkInfo));

        assertDoesNotThrow(() -> {
            var entity = shortLinkController.getByShortLink(shortLink);
            assertEquals(HttpStatus.TEMPORARY_REDIRECT, entity.getStatusCode());
            assertEquals(mockedLinkInfoResponse.getLink(), entity.getHeaders().getFirst(HttpHeaders.LOCATION));
        });

        verify(linkInfoService, times(1)).getByShortLink(shortLink);
    }

    @Test
    void getByShortLink_notFound() {
        var shortLink = UUID.randomUUID().toString();
        when(linkInfoRepository.findByShortLink(shortLink)).thenReturn(Optional.empty());
        when(linkInfoService.getByShortLink(shortLink)).thenThrow(new NotFoundException(""));

        assertDoesNotThrow(() -> {
            var entity = shortLinkController.getByShortLink(shortLink);
            assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
        });

        verify(linkInfoService, times(1)).getByShortLink(shortLink);
    }
}