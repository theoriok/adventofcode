package org.theoriok.adventofcode.y2022;

import org.theoriok.adventofcode.Day;

import java.util.Arrays;
import java.util.List;

public class Day2 extends Day<Integer, Integer> {

    private final List<String> input;

    public Day2(List<String> input) {
        this.input = input;
    }

    @Override
    public Integer firstMethod() {
        return input.stream()
            .map(Day2::mapToMatch)
            .mapToInt(Match::getMyScore)
            .sum();
    }

    private static Match mapToMatch(String line) {
        String[] symbols = line.split(" ");
        return new Match(Shape.fromSymbol(symbols[0]), Shape.fromMySymbol(symbols[1]));
    }

    private record Match(
        Shape theirShape,
        Shape myShape
    ) {

        public int getMyScore() {
            return myShape.value + Result.getMatchScore(myShape, theirShape);
        }
    }

    private enum Shape {
        ROCK(1, "A", "X") {
            @Override
            public boolean winsAgainst(Shape theirShape) {
                return theirShape == SCISSORS;
            }
        },
        PAPER(2, "B", "Y") {
            @Override
            public boolean winsAgainst(Shape theirShape) {
                return theirShape == ROCK;
            }
        },
        SCISSORS(3, "C", "Z") {
            @Override
            public boolean winsAgainst(Shape theirShape) {
                return theirShape == PAPER;
            }
        };

        private final int value;
        private final String symbol;
        private final String mySymbol;

        public abstract boolean winsAgainst(Shape theirShape);

        Shape(int value, String symbol, String mySymbol) {
            this.value = value;
            this.symbol = symbol;
            this.mySymbol = mySymbol;
        }

        public static Shape fromSymbol(String symbol) {
            return Arrays.stream(values())
                .filter(shape -> shape.symbol.equals(symbol))
                .findFirst()
                .orElseThrow();
        }

        public static Shape fromMySymbol(String mySymbol) {
            return Arrays.stream(values())
                .filter(shape -> shape.mySymbol.equals(mySymbol))
                .findFirst()
                .orElseThrow();
        }
    }

    private enum Result {
        WIN(6, "X"),
        DRAW(3, "Y"),
        LOSE(0, "Z");

        private final int score;
        private final String symbol;

        Result(int score, String symbol) {
            this.score = score;
            this.symbol = symbol;
        }

        public static Result fromSymbol(String symbol) {
            return Arrays.stream(values())
                .filter(result -> result.symbol.equals(symbol))
                .findFirst()
                .orElseThrow();
        }

        private static int getMatchScore(Shape myShape, Shape theirShape) {
            Result result = getResult(myShape, theirShape);
            return result.score;
        }

        private static Result getResult(Shape myShape, Shape theirShape) {
            if (myShape.winsAgainst(theirShape)) {
                return Result.WIN;
            } else if (myShape == theirShape) {
                return Result.DRAW;
            } else {
                return Result.LOSE;
            }
        }
    }
}
