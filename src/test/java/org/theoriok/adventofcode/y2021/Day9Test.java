package org.theoriok.adventofcode.y2021;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day9Test {
    private static final List<String> LIST = List.of(
        "2199943210",
        "3987894921",
        "9856789892",
        "8767896789",
        "9899965678"
    );

    @Test
    void firstMethod() {
        var day = new Day9(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(15);
    }

    @Test
    void secondMethod() {
        var day = new Day9(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(1134);
    }
}