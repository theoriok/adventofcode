package org.theoriok.adventofcode.y2023;

import static org.theoriok.adventofcode.util.Utils.splitToList;

import org.theoriok.adventofcode.Day;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day7 implements Day<Long, Long> {

    private final List<String> input;

    public Day7(List<String> input) {
        this.input = input;
    }

    private record Game1(Hand1 hand, int bet) implements Comparable<Game1> {
        public static Game1 fromString(String input) {
            String[] split = input.split(" ");
            return new Game1(Hand1.fromString(split[0]), Integer.parseInt(split[1]));
        }

        @Override
        public int compareTo(Game1 other) {
            return other.hand.compareTo(hand);
        }
    }

    private record Hand1(List<Card1> cards) implements Comparable<Hand1> {
        public Type getType() {
            Map<Card1, Integer> collect = cards.stream()
                .collect(Collectors.groupingBy(Function.identity()))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().size()));
            if (collect.containsValue(5)) {
                return Type.FIVE;
            } else if (collect.containsValue(4)) {
                return Type.FOUR;
            } else if (collect.containsValue(3) && collect.containsValue(2)) {
                return Type.FULL_HOUSE;
            } else if (collect.containsValue(3)) {
                return Type.THREE;
            } else if (collect.containsValue(2) && collect.values().size() == 3) {
                return Type.TWO_PAIR;
            } else if (collect.containsValue(2)) {
                return Type.PAIR;
            } else {
                return Type.HIGH;
            }
        }

        public static Hand1 fromString(String input) {
            return new Hand1(splitToCards(input));
        }

        private static List<Card1> splitToCards(String input) {
            return splitToList(input, "", Card1::fromString);
        }

        private enum Type {
            FIVE,
            FOUR,
            FULL_HOUSE,
            THREE,
            TWO_PAIR,
            PAIR,
            HIGH
        }

        @Override
        public int compareTo(Hand1 other) {
            int typeCompare = getType().compareTo(other.getType());
            if (typeCompare != 0) {
                return typeCompare;
            } else {
                int cardsCompare = 0;
                int index = 0;
                while (cardsCompare == 0 && index < cards.size()) {
                    cardsCompare = cards.get(index).compareTo(other.cards.get(index));
                    index++;
                }
                return cardsCompare;
            }
        }
    }

    private record Card1(String value) implements Comparable<Card1> {

        public static Card1 fromString(String input) {
            return new Card1(input);
        }

        @Override
        public int compareTo(Card1 other) {
            return other.getNumericValue().compareTo(getNumericValue());
        }

        private Integer getNumericValue() {
            return switch (value) {
                case "A" -> 14;
                case "K" -> 13;
                case "Q" -> 12;
                case "J" -> 11;
                case "T" -> 10;
                case "9" -> 9;
                case "8" -> 8;
                case "7" -> 7;
                case "6" -> 6;
                case "5" -> 5;
                case "4" -> 4;
                case "3" -> 3;
                case "2" -> 2;
                default -> throw new IllegalStateException("Unexpected value: " + value);
            };
        }
    }

    private record Game2(Hand2 hand, int bet) implements Comparable<Game2> {
        public static Game2 fromString(String input) {
            String[] split = input.split(" ");
            return new Game2(Hand2.fromString(split[0]), Integer.parseInt(split[1]));
        }

        @Override
        public int compareTo(Game2 other) {
            return other.hand.compareTo(hand);
        }
    }

    private record Hand2(List<Card2> originalCards, List<Card2> cards) implements Comparable<Hand2> {
        public Type getType() {
            Map<Card2, Integer> collect = cards.stream()
                .collect(Collectors.groupingBy(Function.identity()))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().size()));
            if (collect.containsValue(5)) {
                return Type.FIVE;
            } else if (collect.containsValue(4)) {
                return Type.FOUR;
            } else if (collect.containsValue(3) && collect.containsValue(2)) {
                return Type.FULL_HOUSE;
            } else if (collect.containsValue(3)) {
                return Type.THREE;
            } else if (collect.containsValue(2) && collect.values().size() == 3) {
                return Type.TWO_PAIR;
            } else if (collect.containsValue(2)) {
                return Type.PAIR;
            } else {
                return Type.HIGH;
            }
        }

        public static Hand2 fromString(String input) {
            List<Card2> originalCards = mapToCards(input);
            List<Card2> nonJokers = originalCards.stream().filter(card -> !card.value.equals("J")).toList();
            if (nonJokers.isEmpty()) {
                return new Hand2(originalCards, originalCards);
            }
            return nonJokers.stream()
                .map(card -> input.replace("J", card.value))
                .map(Hand2::mapToCards)
                .map(cards -> new Hand2(originalCards, cards))
                .reduce(((hand1, hand2) -> hand1.compareTo(hand2) > 0 ? hand2 : hand1))
                .orElseThrow();
        }

        private static List<Card2> mapToCards(String input) {
            return splitToList(input, "", Card2::fromString);
        }

        private enum Type {
            FIVE,
            FOUR,
            FULL_HOUSE,
            THREE,
            TWO_PAIR,
            PAIR,
            HIGH
        }

        @Override
        public int compareTo(Hand2 other) {
            int typeCompare = getType().compareTo(other.getType());
            if (typeCompare != 0) {
                return typeCompare;
            } else {
                int cardsCompare = 0;
                int index = 0;
                while (cardsCompare == 0 && index < originalCards.size()) {
                    cardsCompare = originalCards.get(index).compareTo(other.originalCards.get(index));
                    index++;
                }
                return cardsCompare;
            }
        }
    }

    private record Card2(String value) implements Comparable<Card2> {

        public static Card2 fromString(String input) {
            return new Card2(input);
        }

        @Override
        public int compareTo(Card2 other) {
            return other.getNumericValue().compareTo(getNumericValue());
        }

        private Integer getNumericValue() {
            return switch (value) {
                case "A" -> 14;
                case "K" -> 13;
                case "Q" -> 12;
                case "T" -> 10;
                case "9" -> 9;
                case "8" -> 8;
                case "7" -> 7;
                case "6" -> 6;
                case "5" -> 5;
                case "4" -> 4;
                case "3" -> 3;
                case "2" -> 2;
                case "J" -> 1;
                default -> throw new IllegalStateException("Unexpected value: " + value);
            };
        }
    }

    @Override
    public Long firstMethod() {
        List<Game1> games;
        games = this.input.stream()
            .map(Game1::fromString)
            .toList();
        var counter = new AtomicLong(1);
        return games.stream()
            .sorted()
            .mapToLong(game -> counter.getAndIncrement() * game.bet)
            .sum();
    }

    @Override
    public Long secondMethod() {
        List<Game2> games;
        games = this.input.stream()
            .map(Game2::fromString)
            .toList();
        var counter = new AtomicLong(1);
        return games.stream()
            .sorted()
            .mapToLong(game -> counter.getAndIncrement() * game.bet)
            .sum();
    }
}
