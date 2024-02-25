package org.theoriok.adventofcode.y2020;

import org.apache.commons.lang3.StringUtils;
import org.theoriok.adventofcode.Day;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Day2 implements Day<Long, Long> {

    private final List<PasswordChecker> passwordCheckers;

    public Day2(List<String> input) {
        passwordCheckers = input.stream()
            .map(PasswordChecker::fromString)
            .filter(Objects::nonNull)
            .toList();
    }

    @Override
    public Long firstMethod() {
        return passwordCheckers.stream()
            .filter(PasswordChecker::isValidMinMax)
            .count();
    }

    @Override
    public Long secondMethod() {
        return passwordCheckers.stream()
            .filter(PasswordChecker::isValidPositions)
            .count();
    }

    private static record PasswordChecker(
        int firstNum,
        int secondNum,
        String letter,
        String password
    ) {
        public static PasswordChecker fromString(String string) {
            var pattern = Pattern.compile("(\\d+)-(\\d+) ([a-z]): ([a-z]+)", Pattern.MULTILINE);
            var matcher = pattern.matcher(string);
            if (matcher.find()) {
                var matchResult = matcher.toMatchResult();
                return new PasswordChecker(Integer.parseInt(matchResult.group(1)), Integer.parseInt(matchResult.group(2)), matchResult.group(3),
                    matchResult.group(4));
            } else {
                return null;
            }
        }

        public boolean isValidMinMax() {
            var matches = StringUtils.countMatches(password, letter);
            return firstNum <= matches && matches <= secondNum;
        }

        public boolean isValidPositions() {
            return password.substring(firstNum - 1, firstNum).equals(letter) ^ password.substring(secondNum - 1, secondNum).equals(letter);
        }
    }
}
