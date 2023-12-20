package org.theoriok.adventofcode.y2023;

import org.theoriok.adventofcode.Day;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day7 implements Day<Long, Long> {

    private final List<Game> games;

    public Day7(List<String> input) {
        games = input.stream()
            .map(Game::fromString)
            .toList();
    }

    private record Game(Hand hand, int bet) implements Comparable<Game> {
        public static Game fromString(String input) {
            String[] split = input.split(" ");
            return new Game(Hand.fromString(split[0]), Integer.parseInt(split[1]));
        }

        @Override
        public int compareTo(Game other) {
            return other.hand.compareTo(hand);
        }
    }

    private record Hand(List<Card> cards) implements Comparable<Hand> {
        public Type getType() {
            Map<Card, Integer> collect = cards.stream()
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

        public static Hand fromString(String input) {
            String[] split = input.split("");
            return new Hand(Arrays.stream(split).map(Card::fromString).toList());
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
        public int compareTo(Hand other) {
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

    private record Card(String value) implements Comparable<Card> {

        public static Card fromString(String input) {
            return new Card(input);
        }

        @Override
        public int compareTo(Card other) {
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

    @Override
    public Long firstMethod() {
        var counter = new AtomicLong(1);
        return games.stream()
            .sorted()
            .mapToLong(game -> counter.getAndIncrement() * game.bet)
            .sum();
    }

    @Override
    public Long secondMethod() {
        return 0L;
    }
}
