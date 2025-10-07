package org.theoriok.adventofcode.y2015;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day16Test {

    private static final List<String> LIST = List.of(
        "Sue 1: cars: 9, akitas: 3, goldfish: 0",
        "Sue 11: akitas: 0, children: 3, pomeranians: 3",
        "Sue 12: akitas: 0, children: 3, pomeranians: 2",
        "Sue 111: trees: 6, cars: 6, children: 4"
    );

    @Test
    void firstMethod() {
        var day = new Day16(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(11);
    }

    @Test
    void secondMethod() {
        var day = new Day16(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(12);
    }
}