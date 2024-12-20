package org.theoriok.adventofcode.y2021;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.theoriok.adventofcode.Day;

import java.util.List;

import static java.lang.Integer.parseInt;

public class Day13 implements Day<Integer, String> {

    private static final String FOLD = "fold along ";
    public static final String SYMBOL = "#";

    private final List<String> input;

    public Day13(List<String> input) {
        this.input = input;
    }

    private String[][] initializeGrid(List<String> input) {
        var coordinates = input.stream()
            .filter(StringUtils::isNotBlank)
            .filter(line -> !line.startsWith(FOLD))
            .map(line -> line.split(","))
            .map(split -> Pair.of(parseInt(split[0]), parseInt(split[1])))
            .toList();
        var maxX = coordinates.stream()
            .mapToInt(Pair::getLeft)
            .max().orElse(0);
        var maxY = coordinates.stream()
            .mapToInt(Pair::getRight)
            .max().orElse(0);
        var grid = new String[maxX + 1][maxY + 1];
        coordinates.forEach(coordinate -> grid[coordinate.getLeft()][coordinate.getRight()] = SYMBOL);
        return grid;
    }

    @Override
    public Integer firstMethod() {
        var grid = initializeGrid(input);
        grid = new Instruction(input.get(input.indexOf("") + 1)).fold(grid);
        return countSymbols(grid);
    }

    @Override
    public String secondMethod() {
        var grid = initializeGrid(input);
        for (int i = input.indexOf("") + 1; i < input.size(); i++) {
            grid = new Instruction(input.get(i)).fold(grid);
        }

        return outputGrid(grid);
    }

    private String outputGrid(String[][] grid) {
        var transposedGrid = new String[grid[0].length][grid.length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                transposedGrid[j][i] = grid[i][j];
            }
        }
        var stringBuilder = new StringBuilder();
        stringBuilder.append(System.lineSeparator());
        for (String[] row : transposedGrid) {
            for (String cell : row) {
                stringBuilder.append(SYMBOL.equals(cell) ? SYMBOL : " ");
            }
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    private int countSymbols(String[][] grid) {
        var count = 0;
        for (String[] row : grid) {
            for (String cell : row) {
                if (SYMBOL.equals(cell)) {
                    count++;
                }
            }
        }
        return count;
    }

    private static class Instruction {
        private final Fold fold;
        private final int axis;

        public Instruction(String line) {
            var split = line.replace(FOLD, "").split("=");
            fold = Fold.valueOf(split[0].toUpperCase());
            axis = Integer.parseInt(split[1]);
        }

        public String[][] fold(String[][] grid) {
            return fold.foldGrid(grid, axis);
        }
    }

    private enum Fold {
        X {
            @Override
            String[][] foldGrid(String[][] grid, int axis) {
                var newHeight = grid[0].length;
                var newGrid = new String[axis][newHeight];
                for (int i = 0; i < axis; i++) {
                    for (int j = 0; j < newHeight; j++) {
                        if (SYMBOL.equals(grid[i][j]) || SYMBOL.equals(grid[grid.length - i - 1][j])) {
                            newGrid[i][j] = SYMBOL;
                        }
                    }
                }
                return newGrid;
            }
        },
        Y {
            @Override
            String[][] foldGrid(String[][] grid, int axis) {
                var newWidth = grid.length;
                var newGrid = new String[newWidth][axis];
                for (int i = 0; i < newWidth; i++) {
                    for (int j = 0; j < axis; j++) {
                        if (SYMBOL.equals(grid[i][j]) || SYMBOL.equals(grid[i][grid[0].length - j - 1])) {
                            newGrid[i][j] = SYMBOL;
                        }
                    }
                }
                return newGrid;
            }
        };

        abstract String[][] foldGrid(String[][] grid, int axis);
    }
}
