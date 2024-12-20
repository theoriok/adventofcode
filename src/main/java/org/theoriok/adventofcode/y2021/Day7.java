package org.theoriok.adventofcode.y2021;

import org.theoriok.adventofcode.Day;

import java.util.List;
import java.util.function.ToLongFunction;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.summarizingLong;

public class Day7 implements Day<Long, Long> {

    private final List<String> input;

    public Day7(List<String> input) {
        this.input = input;
    }

    @Override
    public Long firstMethod() {
        var crabs = initializeCrabs();
        var longSummaryStatistics = crabs.stream()
            .collect(summarizingLong(Crab::position));
        return LongStream.rangeClosed(longSummaryStatistics.getMin(), longSummaryStatistics.getMax())
            .map(position -> alignToPosition(crabs, crab -> crab.linearDistanceTo(position)))
            .min().orElse(0);
    }

    private List<Crab> initializeCrabs() {
        return input.stream()
            .map(Long::parseLong)
            .map(Crab::new)
            .toList();
    }

    public long alignToPosition(List<Crab> crabs, ToLongFunction<Crab> distance) {
        return crabs.stream()
            .mapToLong(distance)
            .sum();
    }

    @Override
    public Long secondMethod() {
        var crabs = initializeCrabs();
        var longSummaryStatistics = crabs.stream()
            .collect(summarizingLong(Crab::position));
        return LongStream.rangeClosed(longSummaryStatistics.getMin(), longSummaryStatistics.getMax())
            .map(position -> alignToPosition(crabs, crab -> crab.exponentialDistanceTo(position)))
            .min().orElse(0);
    }

    record Crab(long position) {
        public long linearDistanceTo(long position) {
            return Math.abs(this.position - position);
        }

        private long exponentialDistanceTo(long position) {
            var linearDistance = linearDistanceTo(position);
            if (linearDistance == 0) {
                return 0;
            }
            return LongStream.rangeClosed(1, linearDistance)
                .sum();
        }
    }
}
