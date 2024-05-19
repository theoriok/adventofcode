package org.theoriok.adventofcode.y2015;

import com.google.common.collect.Collections2;
import org.theoriok.adventofcode.Day;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Day9 implements Day<Integer, Integer> {

    private final Map<String, Short> locationsToIndex;
    private final short[][] distances;

    public Day9(List<String> input) {
        locationsToIndex = new HashMap<>();
        AtomicInteger index = new AtomicInteger(0);
        allLocations(input, index);
        distances = new short[locationsToIndex.size()][locationsToIndex.size()];
        distances(input);
    }

    private void allLocations(List<String> input, AtomicInteger index) {
        for (String line : input) {
            String csvLine = line.replace(" to ", ";").replace(" = ", ";");
            String[] split = csvLine.split(";");
            String from = split[0];
            String to = split[1];
            locationsToIndex.computeIfAbsent(from, ___ -> (short) index.getAndIncrement());
            locationsToIndex.computeIfAbsent(to, ___ -> (short) index.getAndIncrement());
        }
    }

    private void distances(List<String> input) {
        for (String line : input) {
            String csvLine = line.replace(" to ", ";").replace(" = ", ";");
            String[] split = csvLine.split(";");
            String from = split[0];
            String to = split[1];
            short distance = Short.parseShort(split[2]);
            short fromIndex = locationsToIndex.get(from);
            short toIndex = locationsToIndex.get(to);
            distances[fromIndex][toIndex] = distance;
            distances[toIndex][fromIndex] = distance;
        }
    }

    @Override
    public Integer firstMethod() {
        return Collections2.permutations(locationsToIndex.values()).parallelStream()
                .mapToInt(this::totalDistance)
                .min()
                .orElse(0);
    }

    private int totalDistance(List<Short> locationIndexes) {
        int totalDistance = 0;
        for (int i = 1; i < locationIndexes.size(); i++) {
            totalDistance += distances[locationIndexes.get(i - 1)][locationIndexes.get(i)];
        }
        return totalDistance;
    }

    @Override
    public Integer secondMethod() {
        return Collections2.permutations(locationsToIndex.values()).parallelStream()
                .mapToInt(this::totalDistance)
                .max()
                .orElse(0);
    }
}
