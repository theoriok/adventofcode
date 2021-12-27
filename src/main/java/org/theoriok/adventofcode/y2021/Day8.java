package org.theoriok.adventofcode.y2021;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;
import static org.theoriok.adventofcode.y2021.Day8.Digit.DIGITS_WITH_UNIQUE_SIZE;
import static org.theoriok.adventofcode.y2021.Day8.Digit.FIVE;
import static org.theoriok.adventofcode.y2021.Day8.Digit.FOUR;
import static org.theoriok.adventofcode.y2021.Day8.Digit.NINE;
import static org.theoriok.adventofcode.y2021.Day8.Digit.SEVEN;
import static org.theoriok.adventofcode.y2021.Day8.Digit.SIX;
import static org.theoriok.adventofcode.y2021.Day8.Digit.THREE;
import static org.theoriok.adventofcode.y2021.Day8.Digit.TWO;
import static org.theoriok.adventofcode.y2021.Day8.Digit.ZERO;

import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day8 extends Day<Long, Long> {

    public Day8(List<String> input) {
        super(input);
    }

    @Override
    public Long firstMethod() {
        return input.stream()
            .map(Entry::new)
            .mapToLong(Entry::countFirstMethodDigits)
            .sum();
    }

    @Override
    public Long secondMethod() {
        return input.stream()
            .map(Entry::new)
            .mapToLong(Entry::decodeNew)
            .sum();
    }

    private static final class Entry {
        private final List<String> digitSignals;
        private final List<String> output;

        private Entry(String input) {
            var split = input.split(" \\| ");
            digitSignals = Arrays.stream(split[0].split(" "))
                .map(this::normalize)
                .toList();
            output = Arrays.stream(split[1].split(" "))
                .map(this::normalize)
                .collect(Collectors.toCollection(ArrayList::new));
        }

        private String normalize(String string) {
            return Arrays.stream(string.split("")).sorted().collect(joining());
        }

        public long countFirstMethodDigits() {
            var uniqueLengths = DIGITS_WITH_UNIQUE_SIZE.stream()
                .map(Digit::getNumberOfSignals)
                .toList();
            return output.stream()
                .filter(string -> uniqueLengths.contains(string.length()))
                .count();
        }

        public int decodeNew() {
            Map<Digit, String> digitToNormalizedString = new HashMap<>();
            var digitSignalsByLength = digitSignals.stream()
                .collect(groupingBy(String::length));
            mapDigitsWithUniqueSize(digitToNormalizedString, digitSignalsByLength);
            mapSix(digitToNormalizedString, digitSignalsByLength);
            mapThree(digitToNormalizedString, digitSignalsByLength);
            mapFive(digitToNormalizedString, digitSignalsByLength);
            mapTwo(digitToNormalizedString, digitSignalsByLength);
            mapNine(digitToNormalizedString, digitSignalsByLength);
            mapZero(digitToNormalizedString, digitSignalsByLength);

            Map<String, Digit> normalizedStringToDigit = digitToNormalizedString.entrySet().stream()
                .collect(toMap(Map.Entry::getValue, Map.Entry::getKey));
            StringBuilder sb = new StringBuilder();
            for (String s : output) {
                Digit digit = normalizedStringToDigit.get(s);
                String toString = digit.getNumber().toString();
                sb.append(toString);
            }
            var AsString = sb.toString();
            return Integer.parseInt(AsString);
        }

        private void mapSix(Map<Digit, String> digitToNormalizedString, Map<Integer, List<String>> digitSignalsByLength) {
            var digitSignalsLengthSix = digitSignalsByLength.get(6);
            var seven = digitToNormalizedString.get(SEVEN);
            digitSignalsLengthSix.stream()
                .filter(signal -> {
                    var sevenAsStrings = stringToSet(seven);
                    sevenAsStrings.removeAll(stringToSet(signal));
                    return !sevenAsStrings.isEmpty();
                })
                .findFirst()
                .ifPresent(six -> {
                    digitSignalsLengthSix.remove(six);
                    digitToNormalizedString.put(SIX, six);
                });
        }

        private void mapThree(Map<Digit, String> digitToNormalizedString, Map<Integer, List<String>> digitSignalsByLength) {
            var digitSignalsLengthFive = digitSignalsByLength.get(5);
            var seven = digitToNormalizedString.get(SEVEN);
            digitSignalsLengthFive.stream()
                .filter(signal -> {
                    var sevenAsStrings = stringToSet(seven);
                    sevenAsStrings.removeAll(stringToSet(signal));
                    return !sevenAsStrings.isEmpty();
                })
                .findFirst()
                .ifPresent(three -> {
                    digitSignalsLengthFive.remove(three);
                    digitToNormalizedString.put(THREE, three);
                });
        }

        private void mapFive(Map<Digit, String> digitToNormalizedString, Map<Integer, List<String>> digitSignalsByLength) {
            var digitSignalsLengthFive = digitSignalsByLength.get(5);
            var six = digitToNormalizedString.get(SIX);
            digitSignalsLengthFive.stream()
                .filter(signal -> {
                    var sixAsStrings = stringToSet(six);
                    sixAsStrings.removeAll(stringToSet(signal));
                    return !sixAsStrings.isEmpty();
                })
                .findFirst()
                .ifPresent(five -> {
                    digitSignalsLengthFive.remove(five);
                    digitToNormalizedString.put(FIVE, five);
                });
        }

        private void mapTwo(Map<Digit, String> digitToNormalizedString, Map<Integer, List<String>> digitSignalsByLength) {
            var digitSignalsLengthFive = digitSignalsByLength.get(5);
            digitToNormalizedString.put(TWO, digitSignalsLengthFive.get(0));
        }

        private void mapNine(Map<Digit, String> digitToNormalizedString, Map<Integer, List<String>> digitSignalsByLength) {
            var digitSignalsLengthSix = digitSignalsByLength.get(6);
            var four = digitToNormalizedString.get(FOUR);
            digitSignalsLengthSix.stream()
                .filter(signal -> {
                    var fourAsStrings = stringToSet(four);
                    fourAsStrings.removeAll(stringToSet(signal));
                    return !fourAsStrings.isEmpty();
                })
                .findFirst()
                .ifPresent(nine -> {
                    digitSignalsLengthSix.remove(nine);
                    digitToNormalizedString.put(NINE, nine);
                });
        }

        private void mapZero(Map<Digit, String> digitToNormalizedString, Map<Integer, List<String>> digitSignalsByLength) {
            var digitSignalsLengthSix = digitSignalsByLength.get(6);
            digitToNormalizedString.put(ZERO, digitSignalsLengthSix.get(0));
        }

        private HashSet<String> stringToSet(String signal) {
            return new HashSet<>(Arrays.asList(signal.split("")));
        }

        private void mapDigitsWithUniqueSize(Map<Digit, String> digitToNormalizedString, Map<Integer, List<String>> digitSignalsByLength) {
            for (Digit digit : DIGITS_WITH_UNIQUE_SIZE) {
                digitToNormalizedString.put(digit, digitSignalsByLength.get(digit.getNumberOfSignals()).get(0));
            }
        }
    }

    enum Digit {
        ZERO(0, List.of(0, 1, 2, 4, 5, 6)),
        ONE(1, List.of(2, 4)),
        TWO(2, List.of(0, 2, 3, 4, 6)),
        THREE(3, List.of(0, 2, 3, 5, 6)),
        FOUR(4, List.of(1, 2, 3, 5)),
        FIVE(5, List.of(0, 1, 3, 5, 6)),
        SIX(6, List.of(0, 1, 3, 4, 5, 6)),
        SEVEN(7, List.of(0, 2, 5)),
        EIGHT(8, List.of(0, 1, 2, 3, 4, 5, 6)),
        NINE(9, List.of(0, 1, 2, 3, 5, 6));

        public static final List<Digit> DIGITS_WITH_UNIQUE_SIZE = List.of(ONE, FOUR, SEVEN, EIGHT);

        private final int number;
        private final int size;

        Digit(int number, List<Integer> signalsUsed) {
            this.number = number;
            size = signalsUsed.size();
        }

        public int getNumberOfSignals() {
            return size;
        }

        public Integer getNumber() {
            return number;
        }
    }
}
