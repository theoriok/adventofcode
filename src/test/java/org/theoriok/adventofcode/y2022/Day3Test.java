package org.theoriok.adventofcode.y2022;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day3Test {

    private static final List<String> LIST = List.of(
        "vJrwpWtwJgWrhcsFMMfFFhFp",
        "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
        "PmmdzqPrVvPwwTWBwg",
        "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
        "ttgJtRGJQctTZtZT",
        "CrZsJsPPZsGzwwsLwLmpwMDw"
    );

    @Test
    void firstMethod() {
        var day = new Day3(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(157);
    }

    @Test
    void secondMethod() {
        var day = new Day3(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(70);
    }

}