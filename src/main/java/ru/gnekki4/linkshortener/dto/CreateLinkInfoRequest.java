package ru.gnekki4.linkshortener.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

import static ru.gnekki4.linkshortener.util.Constants.LINK_REGEX;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLinkInfoRequest {

    @NotEmpty
    @Pattern(regexp = LINK_REGEX, message = "url is invalid")
    private String link;

    @NotNull
    private Boolean active;

    @NotEmpty
    private String description;

    @Future(message = "endTime cannot be past")
    private LocalDateTime endTime;
}
