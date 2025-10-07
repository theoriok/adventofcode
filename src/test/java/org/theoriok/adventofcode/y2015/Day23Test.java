package org.theoriok.adventofcode.y2015;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day23Test {

    private static final List<String> LIST = List.of(
        "inc b",
        "jio b, +2",
        "tpl b",
        "inc b"
    );

    @Test
    void firstMethod() {
        var day = new Day23(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(2);
    }

    @Test
    void secondMethod() {
        var day = new Day23(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(2);
    }
}