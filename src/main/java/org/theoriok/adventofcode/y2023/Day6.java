package org.theoriok.adventofcode.y2023;

import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.List;

public class Day6 implements Day<Long, Long> {

    private final List<String> input;

    public Day6(List<String> input) {

        this.input = input;
    }

    private record Race(long time, long distance) {

        public long countWinningTimes() {
            int counter = 0;
            for (int i = 1; i < time; i++) {
                if ((i * (time - i)) > distance) {
                    counter++;
                }
            }
            return counter;
        }
    }

    @Override
    public Long firstMethod() {
        List<Race> races = new ArrayList<>();
        String[] times = input.getFirst().replace("Time:", "").trim().split(" +");
        String[] distances = input.getLast().replace("Distance:", "").trim().split(" +");
        for (int i = 0; i < times.length; i++) {
            races.add(new Race(Integer.parseInt(times[i]), Integer.parseInt(distances[i])));
        }
        return races.stream()
            .mapToLong(Race::countWinningTimes)
            .reduce((i1, i2) -> i1 * i2)
            .orElse(0);
    }

    @Override
    public Long secondMethod() {
        String time = input.getFirst().replace("Time:", "").replaceAll(" +", "");
        String distance = input.getLast().replace("Distance:", "").replaceAll(" +", "");
        return new Race(Long.parseLong(time), Long.parseLong(distance)).countWinningTimes();
    }
}
