package org.theoriok.adventofcode.y2015;

import org.paukov.combinatorics3.Generator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theoriok.adventofcode.Day;

import java.util.Comparator;
import java.util.List;

public class Day24 implements Day<Long, Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day24.class);
    private final List<Integer> presentWeights;

    public Day24(List<String> input) {
        presentWeights = input.stream()
            .map(Integer::parseInt)
            .sorted(Comparator.reverseOrder())
            .toList();
    }

    @Override
    public Long firstMethod() {
        return balanceFor(3);
    }

    private long balanceFor(int parts) {
        var totalWeightSplitInEqualParts = sumList(presentWeights) / parts;
        for (int i = 1; i <= presentWeights.size() / 2; i++) {
            LOGGER.debug("Generating all combinations with {} elements", i);
            var quantum = Generator.combination(presentWeights)
                .simple(i)
                .stream()
                .filter(combo -> sumList(combo) == totalWeightSplitInEqualParts)
                .mapToLong(this::quantum)
                .min()
                .orElse(0L);
            if (quantum > 0) {
                return quantum;
            }
        }
        return 0L;
    }

    private long quantum(List<Integer> combination) {
        return combination.stream()
            .mapToLong(Integer::longValue)
            .reduce(1L, (weight1, weight2) -> weight1 * weight2);
    }

    private long sumList(List<Integer> list) {
        return list.stream()
            .mapToLong(Integer::longValue)
            .sum();
    }

    @Override
    public Long secondMethod() {
        return balanceFor(4);
    }
}
