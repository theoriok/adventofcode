package org.theoriok.adventofcode.y2021;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day14Test {
    private static final List<String> LIST = List.of(
        "NNCB",
        "",
        "CH -> B",
        "HH -> N",
        "CB -> H",
        "NH -> C",
        "HB -> C",
        "HC -> B",
        "HN -> C",
        "NN -> C",
        "BH -> H",
        "NC -> B",
        "NB -> B",
        "BN -> B",
        "BB -> N",
        "BC -> B",
        "CC -> N",
        "CN -> C"
    );

    @Test
    void firstMethod() {
        var day = new Day14(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(1588);
    }

    @Test
    void secondMethod() {
        var day = new Day14(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(2188189693529L);
    }
}