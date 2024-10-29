package ru.gnekki4.linkshortener.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Service;
import ru.gnekki4.linkshortener.dto.CreateLinkInfoRequest;
import ru.gnekki4.linkshortener.service.LinkInfoService;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LinkServiceInfoImpl implements LinkInfoService {

    private final Map<String, CreateLinkInfoRequest> storage = new HashMap<>();
    private final RandomStringGenerator generator = new RandomStringGenerator.Builder()
            .withinRange('a', 'z')
            .build();

    @Override
    public String createShortLink(CreateLinkInfoRequest createLinkInfoRequest) {
        var shortUrl = generator.generate(10);

        storage.put(shortUrl, createLinkInfoRequest);

        return shortUrl;
    }
}
