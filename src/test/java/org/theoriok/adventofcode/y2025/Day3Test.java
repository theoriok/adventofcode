package org.theoriok.adventofcode.y2025;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day3Test {

    private static final List<String> LIST = List.of(
        "987654321111111",
        "811111111111119",
        "234234234234278",
        "818181911112111"
    );

    @Test
    void firstMethod() {
        var day = new Day3(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(357);
    }

    @Test
    void secondMethod() {
        var day = new Day3(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(3121910778619L);
    }
}