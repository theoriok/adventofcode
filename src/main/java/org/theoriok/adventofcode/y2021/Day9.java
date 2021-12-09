package org.theoriok.adventofcode.y2021;

import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.List;

public class Day9 extends Day {

    private final Field field;

    public Day9(List<String> input) {
        super(input);
        field = new Field(input);
    }

    @Override
    public long firstMethod() {
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

        private boolean isLowPoint(int x, int y) {
            return getAdjacentDepths(x, y).stream()
                .allMatch(depth -> depth > depths[y][x]);
        }

        private ArrayList<Integer> getAdjacentDepths(int x, int y) {
            var adjacantDepths = new ArrayList<Integer>();
            if (x != 0) {
                adjacantDepths.add(depths[y][x - 1]);
            }
            if (x != width - 1) {
                adjacantDepths.add(depths[y][x + 1]);
            }
            if (y != 0) {
                adjacantDepths.add(depths[y - 1][x]);
            }
            if (y != height - 1) {
                adjacantDepths.add(depths[y + 1][x]);
            }
            return adjacantDepths;
        }
    }
}
