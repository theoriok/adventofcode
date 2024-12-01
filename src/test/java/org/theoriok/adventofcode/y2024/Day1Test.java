package org.theoriok.adventofcode.y2024;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day1Test {

    private static final List<String> LIST = List.of(
        "3   4",
        "4   3",
        "2   5",
        "1   3",
        "3   9",
        "3   3"
    );

    @Test
    void firstMethod() {
        var day = new Day1(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(11);
    }

    @Test
    void secondMethod() {
        var day = new Day1(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(31);
    }
}