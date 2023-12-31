package org.theoriok.adventofcode.y2023;

import static java.util.stream.Collectors.joining;

import org.apache.commons.lang3.ArraySorter;
import org.apache.commons.lang3.StringUtils;
import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day14 implements Day<Long, Long> {
    private final List<String> input;

    public Day14(List<String> input) {
        this.input = input;
    }

    @Override
    public Long firstMethod() {
        List<String> transposedInput = transpose(input);
        List<String> sortedTransposedLines = transposedInput.stream()
            .map(line -> Arrays.stream(line.split("#", -1)).map(this::sortString).collect(joining("#")))
            .toList();

        return calculateLoad(transpose(sortedTransposedLines));
    }

    private String sortString(String subLine) {
        return StringUtils.reverse(String.copyValueOf(ArraySorter.sort(subLine.toCharArray())));
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
        return 0L;
    }
}
