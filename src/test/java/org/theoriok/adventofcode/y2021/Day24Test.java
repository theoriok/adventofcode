package org.theoriok.adventofcode.y2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day24Test {
    private static final List<String> LIST = List.of(
        "inp w",
        "mul x 0",
        "add x z",
        "mod x 26",
        "div z 1",
        "add x 14",
        "eql x w",
        "eql x 0",
        "mul y 0",
        "add y 25",
        "mul y x",
        "add y 1",
        "mul z y",
        "mul y 0",
        "add y w",
        "add y 16",
        "mul y x",
        "add z y",
        "inp w",
        "mul x 0",
        "add x z",
        "mod x 26",
        "div z 1",
        "add x 11",
        "eql x w",
        "eql x 0",
        "mul y 0",
        "add y 25",
        "mul y x",
        "add y 1",
        "mul z y",
        "mul y 0",
        "add y w",
        "add y 3",
        "mul y x",
        "add z y",
        "inp w",
        "mul x 0",
        "add x z",
        "mod x 26",
        "div z 1",
        "add x 12",
        "eql x w",
        "eql x 0",
        "mul y 0",
        "add y 25",
        "mul y x",
        "add y 1",
        "mul z y",
        "mul y 0",
        "add y w",
        "add y 2",
        "mul y x",
        "add z y",
        "inp w",
        "mul x 0",
        "add x z",
        "mod x 26",
        "div z 1",
        "add x 11",
        "eql x w",
        "eql x 0",
        "mul y 0",
        "add y 25",
        "mul y x",
        "add y 1",
        "mul z y",
        "mul y 0",
        "add y w",
        "add y 7",
        "mul y x",
        "add z y",
        "inp w",
        "mul x 0",
        "add x z",
        "mod x 26",
        "div z 26",
        "add x -10",
        "eql x w",
        "eql x 0",
        "mul y 0",
        "add y 25",
        "mul y x",
        "add y 1",
        "mul z y",
        "mul y 0",
        "add y w",
        "add y 13",
        "mul y x",
        "add z y",
        "inp w",
        "mul x 0",
        "add x z",
        "mod x 26",
        "div z 1",
        "add x 15",
        "eql x w",
        "eql x 0",
        "mul y 0",
        "add y 25",
        "mul y x",
        "add y 1",
        "mul z y",
        "mul y 0",
        "add y w",
        "add y 6",
        "mul y x",
        "add z y",
        "inp w",
        "mul x 0",
        "add x z",
        "mod x 26",
        "div z 26",
        "add x -14",
        "eql x w",
        "eql x 0",
        "mul y 0",
        "add y 25",
        "mul y x",
        "add y 1",
        "mul z y",
        "mul y 0",
        "add y w",
        "add y 10",
        "mul y x",
        "add z y",
        "inp w",
        "mul x 0",
        "add x z",
        "mod x 26",
        "div z 1",
        "add x 10",
        "eql x w",
        "eql x 0",
        "mul y 0",
        "add y 25",
        "mul y x",
        "add y 1",
        "mul z y",
        "mul y 0",
        "add y w",
        "add y 11",
        "mul y x",
        "add z y",
        "inp w",
        "mul x 0",
        "add x z",
        "mod x 26",
        "div z 26",
        "add x -4",
        "eql x w",
        "eql x 0",
        "mul y 0",
        "add y 25",
        "mul y x",
        "add y 1",
        "mul z y",
        "mul y 0",
        "add y w",
        "add y 6",
        "mul y x",
        "add z y",
        "inp w",
        "mul x 0",
        "add x z",
        "mod x 26",
        "div z 26",
        "add x -3",
        "eql x w",
        "eql x 0",
        "mul y 0",
        "add y 25",
        "mul y x",
        "add y 1",
        "mul z y",
        "mul y 0",
        "add y w",
        "add y 5",
        "mul y x",
        "add z y",
        "inp w",
        "mul x 0",
        "add x z",
        "mod x 26",
        "div z 1",
        "add x 13",
        "eql x w",
        "eql x 0",
        "mul y 0",
        "add y 25",
        "mul y x",
        "add y 1",
        "mul z y",
        "mul y 0",
        "add y w",
        "add y 11",
        "mul y x",
        "add z y",
        "inp w",
        "mul x 0",
        "add x z",
        "mod x 26",
        "div z 26",
        "add x -3",
        "eql x w",
        "eql x 0",
        "mul y 0",
        "add y 25",
        "mul y x",
        "add y 1",
        "mul z y",
        "mul y 0",
        "add y w",
        "add y 4",
        "mul y x",
        "add z y",
        "inp w",
        "mul x 0",
        "add x z",
        "mod x 26",
        "div z 26",
        "add x -9",
        "eql x w",
        "eql x 0",
        "mul y 0",
        "add y 25",
        "mul y x",
        "add y 1",
        "mul z y",
        "mul y 0",
        "add y w",
        "add y 4",
        "mul y x",
        "add z y",
        "inp w",
        "mul x 0",
        "add x z",
        "mod x 26",
        "div z 26",
        "add x -12",
        "eql x w",
        "eql x 0",
        "mul y 0",
        "add y 25",
        "mul y x",
        "add y 1",
        "mul z y",
        "mul y 0",
        "add y w",
        "add y 6",
        "mul y x",
        "add z y"
    );

    @Test
    void firstMethod() {
        var day = new Day24(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(59996912981939L);
    }

    @Test
    void secondMethod() {
        var day = new Day24(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(17241911811915L);
    }
}