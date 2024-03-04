package org.theoriok.adventofcode.y2015;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day7Test {

    private static final List<String> LIST = List.of(
        "123 -> x",
        "456 -> y",
        "x AND y -> d",
        "x OR y -> e",
        "x LSHIFT 2 -> f",
        "y RSHIFT 2 -> g",
        "NOT x -> h",
        "NOT y -> i",
        "NOT d -> b",
        "NOT b -> a"
    );

    @Test
    void firstMethod() {
        var day = new Day7(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(72);
    }

    @Test
    void secondMethod() {
        var day = new Day7(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(-73);
    }
}