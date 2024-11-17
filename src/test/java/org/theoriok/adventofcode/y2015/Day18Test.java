package org.theoriok.adventofcode.y2015;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day18Test {

    private static final List<String> LIST1 = List.of(
        ".#.#.#",
        "...##.",
        "#....#",
        "..#...",
        "#.#..#",
        "####.."
    );
    private static final List<String> LIST2 = List.of(
        "##.#.#",
        "...##.",
        "#....#",
        "..#...",
        "#.#..#",
        "####.#"
    );

    @Test
    void firstMethod() {
        var day = new Day18(LIST1);

        var result = day.firstMethod(4);

        assertThat(result).isEqualTo(4);
    }

    @Test
    void secondMethod() {
        var day = new Day18(LIST2);

        var result = day.secondMethod(5);

        assertThat(result).isEqualTo(17);
    }
}