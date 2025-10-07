package org.theoriok.adventofcode.y2015;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day15Test {

    private static final List<String> LIST = List.of(
        "Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8",
        "Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3"
    );

    @Test
    void firstMethod() {
        var day = new Day15(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(62842880);
    }

    @Test
    void secondMethod() {
        var day = new Day15(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(57600000);
    }
}