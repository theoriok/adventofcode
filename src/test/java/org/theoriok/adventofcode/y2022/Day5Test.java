package org.theoriok.adventofcode.y2022;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day5Test {

    private static final List<String> LIST = List.of(
        "    [D]    ",
        "[N] [C]",
        "[Z] [M] [P]",
        " 1   2   3 ",
        "",
        "move 1 from 2 to 1",
        "move 3 from 1 to 3",
        "move 2 from 2 to 1",
        "move 1 from 1 to 2"
    );

    @Test
    void firstMethod() {
        var day = new Day5(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo("CMZ");
    }

    @Test
    void secondMethod() {
        var day = new Day5(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo("MCD");
    }

}