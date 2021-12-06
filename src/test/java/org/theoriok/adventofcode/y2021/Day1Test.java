package org.theoriok.adventofcode.y2021;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day1Test {

    private static final List<String> LIST = List.of("199", "200", "208", "210", "200", "207", "240", "269", "260", "263");

    @Test
    void firstMethod() {
        var day = new Day1(LIST);

        var result = day.firstMethod();

        assertThat(increases).isEqualTo(7);
    }

    @Test
    void secondMethod() {
        var day = new Day1(LIST);

        var result = day.secondMethod();

        assertThat(increases).isEqualTo(5);
    }
}