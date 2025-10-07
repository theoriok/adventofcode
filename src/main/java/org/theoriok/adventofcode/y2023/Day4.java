package org.theoriok.adventofcode.y2023;

import static org.theoriok.adventofcode.util.Utils.splitToList;

import org.apache.commons.collections4.ListUtils;
import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day4 implements Day<Integer, Integer> {

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
            var winningNumbers = splitOnSpaces(secondSplit[0].trim());
            var numbers = splitOnSpaces(secondSplit[1].trim());
            return new Card(index, winningNumbers, numbers);
        }

        private static List<Integer> splitOnSpaces(String input) {
            return splitToList(input, " +", part -> Integer.parseInt(part.trim()));
        }

        public static Card copy(Card card) {
            return new Card(card.index, card.winningNumbers, card.numbers);
        }

        public int countPoints() {
            List<Integer> wonNumbers = wonNumbers();
            return !wonNumbers.isEmpty() ? (int) (Math.pow(2, wonNumbers.size() - 1.0)) : 0;
        }

        private List<Integer> wonNumbers() {
            return ListUtils.intersection(winningNumbers, numbers);
        }

        public int numberOfWonNumbers() {
            return wonNumbers().size();
        }
    }

    @Override
    public Integer firstMethod() {
        return cards.stream()
            .mapToInt(Card::countPoints)
            .sum();
    }

    @Override
    public Integer secondMethod() {
        var finalCards = new HashMap<Integer, List<Card>>();
        for (Card card : cards) {
            finalCards.computeIfAbsent(card.index, ignore -> new ArrayList<>()).add(Card.copy(card));
            if (card.numberOfWonNumbers() > 0) {
                for (int i = card.index; i < Math.min(card.index + card.numberOfWonNumbers(), cards.size()); i++) {
                    for (int j = 0; j < finalCards.get(card.index).size(); j++) {
                        Card newCard = cards.get(i);
                        finalCards.computeIfAbsent(newCard.index, ignore -> new ArrayList<>()).add(Card.copy(newCard));
                    }
                }
            }
        }
        return finalCards.values().stream()
            .mapToInt(List::size)
            .sum();
    }
}
