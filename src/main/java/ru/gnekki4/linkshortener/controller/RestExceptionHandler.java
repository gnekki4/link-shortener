package ru.gnekki4.linkshortener.controller;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.gnekki4.linkshortener.dto.CommonResponse;
import ru.gnekki4.linkshortener.dto.ValidationError;
import ru.gnekki4.linkshortener.exception.NotFoundException;

import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestControllerAdvice
public class RestExceptionHandler {

    private final String notFoundPage;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse<?> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        var bindingResult = e.getBindingResult();
        var validationErrors = bindingResult.getFieldErrors().stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        log.warn("Validation error: {}", validationErrors, e);

        return CommonResponse.builder()
                .id(UUID.randomUUID())
                .errorMessage("Validation error")
                .validationErrors(validationErrors)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public CommonResponse<?> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        if (e.getCause() instanceof InvalidFormatException invalidFormatException) {
            var path = invalidFormatException.getPath().stream()
                    .map(Reference::getFieldName)
                    .collect(Collectors.joining("."));

            var errorMessage = String.format("Validation error, invalid field format: %s", path);
            log.error(errorMessage, e);

            return CommonResponse.builder()
                    .id(UUID.randomUUID())
                    .errorMessage(errorMessage)
                    .build();
        }

        return handleException(e);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public final CommonResponse<?> handleException(Exception e) {
        log.error(e.getMessage(), e);

        return CommonResponse.builder()
                .id(UUID.randomUUID())
                .body(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<?> handlerNotFoundException(NotFoundException e) {
        log.error(e.getMessage(), e);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.TEXT_HTML)
                .body(notFoundPage);
    }
}
