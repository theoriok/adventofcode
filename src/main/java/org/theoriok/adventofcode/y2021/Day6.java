package org.theoriok.adventofcode.y2021;

import org.theoriok.adventofcode.Day;

import java.util.Arrays;
import java.util.List;

public class Day6 implements Day<Long, Long> {

    private final List<String> input;

    public Day6(List<String> input) {
        this.input = input;
    }

    @Override
    public Long firstMethod() {
        return doForXDays(80);
    }

    private long doForXDays(int nrDays) {
        long[] generations = new long[9];
        input.stream()
            .map(Short::parseShort)
            .forEach(day -> generations[day]++);
        for (int i = 0; i < nrDays; i++) {
            long spawners = generations[0];
            for (int j = 1; j < generations.length; j++) {
                generations[j - 1] = generations[j];
            }
            generations[6] += spawners;
            generations[8] = spawners;
        }
        return Arrays.stream(generations).sum();
    }

    @Override
    public Long secondMethod() {
        return doForXDays(256);
    }
}
