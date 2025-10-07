package org.theoriok.adventofcode.y2015;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

class Day20Test {

    @ParameterizedTest
    @CsvSource({"180,10", "1800,72", "18000,600"})
    void firstMethod(String input, int expected) {
        var day = new Day20(List.of(input));

        var result = day.firstMethod();

        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"180,10", "1800,60", "18000,540"})
    void secondMethod(String input, int expected) {
        var day = new Day20(List.of(input));

        var result = day.secondMethod();

        assertThat(result).isEqualTo(expected);
    }
}