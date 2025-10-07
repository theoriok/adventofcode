package org.theoriok.adventofcode.y2021;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day15Test {

    private static final List<String> LIST = List.of(
        "1163751742",
        "1381373672",
        "2136511328",
        "3694931569",
        "7463417111",
        "1319128137",
        "1359912421",
        "3125421639",
        "1293138521",
        "2311944581"
    );

    @Test
    void firstMethod() {
        var day = new Day15(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(40);
    }

    @Test
    void secondMethod() {
        var day = new Day15(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(315);
    }
}
