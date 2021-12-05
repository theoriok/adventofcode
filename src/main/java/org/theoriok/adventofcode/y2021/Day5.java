package org.theoriok.adventofcode.y2021;

import static java.util.stream.Collectors.*;
import static org.apache.commons.lang3.StringUtils.countMatches;
import static org.apache.commons.lang3.StringUtils.isBlank;

import org.theoriok.adventofcode.Day;

import java.util.List;
import java.util.Objects;

public class Day5 extends Day {
    public Day5(List<String> input) {
        super(input);
    }

    @Override
    public int firstMethod() {
        var lines = input.stream()
            .map(Line::fromString)
            .filter(Objects::nonNull)
            .toList();

        return super.firstMethod();
    }

    @Override
    public int secondMethod() {
        return super.secondMethod();
    }

    private record Point(int x, int y) {
        public static final String SPLIT = ",";

        public static Point fromString(String s) {
            if (isBlank(s) || countMatches(s, SPLIT) != 1) {
                return null;
            }
            var coords = s.split(SPLIT);
            return new Point(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
        }
    }

    private record Line(Point p1, Point p2) {

        public static final String SPLIT = " -> ";

        public static Line fromString(String s) {
            if (isBlank(s) || countMatches(s, SPLIT) != 1) {
                return null;
            }
            var points = s.split(SPLIT);
            return new Line(Point.fromString(points[0]), Point.fromString(points[1]));
        }

        public boolean horizontal() {
            return p1.x == p2.x;
        }

        public boolean vertical() {
            return p1.y == p2.y;
        }
    }
}
