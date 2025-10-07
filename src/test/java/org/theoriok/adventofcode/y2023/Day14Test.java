package org.theoriok.adventofcode.y2023;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day14Test {
    private static final List<String> LIST = List.of(
        "O....#....",
        "O.OO#....#",
        ".....##...",
        "OO.#O....O",
        ".O.....O#.",
        "O.#..O.#.#",
        "..O..#O..O",
        ".......O..",
        "#....###..",
        "#OO..#...."
    );

    @Test
    void firstMethod() {
        var day = new Day14(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(136);
    }

    @Test
    void secondMethod() {
        var day = new Day14(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(64);
    }
}