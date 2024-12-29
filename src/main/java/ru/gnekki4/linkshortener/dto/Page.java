package ru.gnekki4.linkshortener.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.Builder.Default;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Page {

    @NotNull
    @Positive
    private Integer number;

    @NotNull
    @Positive
    private Integer size;

    @Valid
    @Default
    private List<Sort> sorts = new ArrayList<>();

}
