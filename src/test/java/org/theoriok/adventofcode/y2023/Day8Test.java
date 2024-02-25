package org.theoriok.adventofcode.y2023;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

class Day8Test {

    private static final List<String> LIST = List.of(
        "LR",
        "",
        "11A = (11B, XXX)",
        "11B = (XXX, 11Z)",
        "11Z = (11B, XXX)",
        "22A = (22B, XXX)",
        "22B = (22C, 22C)",
        "22C = (22Z, 22Z)",
        "22Z = (22B, 22B)",
        "XXX = (XXX, XXX)"
    );

    private static final List<String> LIST1 = List.of(
        "RL",
        "",
        "AAA = (BBB, CCC)",
        "BBB = (DDD, EEE)",
        "CCC = (ZZZ, GGG)",
        "DDD = (DDD, DDD)",
        "EEE = (EEE, EEE)",
        "GGG = (GGG, GGG)",
        "ZZZ = (ZZZ, ZZZ)"
    );

    private static final List<String> LIST2 = List.of(
        "LLR",
        "",
        "AAA = (BBB, BBB)",
        "BBB = (AAA, ZZZ)",
        "ZZZ = (ZZZ, ZZZ)"
    );

    public static Stream<Arguments> listsAndResults1() {
        return Stream.of(
            Arguments.of(LIST1, 2),
            Arguments.of(LIST2, 6)
        );
    }

    @ParameterizedTest
    @MethodSource("listsAndResults1")
    void firstMethod(List<String> input, int expectedResult) {
        var day = new Day8(input);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void secondMethod() {
        var day = new Day8(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(6);
    }
}