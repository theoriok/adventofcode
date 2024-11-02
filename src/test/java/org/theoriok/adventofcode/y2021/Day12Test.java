package org.theoriok.adventofcode.y2021;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class Day12Test {

    private static final List<String> LIST1 = List.of(
        "start-A",
        "start-b",
        "A-c",
        "A-b",
        "b-d",
        "A-end",
        "b-end"
    );
    private static final List<String> LIST2 = List.of(
        "dc-end",
        "HN-start",
        "start-kj",
        "dc-start",
        "dc-HN",
        "LN-dc",
        "HN-end",
        "kj-sa",
        "kj-HN",
        "kj-dc"
    );
    private static final List<String> LIST3 = List.of(
        "fs-end",
        "he-DX",
        "fs-he",
        "start-DX",
        "pj-DX",
        "end-zg",
        "zg-sl",
        "zg-pj",
        "pj-he",
        "RW-he",
        "fs-DX",
        "pj-RW",
        "zg-RW",
        "start-pj",
        "he-WI",
        "zg-he",
        "pj-fs",
        "start-RW"
    );

    public static Stream<Arguments> listsAndResults1() {
        return Stream.of(
            Arguments.of(LIST1, 10),
            Arguments.of(LIST2, 19),
            Arguments.of(LIST3, 226)
        );
    }

    @ParameterizedTest
    @MethodSource("listsAndResults1")
    void firstMethod(List<String> list, int expectedResult) {
        var day = new Day12(list);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(expectedResult);
    }

    public static Stream<Arguments> listsAndResults2() {
        return Stream.of(
            Arguments.of(LIST1, 36),
            Arguments.of(LIST2, 103),
            Arguments.of(LIST3, 3509)
        );
    }

    @ParameterizedTest
    @MethodSource("listsAndResults2")
    void secondMethod(List<String> list, int expectedResult) {
        var day = new Day12(list);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(expectedResult);
    }
}