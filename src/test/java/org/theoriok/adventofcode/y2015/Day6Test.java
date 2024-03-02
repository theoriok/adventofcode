package org.theoriok.adventofcode.y2015;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day6Test {

    private static final List<String> LIST = List.of(
        "turn on 0,0 through 999,999",
        "toggle 0,0 through 999,0",
        "turn off 499,499 through 500,500"
    );

    @Test
    void firstMethod() {
        var day = new Day6(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(998_996);
    }

    @Test
    void secondMethod() {
        var day = new Day6(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(1_001_996);
    }
}