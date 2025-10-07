package org.theoriok.adventofcode.y2015;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

class Day19Test {

    @ParameterizedTest
    @CsvSource({"HOH,4", "HOHOHO,7"})
    void firstMethod(String baseMolecule, int expected) {
        List<String> input = List.of(
            "H => HO",
            "H => OH",
            "O => HH",
            "",
            baseMolecule
        );
        var day = new Day19(input);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"HOH,3", "HOHOHO,6"})
    void secondMethod(String baseMolecule, int expected) {
        List<String> input = List.of(
            "e => H",
            "e => O",
            "H => HO",
            "H => OH",
            "O => HH",
            "",
            baseMolecule
        );
        var day = new Day19(input);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(expected);
    }
}