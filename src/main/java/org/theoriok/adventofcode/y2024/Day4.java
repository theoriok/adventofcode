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
        private static final int[][] DIRECTIONS = {
            {-1, -1}, {-1, 0}, {-1, 1},  // NW, N, NE
            { 0, -1},          { 0, 1},  // W,     E
            { 1, -1}, { 1, 0}, { 1, 1}   // SW, S, SE
        };

        private static final String[][] X_PATTERNS = {
            {"M", "M", "S", "S"}, // MM on left, SS on right
            {"S", "M", "S", "M"}, // alternating
            {"S", "S", "M", "M"}, // SS on left, MM on right  
            {"M", "S", "M", "S"}  // alternating other way
        };
        
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

        private boolean isValidPosition(int i, int j) {
            return i >= 0 && i < letters.length && j >= 0 && j < letters[i].length;
        }

        private boolean hasXmasInDirection(int startI, int startJ, int deltaI, int deltaJ) {
            String pattern = "XMAS";
            for (int k = 0; k < 4; k++) {
                int newI = startI + k * deltaI;
                int newJ = startJ + k * deltaJ;
                if (!isValidPosition(newI, newJ) || !pattern.substring(k, k + 1).equals(letters[newI][newJ])) {
                    return false;
                }
            }
            return true;
        }

        private boolean hasValidXPattern(int centerI, int centerJ) {
            if (!isValidPosition(centerI - 1, centerJ - 1) || !isValidPosition(centerI + 1, centerJ + 1)) {
                return false;
            }
            
            String[] corners = {
                letters[centerI - 1][centerJ - 1], // top-left
                letters[centerI + 1][centerJ - 1], // bottom-left  
                letters[centerI - 1][centerJ + 1], // top-right
                letters[centerI + 1][centerJ + 1]  // bottom-right
            };
            
            for (String[] pattern : X_PATTERNS) {
                boolean matches = true;
                for (int k = 0; k < 4; k++) {
                    if (!corners[k].equals(pattern[k])) {
                        matches = false;
                        break;
                    }
                }
                if (matches) return true;
            }
            return false;
        }

        public int countXmas() {
            var counter = 0;
            for (var i = 0; i < letters.length; i++) {
                for (var j = 0; j < letters[i].length; j++) {
                    if ("X".equals(letters[i][j])) {
                        for (int[] direction : DIRECTIONS) {
                            if (hasXmasInDirection(i, j, direction[0], direction[1])) {
                                counter++;
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
                    if ("A".equals(letters[i][j]) && hasValidXPattern(i, j)) {
                        counter++;
                    }
                }
            }
            return counter;
        }
    }
}
