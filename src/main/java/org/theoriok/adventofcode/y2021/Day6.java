package org.theoriok.adventofcode.y2021;

import org.theoriok.adventofcode.Day;

import java.util.Arrays;
import java.util.List;

public class Day6 extends Day {

    protected Day6(List<String> input) {
        super(input);
    }

    @Override
    public long firstMethod() {
        return doForXDays(80);
    }

    private long doForXDays(int nrDays) {
        long[] generations = new long[9];
        input.stream()
            .map(Short::parseShort)
            .forEach(day -> generations[day]++);
        for (int i = 0; i < nrDays; i++) {
            long z = generations[0];
            for (int j = 1; j < generations.length; j++) {
                generations[j - 1] = generations[j];
            }
            generations[6] += z;
            generations[8] = z;
        }
        return Arrays.stream(generations).sum();
    }

    @Override
    public long secondMethod() {
        return doForXDays(256);
    }
}
