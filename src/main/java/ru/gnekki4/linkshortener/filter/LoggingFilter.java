package ru.gnekki4.linkshortener.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static ru.gnekki4.linkshortener.util.LoggingUtils.*;

@Slf4j
@Component
public class LoggingFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        var method = request.getMethod();
        var uri = formatQueryString(request);
        var headers = formatHeaders(request);

        log.info("Request: {} {} {}", method, uri, headers);

        var responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            super.doFilter(request, responseWrapper, chain);
            var body = String.format("body=%s", new String(responseWrapper.getContentAsByteArray(),
                    StandardCharsets.UTF_8));
            log.info("Response: {} {} {} {}", method, uri, response.getStatus(), body);
        } finally {
            responseWrapper.copyBodyToResponse();
        }
    }
}
