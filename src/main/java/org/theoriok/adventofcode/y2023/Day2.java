package org.theoriok.adventofcode.y2023;

import org.theoriok.adventofcode.Day;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToLongFunction;

public class Day2 implements Day<Integer, Long> {

    private final List<Game> games;

    public Day2(List<String> input) {
        games = input.stream()
                .map(Game::fromString)
                .toList();
    }

    @Override
    public Integer firstMethod() {
        return games.stream()
                .filter(Game::isValid)
                .mapToInt(Game::number)
                .sum();
    }

    @Override
    public Long secondMethod() {
        return games.stream()
                .mapToLong(Game::power)
                .sum();
    }

    private record Game(int number, List<Round> rounds) {
        public static Game fromString(String string) {
            String[] firstSplit = string.split(": ");
            var number = Integer.parseInt(firstSplit[0].replace("Game ", ""));
            String[] secondSplit = firstSplit[1].split("; ");
            var rounds = Arrays.stream(secondSplit)
                    .map(Round::fromString)
                    .toList();
            return new Game(number, rounds);
        }

        public boolean isValid() {
            return rounds.stream()
                    .allMatch(Round::isValid);
        }

        public long power() {
            var minimumRed = findMinimumNeeded(Round::red);
            var minimumGreen = findMinimumNeeded(Round::green);
            var minimumBlue = findMinimumNeeded(Round::blue);
            return minimumRed * minimumGreen * minimumBlue;
        }

        private long findMinimumNeeded(ToLongFunction<Round> function) {
            return rounds.stream()
                    .sorted(Comparator.comparingLong(function).reversed())
                    .mapToLong(function)
                    .findFirst()
                    .orElse(0L);
        }
    }

    private record Round(int red, int green, int blue) {
        static final Round MAX_ROUND = new Round(12, 13, 14);

        public static Round fromString(String string) {
            String[] split = string.split(", ");
            var red = getForColor(split, " red");
            var green = getForColor(split, " green");
            var blue = getForColor(split, " blue");
            return new Round(red, green, blue);
        }

        private static int getForColor(String[] split, String color) {
            return Arrays.stream(split)
                    .filter(part -> part.contains(color))
                    .mapToInt(part -> Integer.parseInt(part.replace(color, "")))
                    .findFirst()
                    .orElse(0);
        }

        public boolean isValid() {
            return this.red <= MAX_ROUND.red && this.green <= MAX_ROUND.green && this.blue <= MAX_ROUND.blue;
        }
    }
}
