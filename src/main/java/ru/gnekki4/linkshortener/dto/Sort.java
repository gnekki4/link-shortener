package ru.gnekki4.linkshortener.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.Builder.Default;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sort {

    @NotEmpty
    private String field;

    @Pattern(regexp = "ASC|DESC", message = "Must be ASC or DESC")
    @Default
    private String direction = "ASC";

}
