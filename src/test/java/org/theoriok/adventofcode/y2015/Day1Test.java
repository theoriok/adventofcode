package org.theoriok.adventofcode.y2015;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day1Test {

    @ParameterizedTest
    @CsvSource({
        "(()),0",
        "()(),0",
        "(((,3",
        "(()(()(,3",
        "))(((((,3",
        "()),-1",
        "))(,-1",
        "))),-3",
        ")())()),-3",
    })
    void firstMethod(String input, int expected) {
        var day = new Day1(List.of(input));

        var result = day.firstMethod();

        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
        "),1",
        "()()),5",
    })
    void secondMethod(String input, int expected) {
        var day = new Day1(List.of(input));

        var result = day.secondMethod();

        assertThat(result).isEqualTo(expected);
    }
}