package org.theoriok.adventofcode.y2024;

import static org.theoriok.adventofcode.util.Utils.splitToList;

import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.List;

public class Day2 implements Day<Long, Long> {

    private final List<Report> reports;

    public Day2(List<String> input) {
        reports = input.stream()
            .map(line -> splitToList(line, " ", Integer::parseInt))
            .map(Report::new)
            .toList();
    }

    @Override
    public Long firstMethod() {
        return reports.stream()
            .filter(Report::safe)
            .count();
    }

    @Override
    public Long secondMethod() {
        return reports.stream()
            .filter(Report::safeWithDampening)
            .count();
    }

    record Report(List<Integer> levels) {
        public boolean safe() {
            return safe(levels);
        }

        private boolean safe(List<Integer> levels) {
            List<Integer> increases = new ArrayList<>();
            for (int i = 1; i < levels.size(); i++) {
                increases.add(levels.get(i) - levels.get(i - 1));
            }
            var isPositive = increases.getFirst() > 0;
            for (Integer increase : increases) {
                if (increase > 0 != isPositive) {
                    return false;
                }
                if (1 > Math.abs(increase) || Math.abs(increase) > 3) {
                    return false;
                }
            }
            return true;
        }

        public boolean safeWithDampening() {
            for (int i = 0; i < levels.size(); i++) {
               List<Integer> levelsWithOneRemoved = new ArrayList<>();
                for (int j = 0; j < levels.size(); j++) {
                    if (i != j) {
                        levelsWithOneRemoved.add(levels.get(j));
                    }
                }
                if (safe(levelsWithOneRemoved)) {
                    return true;
                }
            }
            return false;
        }
    }
}
