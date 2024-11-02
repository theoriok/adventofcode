package org.theoriok.adventofcode.y2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day6Test {

    private static final List<String> LIST = List.of(
        "3", "4", "3", "1", "2"
    );

    @Test
    void firstMethod() {
        var day = new Day6(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(5934L);
    }

    @Test
    void secondMethod() {
        var day = new Day6(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(26984457539L);
    }
}