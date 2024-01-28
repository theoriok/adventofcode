package org.theoriok.adventofcode.y2021;

import org.theoriok.adventofcode.Day;

import java.util.List;

public class Day2 implements Day<Integer, Integer> {

    private final List<String> input;

    public Day2(List<String> input) {
        this.input = input;
    }

    @Override
    @SuppressWarnings("PMD.SwitchStmtsShouldHaveDefault") // PMD does not recognize Java 13+ enhanced switch statements
    public Integer firstMethod() {
        var horizontal = 0;
        var depth = 0;
        for (String string : input) {
            var split = string.split(" ");
            var direction = Direction.valueOf(split[0].toUpperCase());
            var value = Integer.parseInt(split[1]);
            switch (direction) {
                case FORWARD -> horizontal += value;
                case DOWN -> depth += value;
                case UP -> depth -= value;
                default -> throw new IllegalStateException("Unexpected value: " + direction);
            }
        }
        return horizontal * depth;
    }

    @Override
    @SuppressWarnings("PMD.SwitchStmtsShouldHaveDefault") // PMD does not recognize Java 13+ enhanced switch statements
    public Integer secondMethod() {
        var horizontal = 0;
        var depth = 0;
        var aim = 0;
        for (String string : input) {
            var split = string.split(" ");
            var direction = Direction.valueOf(split[0].toUpperCase());
            var value = Integer.parseInt(split[1]);
            switch (direction) {
                case FORWARD -> {
                    depth += aim * value;
                    horizontal += value;
                }
                case DOWN -> aim += value;
                case UP -> aim -= value;
                default -> throw new IllegalStateException("Unexpected value: " + direction);
            }
        }
        return horizontal * depth;
    }

    private enum Direction {
        FORWARD,
        DOWN,
        UP
    }
}
