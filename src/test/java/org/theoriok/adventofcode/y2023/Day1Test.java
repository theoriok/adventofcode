package org.theoriok.adventofcode.y2023;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day1Test {

    private static final List<String> LIST1 = List.of(
        "1abc2",
        "pqr3stu8vwx",
        "a1b2c3d4e5f",
        "treb7uchet"
    );

    private static final List<String> LIST2 = List.of(
        "two1nine",
        "eightwothree",
        "abcone2threexyz",
        "xtwone3four",
        "4nineeightseven2",
        "zoneight234",
        "7pqrstsixteen"
    );

    @Test
    void firstMethod() {
        var day = new Day1(LIST1);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(142);
    }

    @Test
    void secondMethod() {
        var day = new Day1(LIST2);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(281);
    }

}