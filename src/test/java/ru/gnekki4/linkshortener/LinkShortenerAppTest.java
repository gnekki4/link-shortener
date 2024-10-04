package ru.gnekki4.linkshortener;


import org.junit.jupiter.api.Test;

class LinkShortenerAppTest {

  @Test
  void test() {
    byte b = 1; // -128 +127 - 1 байт
    short s = 1; // -32000 +32000 - 2 байта
    int i = 1; // -2147000000 +2147000000 - 4 байта
    long l = 1; // 8 байт
    float f = .0f; // 4 байта
    double d = .0; // 8 байт
    char c = 'c'; // 0 +64000 2 байта
    boolean bool = true; // true / false
  }
}