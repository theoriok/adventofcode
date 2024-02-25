package org.theoriok.adventofcode.y2023;

import static org.theoriok.adventofcode.util.Utils.splitToList;

import org.theoriok.adventofcode.Day;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SequencedMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day15 implements Day<Long, Long> {

    private final List<String> hashedParts;

    public Day15(List<String> input) {
        hashedParts = splitToList(input.getFirst(), ",", Function.identity());
    }

    private record Lens(String label, int focalStrength) {
    }

    private static class Box {
        private final SequencedMap<String, Lens> lenses = new LinkedHashMap<>();

        public void add(Lens lens) {
            lenses.put(lens.label, lens);
        }

        public void remove(String label) {
            lenses.remove(label);
        }
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
        Map<Long, Box> boxes = new HashMap<>();
        for (String step : hashedParts) {
            if (step.contains("=")) {
                String[] split = step.split("=");
                Lens lens = new Lens(split[0], Integer.parseInt(split[1]));
                boxes.computeIfAbsent(hash(lens.label), ignore -> new Box()).add(lens);
            } else if (step.contains("-")) {
                String label = step.replace("-", "");
                boxes.computeIfAbsent(hash(label), ignore -> new Box()).remove(label);
            }
        }
        return boxes.entrySet().stream()
            .mapToLong(entry -> calculateValue(entry.getKey() + 1, entry.getValue()))
            .sum();
    }

    private long calculateValue(long boxNumber, Box box) {
        Map<Integer, Lens> numberedLenses = IntStream.range(0, box.lenses.sequencedValues().size())
            .boxed()
            .collect(Collectors.toMap(index -> index + 1, index -> box.lenses.sequencedValues().removeFirst()));
        return numberedLenses.entrySet().stream()
            .mapToLong(entry -> boxNumber * entry.getKey() * entry.getValue().focalStrength)
            .sum();
    }
}
