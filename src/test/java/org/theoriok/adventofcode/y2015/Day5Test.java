package org.theoriok.adventofcode.y2015;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day5Test {

    private static final List<String> LIST1 = List.of(
        "ugknbfddgicrmopn",
        "aaa",
        "jchzalrnumimnmhp",
        "haegwjzuvuyypxyu",
        "dvszwmarrgswjxmb"
    );

    private static final List<String> LIST2 = List.of(
        "qjhvhtzxzqqjkmpb",
        "xxyxx",
        "uurcxstgmygtbstg",
        "ieodomkazucvgmuy",
        "abcde"
    );

    @Test
    void firstMethod() {
        var day = new Day5(LIST1);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(2);
    }

    @Test
    void secondMethod() {
        var day = new Day5(LIST2);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(2);
    }
}