package org.theoriok.adventofcode.y2021;

import org.theoriok.adventofcode.Day;

import java.util.List;

public class Day7 extends Day {
    protected Day7(List<String> input) {
        super(input);
    }

    @Override
    public long firstMethod() {
        var crabs = input.stream()
            .map(Integer::parseInt)
            .map(Crab::new)
            .toList();
        return crabs.stream()
            .mapToLong(crab -> crab.linearAlignOther(crabs))
            .min().orElse(0);
    }

    @Override
    public long secondMethod() {
        return super.secondMethod();
    }

    private static record Crab(int position) {

        public long linearAlignOther(List<Crab> crabs) {
            return crabs.stream()
                .mapToLong(this::distanceTo)
                .sum();
        }

        private long distanceTo(Crab crab) {
            return Math.abs(crab.position - position);
        }
    }
}
