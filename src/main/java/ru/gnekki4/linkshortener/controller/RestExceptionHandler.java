package ru.gnekki4.linkshortener.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.gnekki4.linkshortener.dto.CommonResponse;

@Slf4j
@AllArgsConstructor
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public final CommonResponse<?> handle(Exception e) {
        log.error(e.getMessage(), e);
        return CommonResponse.builder().body(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).build();
    }
}
