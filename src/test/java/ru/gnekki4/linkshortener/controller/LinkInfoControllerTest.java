package ru.gnekki4.linkshortener.controller;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.util.CollectionUtils;
import ru.gnekki4.linkshortener.dto.CommonRequest;
import ru.gnekki4.linkshortener.dto.FilterLinkInfoRequest;
import ru.gnekki4.linkshortener.model.LinkInfoResponse;
import ru.gnekki4.linkshortener.property.LinkInfoProperty;
import ru.gnekki4.linkshortener.repository.LinkInfoRepository;
import ru.gnekki4.linkshortener.service.LinkInfoService;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.gnekki4.linkshortener.LinkShortenerTestData.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LinkInfoControllerTest {

    private static final Random random = new Random();

    @Mock
    private LinkInfoService linkInfoService;
    @Mock
    private LinkInfoProperty linkInfoProperty;
    @Mock
    private LinkInfoRepository linkInfoRepository;

    private AutoCloseable closeable;
    private LinkInfoController linkInfoController;


    @BeforeAll
    void beforeAll() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void beforeEach() {
        linkInfoController = new LinkInfoController(linkInfoService);
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
    void getLinkInfo_doesNotThrow() {
        when(linkInfoService.findByFilter(mockedFilterLinkInfoRequest)).thenReturn(List.of());
        assertDoesNotThrow(() -> {
            var linkInfo = linkInfoController.getLinkInfo(
                    CommonRequest.<FilterLinkInfoRequest>builder()
                            .body(mockedFilterLinkInfoRequest)
                            .build());
            assertNotNull(linkInfo);

            var list = (List<LinkInfoResponse>) linkInfo.getBody();
            assertTrue(CollectionUtils.isEmpty(list));
        });

        when(linkInfoService.findByFilter(mockedFilterLinkInfoRequest)).thenReturn(List.of(mockedLinkInfoResponse));
        assertDoesNotThrow(() -> {
            var linkInfo = linkInfoController.getLinkInfo(CommonRequest.<FilterLinkInfoRequest>builder()
                    .body(mockedFilterLinkInfoRequest)
                    .build());
            assertNotNull(linkInfo);

            var list = (List<LinkInfoResponse>) linkInfo.getBody();
            assertFalse(CollectionUtils.isEmpty(list));
        });
    }

    @Test
    void createLinkInfo_doesNotThrow() {
        var request = new CommonRequest<>(mockedCreateLinkInfoRequest);

        when(linkInfoProperty.getShortLinkLength()).thenReturn(random.nextInt());
        when(linkInfoService.createLinkInfo(mockedCreateLinkInfoRequest)).thenReturn(mockedLinkInfoResponse);

        assertDoesNotThrow(() -> linkInfoController.createLinkInfo(request));

        verify(linkInfoService, times(1)).createLinkInfo(mockedCreateLinkInfoRequest);
    }

    @Test
    void updateLinkInfo_doesNotThrow() {
        var request = new CommonRequest<>(mockedUpdateLinkInfoRequest);

        when(linkInfoProperty.getShortLinkLength()).thenReturn(random.nextInt());
        when(linkInfoService.updateLinkInfo(mockedUpdateLinkInfoRequest)).thenReturn(mockedLinkInfoResponse);

        assertDoesNotThrow(() -> linkInfoController.updateLinkInfo(request));

        verify(linkInfoService, times(1)).updateLinkInfo(mockedUpdateLinkInfoRequest);
    }

    @Test
    void deleteLinkInfo_doesNotThrow() {
        var entityId = UUID.randomUUID();

        when(linkInfoProperty.getShortLinkLength()).thenReturn(random.nextInt());

        assertDoesNotThrow(() -> linkInfoController.deleteLinkInfo(entityId));

        verify(linkInfoService, times(1)).delete(entityId);
    }
}