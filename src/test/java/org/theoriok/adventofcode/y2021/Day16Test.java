package org.theoriok.adventofcode.y2021;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

class Day16Test {

    public static final String LINE1 = "D2FE28";
    public static final String LINE2 = "38006F45291200";
    public static final String LINE3 = "EE00D40C823060";
    public static final String LINE4 = "8A004A801A8002F478";
    public static final String LINE5 = "620080001611562C8802118E34";
    public static final String LINE6 = "C0015000016115A2E0802F182340";
    public static final String LINE7 = "A0016C880162017C3686B18A3D4780";

    @ParameterizedTest
    @CsvSource({
        LINE1 + ",6",
        LINE2 + ",1",
        LINE3 + ",7",
        LINE4 + ",16",
        LINE5 + ",12",
        LINE6 + ",23",
        LINE7 + ",31"
    })
    void firstMethod(String line, Long expectedResult) {
        var day = new Day16(List.of(line));

        var result = day.firstMethod();

        assertThat(result).isEqualTo(expectedResult);
    }
}