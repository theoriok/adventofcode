package org.theoriok.adventofcode.y2021;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

class Day25Test {
    private static final List<String> LIST = List.of(
        "...>...",
        ".......",
        "......>",
        "v.....>",
        "......>",
        ".......",
        "..vvv.."
    );

    @Test
    void firstMethod() {
        var day = new Day25(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(58);
    }

}