package org.theoriok.adventofcode.y2021;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day2Test {

    private static final List<String> LIST = List.of(
        "forward 5",
        "down 5",
        "forward 8",
        "up 3",
        "down 8",
        "forward 2"
    );

    @Test
    void firstMethod() {
        var day = new Day2(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(150);
    }

    @Test
    void secondMethod() {
        var day = new Day2(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(900);
    }
}