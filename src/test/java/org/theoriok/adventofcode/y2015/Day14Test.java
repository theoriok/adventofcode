package org.theoriok.adventofcode.y2015;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day14Test {

    private static final List<String> LIST = List.of(
        "Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.",
        "Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds."
    );

    @Test
    void firstMethod() {
        var day = new Day14(LIST);

        var result = day.firstMethod(1000);

        assertThat(result).isEqualTo(1120);
    }

    @Test
    void secondMethod() {
        var day = new Day14(LIST);

        var result = day.secondMethod(1000);

        assertThat(result).isEqualTo(689);
    }
}