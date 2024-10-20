package ru.gnekki4.linkshortener.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLinkInfoRequest {

    private String link;
    private Boolean active;
    private String description;
    private LocalDateTime endTime;
}
