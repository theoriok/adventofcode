package org.theoriok.adventofcode.y2022;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day4Test {

    private static final List<String> LIST = List.of(
        "2-4,6-8",
        "2-3,4-5",
        "5-7,7-9",
        "2-8,3-7",
        "6-6,4-6",
        "2-6,4-8"
    );

    @Test
    void firstMethod() {
        var day = new Day4(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(2);
    }

    @Test
    void secondMethod() {
        var day = new Day4(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(4);
    }

}