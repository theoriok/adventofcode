package org.theoriok.adventofcode.y2024;

import org.theoriok.adventofcode.Day;

import java.util.List;
import java.util.regex.Pattern;

public class Day3 implements Day<Integer, Integer> {

    private final List<String> input;

    public Day3(List<String> input) {
        this.input = input;
    }

    @Override
    public Integer firstMethod() {
        return getMultiSum(input.getFirst());
    }

    @Override
    public Integer secondMethod() {
        return getMultiSumConditional(input.getFirst());
    }

    private int getMultiSumConditional(String line) {
        var split = line.split("do\\(\\)");
        var counter = 0;
        for (var part : split) {
            counter += getMultiSum(part.split("don't\\(\\)")[0]);
        }
        return counter;
    }

    private int getMultiSum(String line) {
        var pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
        var matcher = pattern.matcher(line);
        var counter = 0;
        while (matcher.find()) {
            counter += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
        }
        return counter;
    }
}
