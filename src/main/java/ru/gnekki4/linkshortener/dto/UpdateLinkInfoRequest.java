package ru.gnekki4.linkshortener.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import ru.gnekki4.linkshortener.validation.UUIDFormat;

import java.time.LocalDateTime;
import java.util.UUID;

import static ru.gnekki4.linkshortener.util.Constants.LINK_REGEX;

@Data
@Builder
public class UpdateLinkInfoRequest {

    @NotEmpty
    @UUIDFormat
    private UUID id;

    @Pattern(regexp = LINK_REGEX, message = "url is invalid")
    private String link;

    @Future(message = "endTime can not be past")
    private LocalDateTime endTime;

    private String description;

    private Boolean active;

}
