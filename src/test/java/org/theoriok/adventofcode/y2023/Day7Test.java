package org.theoriok.adventofcode.y2023;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day7Test {

    private static final List<String> LIST = List.of(
        "32T3K 765",
        "T55J5 684",
        "KK677 28",
        "KTJJT 220",
        "QQQJA 483"
    );

    @Test
    void firstMethod() {
        var day = new Day7(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(6440);
    }

    @Test
    void secondMethod() {
        var day = new Day7(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(0);
    }
}