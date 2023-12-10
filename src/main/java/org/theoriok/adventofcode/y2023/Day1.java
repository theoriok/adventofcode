package org.theoriok.adventofcode.y2023;

import org.apache.commons.lang3.StringUtils;
import org.theoriok.adventofcode.Day;

import java.util.List;

public class Day1 implements Day<Long, Long> {

    private final List<String> input;

    public Day1(List<String> input) {
        this.input = input;
    }

    @Override
    public Long firstMethod() {
        return input.stream()
            .mapToLong(this::doStuff)
            .sum();
    }

    private long doStuff(String inputLine) {
        var nums = inputLine.replaceAll("[a-z]+", "").split("");
        return Long.parseLong(nums[0] + nums[nums.length - 1]);
    }

    @Override
    public Long secondMethod() {
        return input.stream()
            .map(this::doOtherStuffFirst)
            .mapToLong(this::doStuff)
            .sum();
    }

    private String doOtherStuffFirst(String inputLine) {
        return inputLine;
    }
}
