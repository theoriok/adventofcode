package org.theoriok.adventofcode.y2015;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day17Test {

    private static final List<String> LIST = List.of("20", "15", "10", "5", "5");
    private static final int TEST_VOLUME = 25;

    @Test
    void firstMethod() {
        var day = new Day17(LIST);

        var result = day.firstMethod(TEST_VOLUME);

        assertThat(result).isEqualTo(4);
    }

    @Test
    void secondMethod() {
        var day = new Day17(LIST);

        var result = day.secondMethod(TEST_VOLUME);

        assertThat(result).isEqualTo(3);
    }
}