package org.theoriok.adventofcode.y2021;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class Day21Test {

    private static final List<String> LIST1 = List.of(
        "Player 1 starting position: 4",
        "Player 2 starting position: 8"
    );
    private static final List<String> LIST2 = List.of(
        "Player 1 starting position: 1",
        "Player 2 starting position: 1"
    );

    public static Stream<Arguments> listsAndResults1() {
        return Stream.of(
            Arguments.of(LIST1, 739785),
            Arguments.of(LIST2, 598416)
        );
    }

    @ParameterizedTest
    @MethodSource("listsAndResults1")
    void firstMethod(List<String> input, int expectedResult) {
        var day = new Day21(input);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void secondMethod() {
        var day = new Day21(LIST1);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(444356092776315L);
    }
}