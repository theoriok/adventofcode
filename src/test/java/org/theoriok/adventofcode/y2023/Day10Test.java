package org.theoriok.adventofcode.y2023;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day10Test {
    private static final List<String> LIST = List.of(
        "7-F7-",
        ".FJ|7",
        "SJLL7",
        "|F--J",
        "LJ.LJ"
    );

    @Test
    void firstMethod() {
        var day = new Day10(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(8);
    }

    @Test
    void secondMethod() {
        var day = new Day10(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(0);
    }
}