package ru.gnekki4.linkshortener.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "link_info")
public class LinkInfo extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String link;

    @Column(name = "short_link")
    private String shortLink;

    @Column(name = "opening_count")
    private Long openingCount;

    private Boolean active;

    private String description;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        LinkInfo linkInfo = (LinkInfo) object;
        return Objects.equals(id, linkInfo.id)
                && Objects.equals(link, linkInfo.link)
                && Objects.equals(shortLink, linkInfo.shortLink)
                && Objects.equals(openingCount, linkInfo.openingCount)
                && Objects.equals(active, linkInfo.active)
                && Objects.equals(description, linkInfo.description)
                && Objects.equals(endTime, linkInfo.endTime);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(link);
        result = 31 * result + Objects.hashCode(shortLink);
        result = 31 * result + Objects.hashCode(openingCount);
        result = 31 * result + Objects.hashCode(active);
        result = 31 * result + Objects.hashCode(description);
        result = 31 * result + Objects.hashCode(endTime);
        return result;
    }

}
