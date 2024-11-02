package org.theoriok.adventofcode.y2015;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day3Test {

    @ParameterizedTest
    @CsvSource({
        ">,2",
        "^>v<,4",
        "^v^v^v^v^v,2",
    })
    void firstMethod(String input, int expected) {
        var day = new Day3(List.of(input));

        var result = day.firstMethod();

        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
        "^v,3",
        "^>v<,3",
        "^v^v^v^v^v,11",
    })
    void secondMethod(String input, int expected) {
        var day = new Day3(List.of(input));

        var result = day.secondMethod();

        assertThat(result).isEqualTo(expected);
    }
}