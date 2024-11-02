package org.theoriok.adventofcode.y2023;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day9Test {

    private static final List<String> LIST = List.of(
        "0 3 6 9 12 15",
        "1 3 6 10 15 21",
        "10 13 16 21 30 45"
    );

    @Test
    void firstMethod() {
        var day = new Day9(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(114);
    }

    @Test
    void secondMethod() {
        var day = new Day9(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(2);
    }
}