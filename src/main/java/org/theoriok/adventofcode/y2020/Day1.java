package org.theoriok.adventofcode.y2020;

import org.theoriok.adventofcode.Day;

import java.util.List;

public class Day1 extends Day {

    private final List<Integer> numbers;

    public Day1(List<String> input) {
        super(input);
        numbers = input.stream()
            .map(Integer::parseInt)
            .toList();
    }

    @Override
    public long firstMethod() {
        long num = numbers.stream()
            .filter(number -> numbers.contains(2020 - number))
            .findFirst()
            .orElseThrow();
        return num * (2020L - num);
    }
}
