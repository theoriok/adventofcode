package org.theoriok.adventofcode.y2023;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class Day10Test {
    private static final List<String> LIST1 = List.of(
        "7-F7-",
        ".FJ|7",
        "SJLL7",
        "|F--J",
        "LJ.LJ"
    );

    private static final List<String> LIST2 =
        List.of("FF7FSF7F7F7F7F7F---7", "L|LJ||||||||||||F--J", "FL-7LJLJ||||||LJL-77", "F--JF--7||LJLJIF7FJ-", "L---JF-JLJIIIIFJLJJ7", "|F|F-JF---7IIIL7L|7|",
            "|FFJF7L7F-JF7IIL---7", "7-L-JL7||F7|L7F-7F7|", "L.L7LFJ|||||FJL7||LJ", "L7JLJL-JLJLJL--JLJ.L");

    @Test
    void firstMethod() {
        var day = new Day10(LIST1);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(8);
    }

    @Test
    void secondMethod() {
        var day = new Day10(LIST2);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(10);
    }
}