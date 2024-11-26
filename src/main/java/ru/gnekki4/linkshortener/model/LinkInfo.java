package ru.gnekki4.linkshortener.model;

import lombok.*;
import ru.gnekki4.linkshortener.dto.UpdateLinkInfoRequest;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class LinkInfo {

    private UUID id;
    private String link;
    private String shortLink;
    private Long openingCount;
    private Boolean active;
    private String description;
    private LocalDateTime endTime;

}
