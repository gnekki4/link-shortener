package ru.gnekki4.linkshortener.repository.impl;

import org.springframework.stereotype.Repository;
import ru.gnekki4.linkshortener.model.LinkInfo;
import ru.gnekki4.linkshortener.repository.LinkInfoRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class LinkInfoRepositoryImpl implements LinkInfoRepository {

    private final Map<String, LinkInfo> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<LinkInfo> findByShortLink(String shortLink) {
        return Optional.ofNullable(storage.get(shortLink));
    }

    @Override
    public Optional<LinkInfo> findActiveByShortLink(String shortLink, LocalDateTime localDateTime) {
        return Optional.ofNullable(storage.get(shortLink))
                .filter(linkInfo -> isLinkInfoActive(linkInfo, localDateTime));
    }

    @Override
    public LinkInfo save(LinkInfo linkInfo) {
        linkInfo.setId(UUID.randomUUID());
        storage.put(linkInfo.getShortLink(), linkInfo);

        return linkInfo;
    }

    @Override
    public Optional<LinkInfo> findById(UUID id) {
        return findAll().stream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    @Override
    public void delete(UUID id) {
        findById(id).ifPresent(linkInfo -> storage.remove(linkInfo.getShortLink()));
    }

    @Override
    public List<LinkInfo> findAll() {
        return new ArrayList<>(storage.values());
    }

    private boolean isLinkInfoActive(LinkInfo linkInfo, LocalDateTime localDateTime) {
        return Boolean.TRUE.equals(linkInfo.getActive()) && localDateTime.isBefore(linkInfo.getEndTime());
    }
}
