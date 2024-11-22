package ru.gnekki4.linkshortener.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String UPPER_CASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    public static final String DIGITS = "0123456789";
    public static final String GENERATOR_SELECTION = UPPER_CASE_LETTERS + LOWER_CASE_LETTERS + DIGITS;

}
