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
            .mapToLong(depth -> depth + 1)
            .sum();
    }

    private static class Field {
        private final int width;
        private final int height;
        private final int[][] depths;

        private Field(List<String> input) {
            width = input.get(0).length();
            height = input.size();
            depths = new int[height][width];
            for (int i = 0; i < height; i++) {
                String row = input.get(i);
                for (int j = 0; j < width; j++) {
                    depths[i][j] = Integer.parseInt(row.substring(j, j + 1));
                }
            }
        }

        public List<Integer> findLowPoints() {
            var lowPoints = new ArrayList<Integer>();
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (isLowPoint(j, i)) {
                        lowPoints.add(depths[i][j]);
                    }
                }
            }
            return lowPoints;
        }

        private boolean isLowPoint(int row, int col) {
            return getAdjacentDepths(row, col).stream()
                .allMatch(depth -> depth > depths[col][row]);
        }

        private List<Integer> getAdjacentDepths(int row, int col) {
            var adjacentDepths = new ArrayList<Integer>();
            if (row != 0) {
                adjacentDepths.add(depths[col][row - 1]);
            }
            if (row != width - 1) {
                adjacentDepths.add(depths[col][row + 1]);
            }
            if (col != 0) {
                adjacentDepths.add(depths[col - 1][row]);
            }
            if (col != height - 1) {
                adjacentDepths.add(depths[col + 1][row]);
            }
            return adjacentDepths;
        }
    }
}
