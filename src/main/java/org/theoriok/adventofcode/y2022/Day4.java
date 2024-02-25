package org.theoriok.adventofcode.y2022;

import com.google.common.collect.Range;
import org.theoriok.adventofcode.Day;

import java.util.List;

public class Day4 implements Day<Long, Long> {

    private final List<Assignments> assignments;

    public Day4(List<String> input) {
        assignments = input.stream()
            .map(this::toAssignments)
            .toList();
    }

    private Assignments toAssignments(String line) {
        var parts = line.split(",");
        return new Assignments(toAssignment(parts[0]), toAssignment(parts[1]));
    }

    private Assignment toAssignment(String part) {
        var parts = part.split("-");
        return new Assignment(Range.closed(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
    }

    @Override
    public Long firstMethod() {
        return assignments.stream()
            .filter(Assignments::fullyContained)
            .count();
    }

    @Override
    public Long secondMethod() {
        return assignments.stream()
            .filter(Assignments::overlaps)
            .count();
    }

    private record Assignment(Range<Integer> range) {
        public boolean encloses(Assignment other) {
            return range.encloses(other.range);
        }

        public boolean overlaps(Assignment other) {
            return range.isConnected(other.range);
        }
    }

    private record Assignments(
        Assignment first,
        Assignment second
    ) {
        boolean fullyContained() {
            return first.encloses(second) || second.encloses(first);
        }

        public boolean overlaps() {
            return first.overlaps(second);
        }
    }
}
