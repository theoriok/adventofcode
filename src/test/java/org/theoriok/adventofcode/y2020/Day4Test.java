package org.theoriok.adventofcode.y2020;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.theoriok.adventofcode.y2020.Day4;

import java.util.List;

class Day4Test {

    private static final List<String> LIST = List.of(
        "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd",
        "byr:1937 iyr:2017 cid:147 hgt:183cm",
        "",
        "iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884",
        "hcl:#cfa07d byr:1929",
        "",
        "hcl:#ae17e1 iyr:2013",
        "eyr:2024",
        "ecl:brn pid:760753108 byr:1931",
        "hgt:179cm",
        "",
        "hcl:#cfa07d eyr:2025 pid:166559648",
        "iyr:2011 ecl:brn hgt:59in"
    );

    @Test
    void firstMethod() {
        var day = new Day4(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(2);
    }
}