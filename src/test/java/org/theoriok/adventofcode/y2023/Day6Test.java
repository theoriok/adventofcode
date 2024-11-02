package org.theoriok.adventofcode.y2023;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day6Test {


    private static final List<String> LIST = List.of(
        "Time:      7  15   30",
        "Distance:  9  40  200"
    );

    @Test
    void firstMethod() {
        var day = new Day6(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(288);
    }

    @Test
    void secondMethod() {
        var day = new Day6(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(71503);
    }
}