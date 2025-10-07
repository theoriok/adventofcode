package org.theoriok.adventofcode.y2024;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day5Test {

    private static final List<String> LIST = List.of(
        "47|53",
        "97|13",
        "97|61",
        "97|47",
        "75|29",
        "61|13",
        "75|53",
        "29|13",
        "97|29",
        "53|29",
        "61|53",
        "97|53",
        "61|29",
        "47|13",
        "75|47",
        "97|75",
        "47|61",
        "75|61",
        "47|29",
        "75|13",
        "53|13",
        "",
        "75,47,61,53,29",
        "97,61,53,29,13",
        "75,29,13",
        "75,97,47,61,53",
        "61,13,29",
        "97,13,75,29,47"
    );

    @Test
    void firstMethod() {
        var day = new Day5(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(143);
    }

    @Test
    void secondMethod() {
        var day = new Day5(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(123);
    }
}