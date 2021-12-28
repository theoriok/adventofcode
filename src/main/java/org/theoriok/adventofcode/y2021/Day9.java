package org.theoriok.adventofcode.y2021;

import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.List;

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
                    points[i][j] = new Point(j,i, Short.parseShort(row.substring(j, j + 1)));
                }
            }
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
            return getAdjacentDepths(row, col).stream()
                .allMatch(point -> point.depth > points[col][row].depth);
        }

        private List<Point> getAdjacentDepths(int row, int col) {
            var adjacentDepths = new ArrayList<Point>();
            if (row != 0) {
                adjacentDepths.add(points[col][row - 1]);
            }
            if (row != width - 1) {
                adjacentDepths.add(points[col][row + 1]);
            }
            if (col != 0) {
                adjacentDepths.add(points[col - 1][row]);
            }
            if (col != height - 1) {
                adjacentDepths.add(points[col + 1][row]);
            }
            return adjacentDepths;
        }
    }

    private static record Point(
        int row,
        int col,
        short depth
    ){

    }
}
