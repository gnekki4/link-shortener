package ru.gnekki4.linkshortener.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkInfoResponse {

    private UUID id;
    private String link;
    private String shortLink;
    private Long openingCount;
    private Boolean active;
    private String description;
    private LocalDateTime endTime;

    public LinkInfoResponse(LinkInfo linkInfo) {
        this.id = linkInfo.getId();
        this.link = linkInfo.getLink();
        this.shortLink = linkInfo.getShortLink();
        this.openingCount = linkInfo.getOpeningCount();
        this.active = linkInfo.getActive();
        this.description = linkInfo.getDescription();
        this.endTime = linkInfo.getEndTime();
    }
}
