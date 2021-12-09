package org.theoriok.adventofcode.y2021;

import static org.theoriok.adventofcode.y2021.Day8.Digit.EIGHT;
import static org.theoriok.adventofcode.y2021.Day8.Digit.FOUR;
import static org.theoriok.adventofcode.y2021.Day8.Digit.ONE;
import static org.theoriok.adventofcode.y2021.Day8.Digit.SEVEN;

import org.theoriok.adventofcode.Day;

import java.util.Arrays;
import java.util.List;

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

    private static final class Entry {
        public static final List<Integer> SIZES = List.of(
            ONE.getNumberOfSignals(),
            FOUR.getNumberOfSignals(),
            SEVEN.getNumberOfSignals(),
            EIGHT.getNumberOfSignals()
        );
        private final List<String> signals;
        private final List<String> output;

        private Entry(String input) {
            var split = input.split(" \\| ");
            signals = Arrays.stream(split[0].split(" ")).toList();
            output = Arrays.stream(split[1].split(" ")).toList();
        }

        public List<String> signals() {
            return signals;
        }

        public List<String> output() {
            return output;
        }

        public long countFirstMethodDigits() {
            return output.stream()
                .filter(string -> SIZES.contains(string.length()))
                .count();
        }
    }

    enum Digit {
        ZERO(List.of(0, 1, 2, 4, 5, 6)),
        ONE(List.of(2, 4)),
        TWO(List.of(0, 2, 3, 4, 6)),
        THREE(List.of(0, 2, 3, 5, 6)),
        FOUR(List.of(1, 2, 3, 5)),
        FIVE(List.of(0, 1, 3, 5, 6)),
        SIX(List.of(0, 1, 3, 4, 5, 6)),
        SEVEN(List.of(0, 2, 5)),
        EIGHT(List.of(0, 1, 2, 3, 4, 5, 6)),
        NINE(List.of(0, 1, 2, 3, 5, 6));

        private final List<Integer> signalsUsed;

        Digit(List<Integer> signalsUsed) {
            this.signalsUsed = signalsUsed;
        }

        public int getNumberOfSignals() {
            return signalsUsed.size();
        }
    }
}
