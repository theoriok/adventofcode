package org.theoriok.adventofcode.y2022;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

class Day6Test {

    public static Stream<Arguments> listsAndResults1() {
        return Stream.of(
            Arguments.of(List.of("mjqjpqmgbljsphdztnvjfqwrcgsmlb"), 7),
            Arguments.of(List.of("bvwbjplbgvbhsrlpgdmjqwftvncz"), 5),
            Arguments.of(List.of("nppdvjthqldpwncqszvftbrmjlhg"), 6),
            Arguments.of(List.of("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"), 10),
            Arguments.of(List.of("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"), 11)
        );
    }

    public static Stream<Arguments> listsAndResults2() {
        return Stream.of(
            Arguments.of(List.of("mjqjpqmgbljsphdztnvjfqwrcgsmlb"), 19),
            Arguments.of(List.of("bvwbjplbgvbhsrlpgdmjqwftvncz"), 23),
            Arguments.of(List.of("nppdvjthqldpwncqszvftbrmjlhg"), 23),
            Arguments.of(List.of("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"), 29),
            Arguments.of(List.of("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"), 26)
        );
    }

    @ParameterizedTest
    @MethodSource("listsAndResults1")
    void firstMethod(List<String> input, int expectedResult) {
        var day = new Day6(input);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("listsAndResults2")
    void secondMethod(List<String> input, int expectedResult) {
        var day = new Day6(input);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(expectedResult);
    }
}