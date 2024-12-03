package org.theoriok.adventofcode.y2024;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day3Test {

    @Test
    void firstMethod() {
        var day = new Day3(List.of(
            "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
        ));

        var result = day.firstMethod();

        assertThat(result).isEqualTo(161);
    }

    @Test
    void secondMethod() {
        var day = new Day3(List.of(
            "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"
        ));

        var result = day.secondMethod();

        assertThat(result).isEqualTo(48);
    }
}