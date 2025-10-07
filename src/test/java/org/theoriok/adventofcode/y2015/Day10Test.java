package org.theoriok.adventofcode.y2015;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day10Test {

    private static final List<String> LIST = List.of(
        "1"
    );

    @Test
    void iterateSomething() {
        var day = new Day10(LIST);

        var result = day.iterateSomething(5);

        assertThat(result).isEqualTo(6);
    }
}