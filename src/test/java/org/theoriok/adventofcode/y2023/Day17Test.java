package org.theoriok.adventofcode.y2023;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day17Test {

    private static final List<String> LIST = List.of(
        "2413432311323",
        "3215453535623",
        "3255245654254",
        "3446585845452",
        "4546657867536",
        "1438598798454",
        "4457876987766",
        "3637877979653",
        "4654967986887",
        "4564679986453",
        "1224686865563",
        "2546548887735",
        "4322674655533"
    );

    @Test
    void firstMethod() {
        var day = new Day17(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(102);
    }

    @Test
    void secondMethod() {
        var day = new Day17(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(0);
    }
}