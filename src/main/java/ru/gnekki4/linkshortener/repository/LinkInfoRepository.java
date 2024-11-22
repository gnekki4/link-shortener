package ru.gnekki4.linkshortener.repository;

import org.springframework.stereotype.Repository;
import ru.gnekki4.linkshortener.model.LinkInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LinkInfoRepository {

    Optional<LinkInfo> findById(UUID id);

    void delete(UUID id);

    Optional<LinkInfo> findByShortLink(String shortLink);

    LinkInfo save(LinkInfo linkInfo);

    List<LinkInfo> findAll();
}
