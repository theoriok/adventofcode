package org.theoriok.adventofcode.y2023;

import org.theoriok.adventofcode.Day;

import java.util.Arrays;
import java.util.List;

public class Day12 implements Day<Integer, Integer> {

    private final List<Line> lines;

    public Day12(List<String> input) {
        lines = input.stream()
            .map(Line::fromString)
            .toList();
    }

    private record Line(String pattern, List<Integer> brokenGroups) {

        public static Line fromString(String input) {
            String[] split = input.split(" ");
            List<Integer> brokenGroups = Arrays.stream(split[1].split(","))
                .map(Integer::parseInt)
                .toList();
            return new Line(split[0], brokenGroups);
        }

        public int possibilities() {
            return 1;
        }
    }

    @Override
    public Integer firstMethod() {
        return lines.stream()
            .mapToInt(Line::possibilities)
            .sum();
    }

    @Override
    public Integer secondMethod() {
        return 0;
    }
}
