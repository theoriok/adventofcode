package org.theoriok.adventofcode.y2015;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day11Test {

    @ParameterizedTest
    @CsvSource({
        "abcdefgh,abcdffaa",
        "ghijklmn,ghjaabcc",
        "cqjxjnds,cqjxxyzz"
    })
    void firstMethod(String input, String expected) {
        var day = new Day11(List.of(input));

        var result = day.firstMethod();

        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
        "abcdefgh,abcdffbb",
        "ghijklmn,ghjbbcdd",
        "cqjxjnds,cqkaabcc"
    })
    void secondMethod(String input, String expected) {
        var day = new Day11(List.of(input));

        var result = day.secondMethod();

        assertThat(result).isEqualTo(expected);
    }
}