package org.theoriok.adventofcode.y2021;

import static org.apache.commons.lang3.StringUtils.countMatches;
import static org.apache.commons.lang3.StringUtils.isBlank;

import org.theoriok.adventofcode.Day;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day5 extends Day {

    private final List<Line> lines;
    private final int fieldSize;

    public Day5(List<String> input) {
        super(input);
        lines = input.stream()
            .map(Line::fromString)
            .filter(Objects::nonNull)
            .toList();
        int max = lines.stream()
            .flatMap(line -> Stream.of(line.p1, line.p2))
            .flatMapToInt(point -> IntStream.of(point.x, point.y))
            .max().orElse(0);
        fieldSize = max + 1;
    }

    @Override
    public long firstMethod() {
        var field = new Field(new int[fieldSize][fieldSize]);
        lines.stream()
            .filter(((Predicate<Line>) Line::horizontal).or(Line::vertical))
            .forEach(field::updateVents);
        return nrOfCrossedPoints(field);
    }

    private long nrOfCrossedPoints(Field field) {
        var result = 0;
        for (int[] rows : field.field) {
            for (int point : rows) {
                if (point > 1) {
                    result++;
                }
            }
        }
        return result;
    }

    @Override
    public long secondMethod() {
        var field = new Field(new int[fieldSize][fieldSize]);
        lines.forEach(field::updateVents);
        return nrOfCrossedPoints(field);
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
            return p1.y == p2.y;
        }

        public boolean vertical() {
            return p1.x == p2.x;
        }

        public boolean diagonal() {
            return biggest(Point::x) - smallest(Point::x) == biggest(Point::y) - smallest(Point::y);
        }

        public int biggest(ToIntFunction<Point> getter) {
            return Math.max(getter.applyAsInt(p1), getter.applyAsInt(p2));
        }

        public int smallest(ToIntFunction<Point> getter) {
            return Math.min(getter.applyAsInt(p1), getter.applyAsInt(p2));
        }
    }

    private record Field(int[][] field) {
        public void updateVents(Line line) {
            if (line.horizontal()) {
                for (int i = line.smallest(Point::x); i <= line.biggest(Point::x); i++) {
                    field[i][line.p1.y]++;
                }
            }
            if (line.vertical()) {
                for (int i = line.smallest(Point::y); i <= line.biggest(Point::y); i++) {
                    field[line.p1.x][i]++;
                }
            }
            if (line.diagonal()) {
                var length = line.biggest(Point::x) - line.smallest(Point::x);
                var directionX = (line.p2.x - line.p1.x) / Math.abs(line.p2.x - line.p1.x);
                var directionY = (line.p2.y - line.p1.y) / Math.abs(line.p2.y - line.p1.y);
                for (int i = 0; i <= length; i++) {
                    field[line.p1.x + i * directionX][line.p1.y + i * directionY]++;
                }
            }
        }
    }
}
