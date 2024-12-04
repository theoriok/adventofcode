package org.theoriok.adventofcode.y2024;

import org.theoriok.adventofcode.Day;

import java.util.List;

public class Day4 implements Day<Integer, Integer> {
    private final Grid grid;

    public Day4(List<String> input) {
        grid = Grid.from(input);
    }

    @Override
    public Integer firstMethod() {
        return grid.countXmas();
    }

    @Override
    public Integer secondMethod() {
        return grid.countMasX();
    }

    record Grid(String[][] letters) {
        static Grid from(List<String> input) {
            var letters = new String[input.getFirst().length()][input.size()];
            for (var i = 0; i < input.size(); i++) {
                var line = input.get(i);
                for (var j = 0; j < line.length(); j++) {
                    letters[j][i] = line.substring(j, j + 1);
                }
            }
            return new Grid(letters);
        }

        public int countXmas() {
            var counter = 0;
            for (var i = 0; i < letters.length; i++) {
                for (var j = 0; j < letters[i].length; j++) {
                    if ("X".equals(letters[i][j])) {
                        if (i >= 3) {
                            if (j >= 3) {
                                if ("M".equals(letters[i - 1][j - 1]) && "A".equals(letters[i - 2][j - 2]) && "S".equals(letters[i - 3][j - 3])) {
                                    counter++;
                                }
                            }
                            if ("M".equals(letters[i - 1][j]) && "A".equals(letters[i - 2][j]) && "S".equals(letters[i - 3][j])) {
                                counter++;
                            }
                            if (j + 3 < letters[i].length) {
                                if ("M".equals(letters[i - 1][j + 1]) && "A".equals(letters[i - 2][j + 2]) && "S".equals(letters[i - 3][j + 3])) {
                                    counter++;
                                }
                            }
                        }
                        if (j >= 3) {
                            if ("M".equals(letters[i][j - 1]) && "A".equals(letters[i][j - 2]) && "S".equals(letters[i][j - 3])) {
                                counter++;
                            }
                        }
                        if (j + 3 < letters[i].length) {
                            if ("M".equals(letters[i][j + 1]) && "A".equals(letters[i][j + 2]) && "S".equals(letters[i][j + 3])) {
                                counter++;
                            }
                        }
                        if (i + 3 < letters.length) {
                            if (j >= 3) {
                                if ("M".equals(letters[i + 1][j - 1]) && "A".equals(letters[i + 2][j - 2]) && "S".equals(letters[i + 3][j - 3])) {
                                    counter++;
                                }
                            }
                            if ("M".equals(letters[i + 1][j]) && "A".equals(letters[i + 2][j]) && "S".equals(letters[i + 3][j])) {
                                counter++;
                            }
                            if (j + 3 < letters[i].length) {
                                if ("M".equals(letters[i + 1][j + 1]) && "A".equals(letters[i + 2][j + 2]) && "S".equals(letters[i + 3][j + 3])) {
                                    counter++;
                                }
                            }
                        }
                    }
                }
            }
            return counter;
        }

        public int countMasX() {
            var counter = 0;
            for (var i = 0; i < letters.length; i++) {
                for (var j = 0; j < letters[i].length; j++) {
                    if ("A".equals(letters[i][j])) {
                        if (i >= 1 && i + 1 < letters.length) {
                            if (j >= 1 && j + 1 < letters[i].length) {
                                if ("M".equals(letters[i - 1][j - 1]) && "M".equals(letters[i + 1][j - 1])
                                    && "S".equals(letters[i - 1][j + 1]) && "S".equals(letters[i + 1][j + 1])) {
                                    counter++;
                                }
                                if ("S".equals(letters[i - 1][j - 1]) && "M".equals(letters[i + 1][j - 1])
                                    && "S".equals(letters[i - 1][j + 1]) && "M".equals(letters[i + 1][j + 1])) {
                                    counter++;
                                }
                                if ("S".equals(letters[i - 1][j - 1]) && "S".equals(letters[i + 1][j - 1])
                                    && "M".equals(letters[i - 1][j + 1]) && "M".equals(letters[i + 1][j + 1])) {
                                    counter++;
                                }
                                if ("M".equals(letters[i - 1][j - 1]) && "S".equals(letters[i + 1][j - 1])
                                    && "M".equals(letters[i - 1][j + 1]) && "S".equals(letters[i + 1][j + 1])) {
                                    counter++;
                                }
                            }
                        }
                    }
                }
            }
            return counter;
        }
    }
}
