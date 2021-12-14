package org.theoriok.adventofcode.y2021;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day13Test {
    private static final List<String> LIST = List.of(
        "6,10",
        "0,14",
        "9,10",
        "0,3",
        "10,4",
        "4,11",
        "6,0",
        "6,12",
        "4,1",
        "0,13",
        "10,12",
        "3,4",
        "3,0",
        "8,4",
        "1,10",
        "2,14",
        "8,10",
        "9,0",
        "",
        "fold along y=7",
        "fold along x=5"
    );

    @Test
    void firstMethod() {
        var day = new Day13(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(17);
    }

    @Test
    void secondMethod() {
        var day = new Day13(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(-1);
    }

}