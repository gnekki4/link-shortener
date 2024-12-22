package ru.gnekki4.linkshortener.exception;

public class NotFoundShortLinkException extends LinkShortenerException {

    public NotFoundShortLinkException(String msg) {
        super(msg);
    }
}