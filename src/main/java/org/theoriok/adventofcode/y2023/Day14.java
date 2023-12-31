package org.theoriok.adventofcode.y2023;

import static java.util.stream.Collectors.joining;

import org.apache.commons.lang3.ArraySorter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day14 implements Day<Long, Long> {

    private static final Logger logger = LoggerFactory.getLogger(Day14.class);

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
        for (long i = 0; i < 1_000_000_000L; i++) {
            result = tiltNorth(result);
            result = tiltWest(result);
            result = tiltSouth(result);
            result = tiltEast(result);
            logger.info("Iteration: {}", i);
        }
        return calculateLoad(result);
    }
}
