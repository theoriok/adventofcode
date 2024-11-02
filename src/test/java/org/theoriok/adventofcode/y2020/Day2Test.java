package org.theoriok.adventofcode.y2020;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day2Test {

    private static final List<String> LIST = List.of(
        "1-3 a: abcde",
        "1-3 b: cdefg",
        "2-9 c: ccccccccc"
    );

    @Test
    void firstMethod() {
        var day = new Day2(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(2);
    }

    @Test
    void secondMethod() {
        var day = new Day2(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(1);
    }
}