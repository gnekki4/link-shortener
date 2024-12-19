package ru.gnekki4.linkshortener.property;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Component
@Validated
@ConfigurationProperties("link-shortener")
public class LinkInfoProperty {

    @Min(value = 8, message = "shortLinkLength property must not be less than 8!")
    private Integer shortLinkLength;

}
