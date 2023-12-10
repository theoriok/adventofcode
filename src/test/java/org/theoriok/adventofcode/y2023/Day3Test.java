package org.theoriok.adventofcode.y2023;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

public class Day3Test {
    private static final List<String> LIST = List.of(
        "467..114..",
        "...*......",
        "..35..633.",
        "......#...",
        "617*......",
        ".....+.58.",
        "..592.....",
        "......755.",
        "...$.*....",
        ".664.598.."
    );

    private static final List<String> LIST2 = List.of(
        "12.......*..",
        "+.........34",
        ".......-12..",
        "..78........",
        "..*....60...",
        "78..........",
        ".......23...",
        "....90*12...",
        "............",
        "2.2......12.",
        ".*.........*",
        "1.1.......56"
    );

    private static final List<String> LIST3 = List.of(
        "12.......*..",
        "+.........34",
        ".......-12..",
        "..78........",
        "..*....60...",
        "78.........9",
        ".5.....23..$",
        "8...90*12...",
        "............",
        "2.2......12.",
        ".*.........*",
        "1.1..503+.56"
    );

    @Test
    void firstMethod() {
        var day = new Day3(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(4361);
    }

    @Test
    void firstMethod2() {
        var day = new Day3(LIST2);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(413);
    }

    @Test
    void firstMethod3() {
        var day = new Day3(LIST3);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(925);
    }

    @Test
    void secondMethod() {
        var day = new Day3(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(467835);
    }

    @Test
    void secondMethod2() {
        var day = new Day3(LIST2);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(6756);
    }

    @Test
    void secondMethod3() {
        var day = new Day3(LIST3);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(6756);
    }
}
