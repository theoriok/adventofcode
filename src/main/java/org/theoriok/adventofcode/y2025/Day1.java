package org.theoriok.adventofcode.y2025;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theoriok.adventofcode.Day;

import java.util.List;

public class Day1 implements Day<Integer, Integer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day1.class);
    private final List<Turn> turns;

    public Day1(List<String> input) {
        turns = input.stream()
            .map(Turn::from)
            .toList();
    }

    @Override
    public Integer firstMethod() {
        int dial = 50;
        int counter = 0;
        for (Turn turn : turns) {
            dial = turn.turn(dial);
            LOGGER.info("Dial: " + dial);
            if (dial == 0) {
                counter++;
            }
        }
        return counter;
    }

    @Override
    public Integer secondMethod() {
        int dial = 50;
        int counter = 0;
        for (Turn turn : turns) {
            var result = turn.turnBetter(dial);
            dial = result.getLeft();
            LOGGER.info("Dial: " + dial);
            if (dial == 0) {
                counter += result.getRight();
            }
        }
        return counter;
    }

    private record Turn(Direction direction, int amount) {
        public static Turn from(String input) {
            return new Turn(Direction.from(input.substring(0, 1)), Integer.parseInt(input.substring(1)));
        }

        public int turn(int dial) {
            return direction.turn(dial, amount);
        }

        public Pair<Integer, Integer> turnBetter(int dial) {
            return direction.turnBetter(dial, amount);
        }
    }

    private enum Direction {
        LEFT {
            @Override
            public int turn(int dial, int amount) {
                var newDial = dial - amount % 100;
                if (newDial < 0) {
                    newDial += 100;
                }
                return newDial;
            }

            @Override
            public Pair<Integer, Integer> turnBetter(int dial, int amount) {
                var newDial = dial - amount + 100;
                return Pair.of(newDial % 100, (newDial / 100)+1);
            }
        },
        RIGHT {
            @Override
            public int turn(int dial, int amount) {
                var newDial = dial + amount % 100;
                if (newDial >= 100) {
                    newDial -= 100;
                }
                return newDial;
            }

            @Override
            public Pair<Integer, Integer> turnBetter(int dial, int amount) {
                var newDial = dial + amount;
                return Pair.of(newDial % 100, newDial / 100);
            }
        };

        public static Direction from(String input) {
            return switch (input) {
                case "L" -> LEFT;
                case "R" -> RIGHT;
                default -> throw new IllegalStateException("Unexpected value: %s".formatted(input));
            };
        }

        public abstract int turn(int dial, int amount);

        public abstract Pair<Integer, Integer> turnBetter(int dial, int amount);
    }
}
