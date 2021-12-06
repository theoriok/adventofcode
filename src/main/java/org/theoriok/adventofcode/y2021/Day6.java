package org.theoriok.adventofcode.y2021;

import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Day6 extends Day {

    private final List<Short> fishes;

    protected Day6(List<String> input) {
        super(input);
        fishes = new ArrayList<>(
            input.stream()
                .map(Short::parseShort)
                .toList()
        );
    }

    @Override
    public long firstMethod() {
        return doForXDays(80);
    }

    private long doForXDays(int nrDays) {
        for (int i = 0; i < nrDays; i++) {
            List<Short> newFishes = new ArrayList<>();
            for (int j = 0; j < fishes.size(); j++) {
                Short fish = fishes.get(j);
                if (fish == 0) {
                    newFishes.add((short) 8);
                    fishes.set(j, (short) 6);
                } else {
                    fishes.set(j, (short) (fish - (short) 1));
                }
            }
            fishes.addAll(newFishes);
        }
        return fishes.size();
    }

    @Override
    public long secondMethod() {
        return doForXDays(256);
    }
}
