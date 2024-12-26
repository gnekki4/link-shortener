package ru.gnekki4.linkshortener.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gnekki4.linkshortener.model.LinkInfo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LinkInfoRepository extends JpaRepository<LinkInfo, UUID> {

    @Query("""
            FROM LinkInfo
            WHERE shortLink = :shortLink
            AND active = true
            AND (endTime IS NULL OR endTime >= :endTime)
            """)
    Optional<LinkInfo> findActiveShortLink(String shortLink, LocalDateTime endTime);

    @Query(value = """
            FROM LinkInfo
            WHERE (:linkPart IS NULL OR lower(link) LIKE '%' || lower(cast(:linkPart AS String)) || '%')
              AND (cast(:endTimeFrom AS DATE) IS NULL OR endTime >= :endTimeFrom)
              AND (cast(:endTimeTo AS DATE) IS NULL OR endTime <= :endTimeTo)
              AND (:descriptionPart IS NULL OR lower(description) LIKE '%' || lower(cast(:descriptionPart AS String)) || '%')
              AND (:active IS NULL OR active = :active)
            """)
    List<LinkInfo> findByFilter(String linkPart,
                                LocalDateTime endTimeFrom,
                                LocalDateTime endTimeTo,
                                String descriptionPart,
                                Boolean active);

    @Query("""
            UPDATE LinkInfo
            SET openingCount = openingCount + 1
            WHERE shortLink = :shortLink
            """)
    @Modifying
    @Transactional
    void incrementOpeningCount(String shortLink);

}
