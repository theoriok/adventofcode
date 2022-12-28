package org.theoriok.adventofcode.y2020;

import org.apache.commons.lang3.StringUtils;
import org.theoriok.adventofcode.Day;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day4 implements Day<Long, Integer> {
    private final List<Passport> passports;

    public Day4(List<String> input) {
        var passportsAsStrings = String.join("\n", input)
            .replace("\n\n", "---SPLIT---")
            .replace("\n", " ")
            .split("---SPLIT---");
       passports = Arrays.stream(passportsAsStrings)
            .filter(StringUtils::isNotBlank)
            .map(this::toPassport)
            .toList();

    }

    private Passport toPassport(String lines) {
        var parts = Arrays.stream(lines.split(" "))
            .map(part -> part.split(":"))
            .collect(Collectors.toMap(part -> part[0], part -> part[1]));
        return new Passport(
            parts.get("byr"),
            parts.get("iyr"),
            parts.get("eyr"),
            parts.get("hgt"),
            parts.get("hcl"),
            parts.get("ecl"),
            parts.get("pid"),
            parts.get("cid")
        );
    }

    @Override
    public Long firstMethod() {
        return passports.stream()
            .filter(Passport::validateRequiredFields)
            .count();
    }

    private record Passport(
        String birthYear,
        String issueYear,
        String expirationYear,
        String height,
        String hairColor,
        String eyeColor,
        String passportID,
        String countryID
    ) {
        boolean validateRequiredFields() {
            return Objects.nonNull(birthYear)
                && Objects.nonNull(issueYear)
                && Objects.nonNull(expirationYear)
                && Objects.nonNull(height)
                && Objects.nonNull(hairColor)
                && Objects.nonNull(eyeColor)
                && Objects.nonNull(passportID);
        }
    }

}
