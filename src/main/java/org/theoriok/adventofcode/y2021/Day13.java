package org.theoriok.adventofcode.y2021;

import static java.lang.Integer.parseInt;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.theoriok.adventofcode.Day;

import java.util.List;

public class Day13 extends Day<Integer, String> {

    private static final String FOLD = "fold along ";
    public static final String SYMBOL = "#";

    private final List<String> input;

    public Day13(List<String> input) {
        this.input = input;
    }

    private String[][] initializeGrid(List<String> input) {
        var coords = input.stream()
            .filter(StringUtils::isNotBlank)
            .filter(line -> !line.startsWith(FOLD))
            .map(line -> line.split(","))
            .map(split -> Pair.of(parseInt(split[0]), parseInt(split[1])))
            .toList();
        var maxX = coords.stream()
            .mapToInt(Pair::getLeft)
            .max().orElse(0);
        var maxY = coords.stream()
            .mapToInt(Pair::getRight)
            .max().orElse(0);
        var grid = new String[maxX + 1][maxY + 1];
        coords.forEach(coord -> grid[coord.getLeft()][coord.getRight()] = SYMBOL);
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
            if (fold == Fold.X) {
                var newWidth = axis;
                var newHeight = grid[0].length;
                var newGrid = new String[newWidth][newHeight];
                for (int i = 0; i < newWidth; i++) {
                    for (int j = 0; j < newHeight; j++) {
                        if (SYMBOL.equals(grid[i][j]) || SYMBOL.equals(grid[grid.length - i - 1][j])) {
                            newGrid[i][j] = SYMBOL;
                        }
                    }
                }
                return newGrid;
            }
            if (fold == Fold.Y) {
                var newWidth = grid.length;
                var newHeight = axis;
                var newGrid = new String[newWidth][newHeight];
                for (int i = 0; i < newWidth; i++) {
                    for (int j = 0; j < newHeight; j++) {
                        if (SYMBOL.equals(grid[i][j]) || SYMBOL.equals(grid[i][grid[0].length - j - 1])) {
                            newGrid[i][j] = SYMBOL;
                        }
                    }
                }
                return newGrid;
            }
            return grid;
        }
    }

    private enum Fold {
        X,
        Y
    }
}
