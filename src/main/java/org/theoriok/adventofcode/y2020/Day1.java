package org.theoriok.adventofcode.y2020;

import org.theoriok.adventofcode.Day;

import java.util.List;

public class Day1 implements Day<Long, Long> {

    private final List<Integer> numbers;

    public Day1(List<String> input) {
        numbers = input.stream()
            .map(Integer::parseInt)
            .toList();
    }

    @Override
    public Long firstMethod() {
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = i + 1; j < numbers.size(); j++) {
                if (numbers.get(i) + numbers.get(j) == 2020) {
                    return (long) numbers.get(i) * numbers.get(j);
                }
            }
        }
        return 0L;
    }

    @Override
    public Long secondMethod() {
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = i + 1; j < numbers.size(); j++) {
                for (int k = j + 1; k < numbers.size(); k++) {
                    if (numbers.get(i) + numbers.get(j) + numbers.get(k) == 2020) {
                        return (long) numbers.get(i) * numbers.get(j) * numbers.get(k);
                    }
                }
            }
        }
        return 0L;
    }
}
