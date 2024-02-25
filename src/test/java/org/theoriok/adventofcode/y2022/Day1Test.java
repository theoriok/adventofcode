package org.theoriok.adventofcode.y2022;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day1Test {

    private static final List<String> LIST = List.of(
        "1000",
        "2000",
        "3000",
        "",
        "4000",
        "",
        "5000",
        "6000",
        "",
        "7000",
        "8000",
        "9000",
        "",
        "10000"
    );

    @Test
    void firstMethod() {
        var day = new Day1(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(24000);
    }

    @Test
    void secondMethod() {
        var day = new Day1(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(45000);
    }
}