package org.theoriok.adventofcode.y2015;

import static org.theoriok.adventofcode.util.Utils.splitToList;

import org.theoriok.adventofcode.Day;

import java.util.List;
import java.util.function.Function;

public class Day1 implements Day<Long, Integer> {

    private final List<String> floorInstructions;

    public Day1(List<String> input) {
        floorInstructions = splitToList(input.getFirst(), "", Function.identity());
    }

    @Override
    public Long firstMethod() {
        return floorInstructions.stream()
            .mapToLong(this::valueOf)
            .sum();
    }

    private long valueOf(String character) {
        return switch (character) {
            case "(" -> 1;
            case ")" -> -1;
            default -> 0;
        };
    }

    @Override
    public Integer secondMethod() {
        long floor = 0;
        for (int i = 0; i < floorInstructions.size(); i++) {
            floor += valueOf(floorInstructions.get(i));
            if (floor < 0) {
                return i + 1;
            }
        }
        return 0;
    }
}
