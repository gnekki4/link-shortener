package ru.gnekki4.linkshortener.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.gnekki4.linkshortener.dto.CommonResponse;
import ru.gnekki4.linkshortener.exception.NotFoundException;

import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final String notFoundPage;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        var bindingResult = ex.getBindingResult();
        var validationErrors = bindingResult.getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, fieldError -> fieldError.getDefaultMessage() != null
                        ? fieldError.getDefaultMessage() : ""));

        log.warn("Validation error: {}", validationErrors, ex);

        return new ResponseEntity<>(CommonResponse.builder()
                .errorMessage("Validation error")
                .validationErrors(validationErrors)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public final CommonResponse<?> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return CommonResponse.builder().body(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public final CommonResponse<?> handlerNotFoundException(NotFoundException e) {
        log.error(e.getMessage(), e);
        return CommonResponse.builder()
                .body(notFoundPage)
                .build();
    }
}
