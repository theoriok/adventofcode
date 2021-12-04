package org.theoriok.adventofcode.y2021;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.theoriok.adventofcode.Day;

import java.util.List;

class Day3Test {

    private static final List<String> LIST = List.of(
        "00100",
        "11110",
        "10110",
        "10111",
        "10101",
        "01111",
        "00111",
        "11100",
        "10000",
        "11001",
        "00010",
        "01010"
    );

    @Test
    void firstMethod() {
        var day = new Day3(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(198);
    }

    @Test
    void secondMethod() {
        var day = new Day3(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(230);
    }
}