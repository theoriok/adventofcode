package org.theoriok.adventofcode.y2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day25Test {
    private static final List<String> LIST = List.of(
        "v...>>.vv>",
        ".vv>>.vv..",
        ">>.>v>...v",
        ">>v>>.>.v.",
        "v>v.vv.v..",
        ">.>>..v...",
        ".vv..>.>v.",
        "v.v..>>v.v",
        "....v..v.>"
    );

    @Test
    void firstMethod() {
        var day = new Day25(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(58);
    }

}