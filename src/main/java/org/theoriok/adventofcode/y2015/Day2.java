package org.theoriok.adventofcode.y2015;

import static org.theoriok.adventofcode.util.Utils.splitToList;

import org.theoriok.adventofcode.Day;

import java.util.List;
import java.util.stream.Stream;

public class Day2 implements Day<Integer, Integer> {

    private final List<Box> boxes;

    public Day2(List<String> input) {
        boxes = input.stream()
            .map(Day2::toBox)
            .toList();
    }

    private static Box toBox(String line) {
        List<Integer> values = splitToList(line, "x", Integer::parseInt);
        return new Box(values.get(0), values.get(1), values.get(2));
    }

    @Override
    public Integer firstMethod() {
        return boxes.stream()
            .mapToInt(Box::wrappingPaperNeeded)
            .sum();
    }

    @Override
    public Integer secondMethod() {
        return boxes.stream()
            .mapToInt(Box::ribbonNeeded)
            .sum();
    }

    record Box(int length, int width, int height) {

        public int wrappingPaperNeeded() {
            return totalArea() + smallestSideArea();
        }

        private int totalArea() {
            return 2 * length * width + 2 * width * height + 2 * height * length;
        }

        private int smallestSideArea() {
            return Stream.of(length, width, height)
                .sorted()
                .limit(2)
                .reduce((side1, side2) -> side1 * side2)
                .orElse(0);
        }

        public int ribbonNeeded() {
            return volume() + smallestSidePerimeter();
        }

        private int volume() {
            return length * width * height;
        }

        private int smallestSidePerimeter() {
            return Stream.of(length, width, height)
                .sorted()
                .limit(2)
                .reduce((side1, side2) -> (side1 + side2) * 2)
                .orElse(0);
        }
    }
}
