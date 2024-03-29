package com.suyh0103.valuesource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 这就是将指定方法的返回值，按顺序作为参数传入
 */
public class MethodSourceTest {

    @ParameterizedTest(name = "#{index} - Test with String : {0}")
    @MethodSource("stringProvider")
    void test_method_string(String arg) {
        Assertions.assertNotNull(arg);
    }

    // this need static
    static Stream<String> stringProvider() {
        return Stream.of("java", "rust");
    }

    @ParameterizedTest(name = "#{index} - Test with Int : {0}")
    @MethodSource("rangeProvider")
    void test_method_int(int arg) {
        Assertions.assertTrue(arg < 10);
    }

    // this need static
    static IntStream rangeProvider() {
        return IntStream.range(0, 10);
    }

}