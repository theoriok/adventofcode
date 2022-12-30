package org.theoriok.adventofcode.y2020;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.theoriok.adventofcode.Day;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day4 implements Day<Long, Long> {
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

    @Override
    public Long secondMethod() {
        return passports.stream()
            .filter(Passport::validateRequiredFields)
            .filter(Passport::validateValues)
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
            return birthYear.validateRequired()
                && issueYear.validateRequired()
                && expirationYear.validateRequired()
                && height.validateRequired()
                && hairColor.validateRequired()
                && eyeColor.validateRequired()
                && passportID.validateRequired();
        }

        boolean validateValues() {
            return birthYear.validateValue()
                && issueYear.validateValue()
                && expirationYear.validateValue()
                && height.validateValue()
                && hairColor.validateValue()
                && eyeColor.validateValue()
                && passportID.validateValue();
        }
    }

    private record BirthYear(String stringValue) {
        boolean validateRequired() {
            return Objects.nonNull(stringValue);
        }

        public boolean validateValue() {
            if (!NumberUtils.isDigits(stringValue)) {
                return false;
            }
            var year = Integer.parseInt(stringValue);
            return year >= 1920 && year <= 2002;
        }
    }

    private record IssueYear(String stringValue) {
        boolean validateRequired() {
            return Objects.nonNull(stringValue);
        }

        public boolean validateValue() {
            if (!NumberUtils.isDigits(stringValue)) {
                return false;
            }
            var year = Integer.parseInt(stringValue);
            return year >= 2010 && year <= 2020;
        }
    }

    private record ExpirationYear(String stringValue) {
        boolean validateRequired() {
            return Objects.nonNull(stringValue);
        }

        public boolean validateValue() {
            if (!NumberUtils.isDigits(stringValue)) {
                return false;
            }
            var year = Integer.parseInt(stringValue);
            return year >= 2020 && year <= 2030;
        }
    }

    private record Height(String stringValue) {
        boolean validateRequired() {
            return Objects.nonNull(stringValue);
        }

        public boolean validateValue() {
            return (stringValue.endsWith("cm") && validateCm())
                || (stringValue.endsWith("in") && validateInch());
        }

        private boolean validateCm() {
            var numString = stringValue.replace("cm", "");
            if (!NumberUtils.isDigits(numString)) {
                return false;
            }
            var heightInCm = Integer.parseInt(numString);
            return heightInCm >= 150 && heightInCm <= 196;
        }

        private boolean validateInch() {
            var numString = stringValue.replace("in", "");
            if (!NumberUtils.isDigits(numString)) {
                return false;
            }
            var heightInInch = Integer.parseInt(numString);
            return heightInInch >= 59 && heightInInch <= 76;
        }
    }

    private record HairColor(String stringValue) {
        boolean validateRequired() {
            return Objects.nonNull(stringValue);
        }

        public boolean validateValue() {
            var colorString = stringValue.replace("#", "");
            return stringValue.startsWith("#") && colorString.matches("-?[0-9a-f]+") && colorString.length() == 6;
        }
    }

    private record EyeColor(String stringValue) {

        public static final List<String> POSSIBLE_VALUES = List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");

        boolean validateRequired() {
            return Objects.nonNull(stringValue);
        }

        public boolean validateValue() {
            return POSSIBLE_VALUES.contains(stringValue.toLowerCase());
        }
    }

    private record PassportId(String stringValue) {
        boolean validateRequired() {
            return Objects.nonNull(stringValue);
        }

        public boolean validateValue() {
            return NumberUtils.isDigits(stringValue) && stringValue.length() == 9;
        }
    }
}
