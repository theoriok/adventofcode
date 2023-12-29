package org.theoriok.adventofcode.y2023;

import static org.theoriok.adventofcode.util.Utils.splitToList;

import org.theoriok.adventofcode.Day;

import java.util.List;
import java.util.function.Function;

public class Day15 implements Day<Long, Long> {

    private final List<String> hashedParts;

    public Day15(List<String> input) {
        hashedParts = splitToList(input.getFirst(), ",", Function.identity());
    }

    @Override
    public Long firstMethod() {
        return hashedParts.stream()
            .mapToLong(this::hash)
            .sum();
    }

    private long hash(String inputLine) {
        List<Integer> asciiValues = splitToList(inputLine, "", string -> (int) string.charAt(0));
        long value = 0L;
        for (Integer asciiValue : asciiValues) {
            value += asciiValue;
            value *= 17;
            value %= 256;
        }
        return value;
    }

    @Override
    public Long secondMethod() {
        return 0L;
    }
}
