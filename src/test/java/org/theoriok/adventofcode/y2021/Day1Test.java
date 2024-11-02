package org.theoriok.adventofcode.y2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day1Test {

    private static final List<String> LIST = List.of("199", "200", "208", "210", "200", "207", "240", "269", "260", "263");

    @Test
    void firstMethod() {
        var day = new Day1(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(7);
    }

    @Test
    void secondMethod() {
        var day = new Day1(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(5);
    }
}