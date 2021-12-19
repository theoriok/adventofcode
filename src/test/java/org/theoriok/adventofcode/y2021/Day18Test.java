package org.theoriok.adventofcode.y2021;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.List;

class Day18Test {

    @ParameterizedTest
    @Disabled
    void firstMethod(List<String> list, Long expectedResult) {
        var day = new Day16(list);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(expectedResult);
    }

}