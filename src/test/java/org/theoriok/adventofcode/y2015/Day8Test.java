package org.theoriok.adventofcode.y2015;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day8Test {

    private static final List<String> LIST = List.of(
        "\"\"",
        "\"abc\"",
        "\"aaa\\\"aaa\"",
        "\"\\x27\""
    );

    @Test
    void firstMethod() {
        var day = new Day8(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(12);
    }

    @Test
    void secondMethod() {
        var day = new Day8(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(19);
    }
}