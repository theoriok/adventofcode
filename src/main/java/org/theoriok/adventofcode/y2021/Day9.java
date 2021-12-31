package org.theoriok.adventofcode.y2021;

import static java.util.function.Predicate.not;

import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day9 extends Day<Long, Long> {

    private final Field field;

    public Day9(List<String> input) {
        super(input);
        field = new Field(input);
    }

    @Override
    public Long firstMethod() {
        return field.findLowPoints().stream()
            .mapToLong(point -> point.depth + 1)
            .sum();
    }

    @Override
    public Long secondMethod() {

        var lowPoints = field.findLowPoints();
        List<Set<Point>> valleys = lowPoints.stream()
            .map(point -> field.findNeighbours(point, new HashSet<Point>()))
            .collect(Collectors.toList());

        return valleys.stream()
            .mapToLong(Set::size)
            .sorted()
            .skip(valleys.size() - 3)
            .reduce((valley1, valley2) -> valley1 * valley2)
            .orElse(-1);
    }

    private static class Field {
        private final int width;
        private final int height;
        private final Point[][] points;

        private Field(List<String> input) {
            width = input.get(0).length();
            height = input.size();
            points = new Point[height][width];
            for (int i = 0; i < height; i++) {
                String row = input.get(i);
                for (int j = 0; j < width; j++) {
                    points[i][j] = new Point(j, i, Short.parseShort(row.substring(j, j + 1)));
                }
            }
        }

        public Set<Point> findNeighbours(Point point, Set<Point> neighbours) {
            var adjacentPoints = getAdjacentPoints(point.row, point.col);
            adjacentPoints.stream()
                .filter(adjacentPoint -> adjacentPoint.depth < 9)
                .filter(not(neighbours::contains))
                .forEach(adjacentPoint -> {
                    neighbours.add(adjacentPoint);
                    neighbours.addAll(findNeighbours(adjacentPoint, neighbours));
                });
            return neighbours;
        }

        public List<Point> findLowPoints() {
            var lowPoints = new ArrayList<Point>();
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (isLowPoint(j, i)) {
                        lowPoints.add(points[i][j]);
                    }
                }
            }
            return lowPoints;
        }

        private boolean isLowPoint(int row, int col) {
            return getAdjacentPoints(row, col).stream()
                .allMatch(point -> point.depth > points[col][row].depth);
        }

        private List<Point> getAdjacentPoints(int row, int col) {
            var adjacentPoints = new ArrayList<Point>();
            if (row != 0) {
                adjacentPoints.add(points[col][row - 1]);
            }
            if (row != width - 1) {
                adjacentPoints.add(points[col][row + 1]);
            }
            if (col != 0) {
                adjacentPoints.add(points[col - 1][row]);
            }
            if (col != height - 1) {
                adjacentPoints.add(points[col + 1][row]);
            }
            return adjacentPoints;
        }
    }

    private static record Point(
        int row,
        int col,
        short depth
    ) {

    }
}
