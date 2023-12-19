package org.theoriok.adventofcode.y2023;

import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.List;

public class Day6 implements Day<Integer, Long> {

    private final List<Race> races;

    public Day6(List<String> input) {
        races = new ArrayList<>();
        String[] times = input.getFirst().replace("Time:", "").trim().split(" +");
        String[] distances = input.getLast().replace("Distance:", "").trim().split(" +");
        for (int i = 0; i < times.length; i++) {
            races.add(new Race(Integer.parseInt(times[i]), Integer.parseInt(distances[i])));
        }
    }

    private record Race(int time, int distance) {
        public int countWinningTimes() {
            int counter = 0;

            return counter;
        }
    }

    @Override
    public Integer firstMethod() {
        return races.stream()
            .mapToInt(Race::countWinningTimes)
            .reduce((i1, i2) -> i1 * i2)
            .orElse(0);
    }

    @Override
    public Long secondMethod() {

        return 0L;
    }
}
