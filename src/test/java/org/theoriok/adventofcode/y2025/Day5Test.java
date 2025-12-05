package org.theoriok.adventofcode.y2025;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day5Test {

    private static final List<String> LIST = List.of(
        "3-5",
        "10-14",
        "16-20",
        "12-18",
        "",
        "1",
        "5",
        "8",
        "11",
        "17",
        "32"
    );

    @Test
    void firstMethod() {
        var day = new Day5(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(3L);
    }

    @Test
    void secondMethod() {
        var day = new Day5(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(14L);
    }
}