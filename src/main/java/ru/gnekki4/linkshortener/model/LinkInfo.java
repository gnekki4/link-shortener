package ru.gnekki4.linkshortener.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "link_info")
@EqualsAndHashCode(of = "id")
public class LinkInfo extends AuditableEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "link")
    private String link;

    @Column(name = "short_link")
    private String shortLink;

    @Column(name = "opening_count")
    private Long openingCount;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "description")
    private String description;

    @Column(name = "end_time")
    private LocalDateTime endTime;

}
