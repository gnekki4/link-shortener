package ru.gnekki4.linkshortener.dto;

import jakarta.validation.Valid;
import lombok.*;
import lombok.Builder.Default;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterLinkInfoRequest {

    private String linkPart;
    private LocalDateTime endTimeFrom;
    private LocalDateTime endTimeTo;
    private String description;
    private Boolean active;

    @Valid
    @Default
    private Page page = new Page(1, 5, List.of());

}
