package org.theoriok.adventofcode.y2015;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day25Test {

    private static final List<String> LIST = List.of(
        "To continue, please consult the code grid in the manual.  Enter the code at row 3, column 2."
    );

    @Test
    void firstMethod() {
        var day = new Day25(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(8057251L);
    }
}