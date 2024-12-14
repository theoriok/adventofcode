package org.theoriok.adventofcode.y2024;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day9Test {

    private static final List<String> LIST = List.of(
        "2333133121414131402"
    );

    @Test
    void firstMethod() {
        var day = new Day9(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(1928);
    }

    @Test
    void secondMethod() {
        var day = new Day9(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(2858);
    }
}