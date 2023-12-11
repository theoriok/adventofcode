package org.theoriok.adventofcode.y2023;

import org.apache.commons.collections4.ListUtils;
import org.theoriok.adventofcode.Day;

import java.util.Arrays;
import java.util.List;

public class Day4 implements Day<Long, Long> {

    private final List<Card> cards;

    public Day4(List<String> input) {
        cards = input.stream()
            .map(Card::fromString)
            .toList();
    }

    private record Card(int index, List<Integer> winningNumbers, List<Integer> numbers) {
        public static Card fromString(String input) {
            String[] firstSplit = input.split(":");
            var index = Integer.parseInt(firstSplit[0].replace("Card", "").trim());
            String[] secondSplit = firstSplit[1].split("\\|");
            var winningNumbers = splitOnSpaces(secondSplit[0]);
            var numbers = splitOnSpaces(secondSplit[1]);
            return new Card(index, winningNumbers, numbers);
        }

        private static List<Integer> splitOnSpaces(String input) {
            return Arrays.stream(input.trim().split(" +")).map(part -> Integer.parseInt(part.trim())).toList();
        }

        public long countPoints() {
            List<Integer> wonNumbers = ListUtils.intersection(winningNumbers, numbers);
            return !wonNumbers.isEmpty() ? (long) (Math.pow(2, wonNumbers.size() - 1.0)) : 0L;
        }

    }

    @Override
    public Long firstMethod() {
        return cards.stream()
            .mapToLong(Card::countPoints)
            .sum();
    }

    @Override
    public Long secondMethod() {
        return 0L;
    }
}
