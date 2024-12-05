package org.theoriok.adventofcode.y2015;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day24Test {

    private static final List<String> LIST = List.of(
        "1",
        "2",
        "3",
        "4",
        "5",
        "7",
        "8",
        "9",
        "10",
        "11"
    );

    @Test
    void firstMethod() {
        var day = new Day24(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(99);
    }

    @Test
    void secondMethod() {
        var day = new Day24(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(0);
    }
}