package org.theoriok.adventofcode.y2020;

import org.apache.commons.lang3.StringUtils;
import org.theoriok.adventofcode.Day;

import java.util.List;
import java.util.Objects;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day2 extends Day {

    private final List<PasswordChecker> passwordCheckers;

    public Day2(List<String> input) {
        super(input);
        passwordCheckers = input.stream()
            .map(PasswordChecker::fromString)
            .filter(Objects::nonNull)
            .toList();
    }

    @Override
    public long firstMethod() {
        return passwordCheckers.stream()
            .filter(PasswordChecker::isValid)
            .count();
    }

    @Override
    public long secondMethod() {

        return 0L;
    }

    private static record PasswordChecker(
        int min,
        int max,
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

        public boolean isValid() {
            var matches = StringUtils.countMatches(password, letter);
            return min <= matches && matches <= max;
        }
    }
}
