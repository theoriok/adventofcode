package org.theoriok.adventofcode.y2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day11Test {
    private static final List<String> LIST = List.of(
        "5483143223",
        "2745854711",
        "5264556173",
        "6141336146",
        "6357385478",
        "4167524645",
        "2176841721",
        "6882881134",
        "4846848554",
        "5283751526"
    );

    @Test
    void firstMethod() {
        var day = new Day11(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(1656);
    }

    @Test
    void secondMethod() {
        var day = new Day11(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(195);
    }

}