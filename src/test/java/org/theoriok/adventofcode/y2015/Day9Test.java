package org.theoriok.adventofcode.y2015;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day9Test {

    private static final List<String> LIST = List.of(
            "London to Belfast = 518",
            "London to Dublin = 464",
            "Dublin to Belfast = 141"
            );

    @Test
    void firstMethod() {
        var day = new Day9(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(605);
    }

    @Test
    void secondMethod() {
        var day = new Day9(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(982);
    }
}