package ru.gnekki4.linkshortener.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoggingUtils {

    public static String formatQueryString(HttpServletRequest request) {
        return Optional.ofNullable(request.getQueryString())
                .map(queryString -> String.format("%s?%s", request.getRequestURI(), queryString))
                .orElse("");
    }

    public static String formatHeaders(HttpServletRequest request) {
        return Collections.list(request.getHeaderNames()).stream()
                .map(header -> String.format("%s=%s", header, request.getHeader(header)))
                .collect(Collectors.joining(","));
    }
}
