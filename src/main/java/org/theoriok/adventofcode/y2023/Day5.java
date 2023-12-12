package org.theoriok.adventofcode.y2023;

import org.theoriok.adventofcode.Day;

import java.util.Arrays;
import java.util.List;

public class Day5 implements Day<Long, Long> {

    public Day5(List<String> input) {
        var seeds = Arrays.stream(input.getFirst().replace("seeds: ", "").split(" ")).map(Long::parseLong).toList();
    }

    @Override
    public Long firstMethod() {
        return 0L;
    }

    @Override
    public Long secondMethod() {
        return 0L;
    }
}
