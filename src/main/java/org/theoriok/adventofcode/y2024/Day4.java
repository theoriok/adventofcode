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
            for (var x = 0; x < input.size(); x++) {
                var line = input.get(x);
                for (var y = 0; y < line.length(); y++) {
                    letters[y][x] = line.substring(y, y + 1);
                }
            }
            return new Grid(letters);
        }

        private boolean isInvalidPosition(int xPos, int yPos) {
            return xPos < 0 || xPos >= letters.length || yPos < 0 || yPos >= letters[xPos].length;
        }

        private boolean hasXmasInDirection(int startX, int startY, int deltaX, int deltaY) {
            String pattern = "XMAS";
            for (int k = 0; k < 4; k++) {
                int newI = startX + k * deltaX;
                int newJ = startY + k * deltaY;
                if (isInvalidPosition(newI, newJ) || !pattern.substring(k, k + 1).equals(letters[newI][newJ])) {
                    return false;
                }
            }
            return true;
        }

        private boolean hasValidXPattern(int centerX, int centerY) {
            if (isInvalidPosition(centerX - 1, centerY - 1) || isInvalidPosition(centerX + 1, centerY + 1)) {
                return false;
            }
            
            String[] corners = {
                letters[centerX - 1][centerY - 1], // top-left
                letters[centerX + 1][centerY - 1], // bottom-left
                letters[centerX - 1][centerY + 1], // top-right
                letters[centerX + 1][centerY + 1]  // bottom-right
            };
            
            for (String[] pattern : X_PATTERNS) {
                boolean matches = true;
                for (int k = 0; k < 4; k++) {
                    if (!corners[k].equals(pattern[k])) {
                        matches = false;
                        break;
                    }
                }
                if (matches) {
                    return true;
                }
            }
            return false;
        }

        public int countXmas() {
            var counter = 0;
            for (var x = 0; x < letters.length; x++) {
                for (var y = 0; y < letters[x].length; y++) {
                    if ("X".equals(letters[x][y])) {
                        for (int[] direction : DIRECTIONS) {
                            if (hasXmasInDirection(x, y, direction[0], direction[1])) {
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
            for (var x = 0; x < letters.length; x++) {
                for (var y = 0; y < letters[x].length; y++) {
                    if ("A".equals(letters[x][y]) && hasValidXPattern(x, y)) {
                        counter++;
                    }
                }
            }
            return counter;
        }
    }
}
