package org.theoriok.adventofcode.y2015;

import org.theoriok.adventofcode.Day;

import java.util.List;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.containsAnyIgnoreCase;

public class Day5 implements Day<Long, Long> {

    private final List<String> input;

    public Day5(List<String> input) {
        this.input = input;
    }

    @Override
    public Long firstMethod() {
        return input.stream()
            .filter(this::isNice)
            .count();
    }

    private boolean isNice(String line) {
        return hasAtLeastThreeVowels(line) && hasAtLeastOneDoubleLetter(line) && doesNotContainForbiddenParts(line);
    }

    private boolean hasAtLeastThreeVowels(String line) {
        return Pattern.compile("[aeiou]").matcher(line).results().count() >= 3;
    }

    private boolean hasAtLeastOneDoubleLetter(String line) {
        return Pattern.compile("(.)\\1").matcher(line).results().findAny().isPresent();
    }

    private boolean doesNotContainForbiddenParts(String line) {
        return !containsAnyIgnoreCase(line, "ab", "cd", "pq", "xy");
    }

    @Override
    public Long secondMethod() {
        return input.stream()
            .filter(this::isReallyNice)
            .count();
    }

    private boolean isReallyNice(String line) {
        return hasRepeatingNonOverlappingTwoLetters(line) && hasRepeatWithOneInBetween(line);
    }

    private boolean hasRepeatingNonOverlappingTwoLetters(String line) {
        for (int i = 0; i < line.length() - 1; i++) {
            String toCheck = line.substring(i, i + 2);
            if (line.substring(i + 2).contains(toCheck)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasRepeatWithOneInBetween(String line) {
        for (int i = 0; i < line.length() - 2; i++) {
            String toCheck = line.substring(i, i + 1);
            if (line.substring(i + 2, i + 3).equals(toCheck)) {
                return true;
            }
        }
        return false;
    }
}
