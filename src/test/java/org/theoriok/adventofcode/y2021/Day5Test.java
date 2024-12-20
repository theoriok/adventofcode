package org.theoriok.adventofcode.y2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day5Test {

    private static final List<String> LIST = List.of(
        "0,9 -> 5,9",
        "8,0 -> 0,8",
        "9,4 -> 3,4",
        "2,2 -> 2,1",
        "7,0 -> 7,4",
        "6,4 -> 2,0",
        "0,9 -> 2,9",
        "3,4 -> 1,4",
        "0,0 -> 8,8",
        "5,5 -> 8,2"
    );

    @Test
    void firstMethod() {
        var day = new Day5(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(5);
    }

    @Test
    void secondMethod() {
        var day = new Day5(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(12);
    }
}