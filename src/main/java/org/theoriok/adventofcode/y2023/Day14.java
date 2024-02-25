package org.theoriok.adventofcode.y2023;

import static java.util.stream.Collectors.joining;

import org.apache.commons.lang3.ArraySorter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 implements Day<Long, Long> {

    public static final long ITERATIONS = 1_000_000_000L;

    private final List<String> input;

    public Day14(List<String> input) {
        this.input = input;
    }

    @Override
    public Long firstMethod() {
        List<String> sortedLines = tiltNorth(input);

        return calculateLoad(sortedLines);
    }

    private List<String> tiltNorth(List<String> lines) {
        List<String> transposedInput = transpose(lines);
        List<String> sortedTransposedLines = transposedInput.stream()
            .map(line -> Arrays.stream(line.split("#", -1))
                .map(this::sortString)
                .map(StringUtils::reverse)
                .collect(joining("#"))
            )
            .toList();
        return transpose(sortedTransposedLines);
    }

    private List<String> tiltWest(List<String> lines) {
        return lines.stream()
            .map(line -> Arrays.stream(line.split("#", -1))
                .map(this::sortString)
                .map(StringUtils::reverse)
                .collect(joining("#"))
            )
            .toList();
    }

    private List<String> tiltSouth(List<String> lines) {
        List<String> transposedInput = transpose(lines);
        List<String> sortedTransposedLines = transposedInput.stream()
            .map(line -> Arrays.stream(line.split("#", -1))
                .map(this::sortString)
                .collect(joining("#"))
            )
            .toList();
        return transpose(sortedTransposedLines);
    }

    private List<String> tiltEast(List<String> lines) {
        return lines.stream()
            .map(line -> Arrays.stream(line.split("#", -1))
                .map(this::sortString)
                .collect(joining("#"))
            )
            .toList();
    }

    private String sortString(String subLine) {
        return String.copyValueOf(ArraySorter.sort(subLine.toCharArray()));
    }

    public Long calculateLoad(List<String> lines) {
        return lines.stream()
            .mapToLong(line -> (lines.size() - lines.indexOf(line)) * countStones(line))
            .sum();
    }

    private long countStones(String line) {
        return line.replace(".", "").replace("#", "").length();
    }

    private List<String> transpose(List<String> input) {
        List<String> newStrings = new ArrayList<>();
        int size = input.getFirst().length();
        for (int i = 0; i < size; i++) {
            StringBuilder sb = new StringBuilder();
            for (String line : input) {
                sb.append(line.charAt(i));
            }
            newStrings.add(sb.toString());
        }
        return newStrings;
    }

    @Override
    public Long secondMethod() {
        List<String> result = input;
        Map<List<String>, List<String>> mapping = new HashMap<>();
        Map<Pair<List<String>, List<String>>, List<Long>> mappingNumbers = new HashMap<>();
        for (long i = 1; i < ITERATIONS; i++) {
            List<String> newResult;
            if (mapping.containsKey(result)) {
                newResult = mapping.get(result);
            } else {
                newResult = doTilts(result);
                mapping.put(result, newResult);
            }
            List<Long> numbers = mappingNumbers.computeIfAbsent(Pair.of(result, newResult), ignore -> new ArrayList<>());
            if (!numbers.isEmpty() && (ITERATIONS - numbers.getFirst()) % (i - numbers.getLast()) == 0) {
                return calculateLoad(newResult);
            }
            numbers.add(i);
            result = newResult;
        }
        return calculateLoad(result);
    }

    private List<String> doTilts(List<String> inputs) {
        List<String> result = tiltNorth(inputs);
        result = tiltWest(result);
        result = tiltSouth(result);
        result = tiltEast(result);
        return result;
    }
}
