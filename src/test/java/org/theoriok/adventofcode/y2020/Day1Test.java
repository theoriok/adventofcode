package org.theoriok.adventofcode.y2020;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day1Test {

    private static final List<String> LIST = List.of(
        "1721",
        "979",
        "366",
        "299",
        "675",
        "1456"
    );

    @Test
    void firstMethod() {
        var day = new Day1(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(514579);
    }

    @Test
    void secondMethod() {
        var day = new Day1(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(241861950);
    }
}