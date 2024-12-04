package org.theoriok.adventofcode.y2024;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

class Day4Test {

    private static final List<String> LIST = List.of(
        "MMMSXXMASM",
        "MSAMXMSMSA",
        "AMXSXMAAMM",
        "MSAMASMSMX",
        "XMASAMXAMM",
        "XXAMMXXAMA",
        "SMSMSASXSS",
        "SAXAMASAAA",
        "MAMMMXMMMM",
        "MXMXAXMASX"
    );

    private static final List<String> LIST2 = List.of(
        "S..S..S",
        ".A.A.A.",
        "..MMM..",
        "SAMXMAS",
        "..MMM..",
        ".A.A.A.",
        "S..S..S"
    );

    public static Stream<Arguments> firstMethod() {
        return Stream.of(
            Arguments.of(LIST, 18),
            Arguments.of(LIST2, 8)
        );
    }

    @ParameterizedTest
    @MethodSource()
    void firstMethod(List<String> input, int expected) {
        var day = new Day4(input);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void secondMethod() {
        var day = new Day4(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(9);
    }
}