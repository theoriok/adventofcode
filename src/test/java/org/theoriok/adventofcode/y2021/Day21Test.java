package org.theoriok.adventofcode.y2021;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day21Test {

    private static final List<String> LIST = List.of(
        "Player 1 starting position: 4",
        "Player 2 starting position: 8"
    );

    @Test
    void firstMethod() {
        var day = new Day21(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(739785);
    }

    @Test
    void secondMethod() {
        var day = new Day21(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(444356092776315L);
    }
}