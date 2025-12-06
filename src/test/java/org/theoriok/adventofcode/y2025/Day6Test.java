package org.theoriok.adventofcode.y2025;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day6Test {

    private static final List<String> LIST = List.of(
        "123 328  51 64 ",
        " 45 64  387 23 ",
        "  6 98  215 314",
        "*   +   *   +  "
    );

    @Test
    void firstMethod() {
        var day = new Day6(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(4277556);
    }

    @Test
    void secondMethod() {
        var day = new Day6(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(3263827);
    }
}