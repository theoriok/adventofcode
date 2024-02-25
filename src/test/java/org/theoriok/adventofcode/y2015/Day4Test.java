package org.theoriok.adventofcode.y2015;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

class Day4Test {

    @ParameterizedTest
    @CsvSource({
        "abcdef,609043",
        "pqrstuv,1048970",
    })
    void firstMethod(String input, long expected) {
        var day = new Day4(List.of(input));

        var result = day.firstMethod();

        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
        "abcdef,6742839",
        "pqrstuv,5714438",
    })
    void secondMethod(String input, long expected) {
        var day = new Day4(List.of(input));

        var result = day.secondMethod();

        assertThat(result).isEqualTo(expected);
    }
}