package ru.gnekki4.linkshortener.exception;

public class LinkShortenerException extends RuntimeException {

    public LinkShortenerException(String message) {
        super(message);
    }

    public LinkShortenerException(String message, Exception e) {
        super(message, e);
    }
}
