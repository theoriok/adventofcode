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
            new BirthYear(parts.get("byr")),
            new IssueYear(parts.get("iyr")),
            new ExpirationYear(parts.get("eyr")),
            new Height(parts.get("hgt")),
            new HairColor(parts.get("hcl")),
            new EyeColor(parts.get("ecl")),
            new PassportId(parts.get("pid")),
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
        BirthYear birthYear,
        IssueYear issueYear,
        ExpirationYear expirationYear,
        Height height,
        HairColor hairColor,
        EyeColor eyeColor,
        PassportId passportID,
        String countryID
    ) {
        boolean validateRequiredFields() {
            return Objects.nonNull(birthYear.stringValue)
                && Objects.nonNull(issueYear.stringValue)
                && Objects.nonNull(expirationYear.stringValue)
                && Objects.nonNull(height.stringValue)
                && Objects.nonNull(hairColor.stringValue)
                && Objects.nonNull(eyeColor.stringValue)
                && Objects.nonNull(passportID.stringValue);
        }
    }



    private record BirthYear(String stringValue) {
    }

    private record IssueYear(String stringValue) {
    }

    private record ExpirationYear(String stringValue) {
    }

    private record Height(String stringValue) {
    }

    private record HairColor(String stringValue) {
    }

    private record EyeColor(String stringValue) {
    }

    private record PassportId(String stringValue) {
    }
}
