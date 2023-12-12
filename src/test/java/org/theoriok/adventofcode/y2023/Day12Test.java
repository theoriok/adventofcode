package org.theoriok.adventofcode.y2023;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day12Test {

    private static final List<String> LIST = List.of(
        "???.### 1,1,3",
        ".??..??...?##. 1,1,3",
        "?#?#?#?#?#?#?#? 1,3,1,6",
        "????.#...#... 4,1,1",
        "????.######..#####. 1,6,5",
        "?###???????? 3,2,1"
    );

    @Test
    void firstMethod() {
        var day = new Day12(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(21);
    }

    @Test
    void secondMethod() {
        var day = new Day12(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(0);
    }
}
