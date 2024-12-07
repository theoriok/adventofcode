package org.theoriok.adventofcode.y2024;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day7Test {

    private static final List<String> LIST = List.of(
        "190: 10 19",
        "3267: 81 40 27",
        "83: 17 5",
        "156: 15 6",
        "7290: 6 8 6 15",
        "161011: 16 10 13",
        "192: 17 8 14",
        "21037: 9 7 18 13",
        "292: 11 6 16 20"
    );

    @Test
    void firstMethod() {
        var day = new Day7(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(3749);
    }

    @Test
    void secondMethod() {
        var day = new Day7(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(11387);
    }
}