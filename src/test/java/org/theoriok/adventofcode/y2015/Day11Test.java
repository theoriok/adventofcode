package org.theoriok.adventofcode.y2015;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day11Test {

    private static final List<String> LIST = List.of(

    );

    @ParameterizedTest
    @CsvSource({
            "abcdefgh,abcdffaa",
            "ghijklmn,ghjaabcc"
    })
    void firstMethod(String input, String expected) {
        var day = new Day11(List.of(input));

        var result = day.firstMethod();

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void secondMethod() {
        var day = new Day11(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(0);
    }
}