package org.theoriok.adventofcode.y2023;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day15Test {

    private static final List<String> LIST = List.of(
        "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7"
    );

    @Test
    void firstMethod() {
        var day = new Day15(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(1320);
    }

    @Test
    void secondMethod() {
        var day = new Day15(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(145);
    }
}