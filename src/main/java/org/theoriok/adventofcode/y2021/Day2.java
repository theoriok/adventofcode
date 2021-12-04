package org.theoriok.adventofcode.y2021;

import org.theoriok.adventofcode.Day;

import java.util.List;

public class Day2 extends Day {

    public Day2(List<String> input) {
        super(input);
    }

    @Override
    public int firstMethod() {
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
            }
        }
        return horizontal * depth;
    }

    @Override
    public int secondMethod() {
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
