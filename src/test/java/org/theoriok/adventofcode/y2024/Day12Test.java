package org.theoriok.adventofcode.y2024;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

class Day12Test {

    private static final List<String> LIST1 = List.of(
        "AAAA",
        "BBCD",
        "BBCC",
        "EEEC"
    );

    private static final List<String> LIST2 = List.of(
        "OOOOO",
        "OXOXO",
        "OOOOO",
        "OXOXO",
        "OOOOO"
    );

    private static final List<String> LIST3 = List.of(
        "RRRRIICCFF",
        "RRRRIICCCF",
        "VVRRRCCFFF",
        "VVRCCCJFFF",
        "VVVVCJJCFE",
        "VVIVCCJJEE",
        "VVIIICJJEE",
        "MIIIIIJJEE",
        "MIIISIJEEE",
        "MMMISSJEEE"
    );

    public static Stream<Arguments> firstMethod() {
        return Stream.of(
            Arguments.of(LIST1, 140),
            Arguments.of(LIST2, 772),
            Arguments.of(LIST3, 1930)
        );
    }

    @ParameterizedTest
    @MethodSource()
    void firstMethod(List<String> list, int expected) {
        var day = new Day12(list);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void secondMethod() {
        var day = new Day12(LIST1);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(0);
    }
}