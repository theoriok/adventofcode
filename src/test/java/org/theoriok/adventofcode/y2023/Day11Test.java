package org.theoriok.adventofcode.y2023;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day11Test {

    private static final List<String> LIST = List.of(
        "...#......",
        ".......#..",
        "#.........",
        "..........",
        "......#...",
        ".#........",
        ".........#",
        "..........",
        ".......#..",
        "#...#....."
    );

    @Test
    void firstMethod() {
        var day = new Day11(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(374);
    }

    @Test
    void secondMethod() {
        var day = new Day11(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(82000210L);
    }
}