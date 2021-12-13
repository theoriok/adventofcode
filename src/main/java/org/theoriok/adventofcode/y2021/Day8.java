package org.theoriok.adventofcode.y2021;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.collections4.CollectionUtils.isEqualCollection;
import static org.theoriok.adventofcode.y2021.Day8.Digit.EIGHT;
import static org.theoriok.adventofcode.y2021.Day8.Digit.FOUR;
import static org.theoriok.adventofcode.y2021.Day8.Digit.ONE;
import static org.theoriok.adventofcode.y2021.Day8.Digit.SEVEN;

import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day8 extends Day {

    public Day8(List<String> input) {
        super(input);
    }

    @Override
    public long firstMethod() {
        return input.stream()
            .map(Entry::new)
            .mapToLong(Entry::countFirstMethodDigits)
            .sum();
    }

    @Override
    public long secondMethod() {
        return input.stream()
            .map(Entry::new)
            .mapToLong(Entry::decode)
            .sum();
    }

    private static final class Entry {
        public static final List<Digit> DIGITS_WITH_UNIQUE_SIZE = List.of(ONE, FOUR, SEVEN, EIGHT);
        private final List<String> digitSignals;
        private final List<String> output;

        private Entry(String input) {
            var split = input.split(" \\| ");
            digitSignals = Arrays.stream(split[0].split(" ")).toList();
            output = Arrays.stream(split[1].split(" ")).toList();
        }

        public long countFirstMethodDigits() {
            var uniqueLengths = DIGITS_WITH_UNIQUE_SIZE.stream()
                .map(Digit::getNumberOfSignals)
                .toList();
            return output.stream()
                .filter(string -> uniqueLengths.contains(string.length()))
                .count();
        }

        public long decode() {
            var digitSignalsByLength = digitSignals.stream()
                .collect(groupingBy(String::length));
            Map<String, Integer> lettersBySignal = new HashMap<>();
            Map<String, Set<Integer>> lettersByPossibleSignals = new HashMap<>();
            for (Digit digit : Digit.values()) {
                var digitSignal = digitSignalsByLength.get(digit.getNumberOfSignals()).get(0);
                for (int i = 0; i < digit.signalsUsed.size(); i++) {
                    lettersByPossibleSignals.computeIfAbsent(digitSignal.substring(i, i + 1), ___ -> new HashSet<>()).add(digit.signalsUsed.get(i));
                }
            }
            while(!done(lettersByPossibleSignals)) {
                lettersByPossibleSignals.entrySet().stream()
                    .filter(entry -> entry.getValue().size() == 1)
                    .forEach(entry -> {
                        var value = entry.getValue().iterator().next();
                        var key = entry.getKey();
                        lettersBySignal.put(key, value);
                        lettersByPossibleSignals.values().forEach(values-> values.remove(value));
                    });
                for (Digit digit : DIGITS_WITH_UNIQUE_SIZE) {
                    var signalsByLetter = lettersBySignal.entrySet().stream()
                        .collect(toMap(Map.Entry::getValue, Map.Entry::getKey));
                    var unmappedLetters = Arrays.stream(digitSignalsByLength.get(digit.getNumberOfSignals()).get(0).split(""))
                        .collect(partitioningBy(lettersBySignal::containsKey));
                    if (unmappedLetters.get(false).size() == 1) {
                        //lettersBySignal.put()
                    }
                }
            }
            return output.stream()
                .mapToLong(outputDigit -> {
                    var signals = Arrays.stream(outputDigit.split(""))
                        .map(lettersBySignal::get)
                        .sorted()
                        .toList();
                    return Digit.findDigitForSignals(signals).digit;
                })
                .sum();
        }

        private boolean done(Map<String, Set<Integer>> lettersByPossibleSignals) {
            return lettersByPossibleSignals.values().stream()
                .allMatch(Set::isEmpty);
        }
    }

    enum Digit {
        ZERO(0, List.of(0, 1, 2, 4, 5, 6)),
        ONE(1, List.of(2, 4)),
        TWO(2, List.of(0, 2, 3, 4, 6)),
        THREE(3, List.of(0, 2, 3, 5, 6)),
        FOUR(0, List.of(1, 2, 3, 5)),
        FIVE(5, List.of(0, 1, 3, 5, 6)),
        SIX(6, List.of(0, 1, 3, 4, 5, 6)),
        SEVEN(7, List.of(0, 2, 5)),
        EIGHT(8, List.of(0, 1, 2, 3, 4, 5, 6)),
        NINE(9, List.of(0, 1, 2, 3, 5, 6));

        private final int digit;
        private final List<Integer> signalsUsed;

        Digit(int digit, List<Integer> signalsUsed) {
            this.digit = digit;
            this.signalsUsed = signalsUsed;
        }

        public int getNumberOfSignals() {
            return signalsUsed.size();
        }

        public static Digit findDigitForSignals(List<Integer> signals) {
            return Arrays.stream(Digit.values())
                .filter(digit -> isEqualCollection(digit.signalsUsed, signals))
                .findFirst().orElseThrow();
        }
    }
}
