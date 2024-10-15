package org.theoriok.adventofcode.y2015;

import com.google.common.collect.Streams;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class Day12Test {

    public static Stream<Arguments> firstMethodSource() {
        return Stream.of(
                Arguments.of("[]", 0), Arguments.of("{}", 0),
                Arguments.of("{\"a\":[-1,1]}", 0), Arguments.of("[-1,{\"a\":1}]", 0),
                Arguments.of("[[[3]]]", 3), Arguments.of("{\"a\":{\"b\":4},\"c\":-1}", 3),
                Arguments.of("[1,2,3]", 6), Arguments.of("{\"a\":2,\"b\":4}", 6)
        );
    }

    public static Stream<Arguments> secondMethodSource() {
        return Streams.concat(firstMethodSource(), Stream.of(
                Arguments.of("[1,{\"c\":\"red\",\"b\":2},3]", 4),
                Arguments.of("{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}", 0),
                Arguments.of("[1,\"red\",5]", 6),
                Arguments.of("{\"test\":[1,\"red\",5]}", 6)
        ));
    }

    @ParameterizedTest
    @MethodSource("firstMethodSource")
    void firstMethod(String input, int expected) {
        var day = new Day12(List.of(input));

        var result = day.firstMethod();

        assertThat(result).isEqualTo(expected);
    }


    @ParameterizedTest
    @MethodSource("secondMethodSource")
    void secondMethod(String input, int expected) {
        var day = new Day12(List.of(input));

        var result = day.secondMethod();

        assertThat(result).isEqualTo(expected);
    }
}