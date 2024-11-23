package org.theoriok.adventofcode.y2015;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day21Test {

    private static final List<String> LIST = List.of(
        "Hit Points: 100",
        "Damage: 5",
        "Armor: 1"
    );

    @Test
    void firstMethod() {
        var day = new Day21(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(23);
    }

    @Test
    void secondMethod() {
        var day = new Day21(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(33);
    }
}