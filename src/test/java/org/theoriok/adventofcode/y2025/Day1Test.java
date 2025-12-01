package org.theoriok.adventofcode.y2025;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day1Test {

    private static final List<String> LIST = List.of(
        "L68",
        "L30",
        "R48",
        "L5",
        "R60",
        "L55",
        "L1",
        "L99",
        "R14",
        "L82"
    );

    private static final List<String> LIST2 = List.of(
        "L68",
        "L30",
        "R48",
        "L5",
        "R60",
        "L55",
        "L1",
        "L99",
        "R214",
        "L382"
    );

    @Test
    void firstMethod() {
        var day = new Day1(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(3);
    }

    @Test
    void secondMethod() {
        var day = new Day1(LIST2);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(11);
    }
}