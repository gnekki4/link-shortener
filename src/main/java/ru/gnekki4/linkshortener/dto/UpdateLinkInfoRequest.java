package ru.gnekki4.linkshortener.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class UpdateLinkInfoRequest {

    private UUID id;
    private String link;
    private LocalDateTime endTime;
    private String description;
    private Boolean active;

}
