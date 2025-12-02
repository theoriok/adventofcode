package org.theoriok.adventofcode.y2025;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day2Test {

    private static final List<String> LIST = List.of(
        "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"
    );

    @Test
    void firstMethod() {
        var day = new Day2(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(1227775554L);
    }

    @Test
    void secondMethod() {
        var day = new Day2(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(4174379265L);
    }
}