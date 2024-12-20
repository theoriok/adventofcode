package org.theoriok.adventofcode.y2023;

import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.isDigit;

public class Day3 implements Day<Long, Long> {

    private final List<Symbol> symbols = new ArrayList<>();
    private final List<Number> numbers = new ArrayList<>();

    public Day3(List<String> input) {
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            List<Point> locations = new ArrayList<>();
            StringBuilder number = new StringBuilder();
            for (int j = 0; j < line.length(); j++) {
                char character = line.toCharArray()[j];
                if (isDigit(character)) {
                    number.append(character);
                    locations.add(new Point(j, i));
                } else {
                    if (!locations.isEmpty()) {
                        numbers.add(new Number(locations, Long.parseLong(number.toString())));
                        locations = new ArrayList<>();
                        number = new StringBuilder();
                    }
                    if (character != '.') {
                        symbols.add(new Symbol(new Point(j, i), character));
                    }
                }
            }
            if (!locations.isEmpty()) {
                numbers.add(new Number(locations, Long.parseLong(number.toString())));
            }
        }
    }

    @Override
    public Long firstMethod() {
        return numbers.stream()
            .filter(number -> number.isPartNumber(symbols))
            .mapToLong(Number::number)
            .sum();
    }

    @Override
    public Long secondMethod() {
        return symbols.stream()
            .filter(Symbol::couldBeGear)
            .mapToLong(symbol -> symbol.calculateGearRatio(numbers))
            .sum();
    }

    private record Symbol(Point location, char character) {
        public boolean couldBeGear() {
            return character == '*';
        }

        public long calculateGearRatio(List<Number> numbers) {
            var gearNumbers = numbers.stream()
                .filter(number -> location.isAdjacentToAny(number.locations))
                .toList();
            if (gearNumbers.size() == 2) {
                return gearNumbers.getFirst().number * gearNumbers.getLast().number;
            }
            return 0;
        }
    }

    private record Number(List<Point> locations, long number) {
        boolean isPartNumber(List<Symbol> symbols) {
            return symbols.stream()
                .anyMatch(symbol -> symbol.location.isAdjacentToAny(locations));
        }
    }

    private record Point(int x, int y) {
        public boolean isAdjacentToAny(List<Point> points) {
            return points.stream()
                .anyMatch(this::isAdjacentTo);
        }

        public boolean isAdjacentTo(Point point) {
            return Math.abs(point.x - x) <= 1 && Math.abs(point.y - y) <= 1;
        }
    }
}
