package org.theoriok.adventofcode.y2020;

import org.theoriok.adventofcode.Day;

import java.util.List;

public class Day3 implements Day<Integer, Integer> {

    private int height;
    private int width;
    private char[][] grid;

    public Day3(List<String> input) {
        initializeGrid(input);
    }

    private void initializeGrid(List<String> input) {
        width = input.getFirst().length();
        height = input.size();
        grid = new char[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = input.get(y).charAt(x);
            }
        }
    }

    @Override
    public Integer firstMethod() {
        return countSlope(3, 1);
    }

    @Override
    public Integer secondMethod() {
        return countSlope(1, 1) * countSlope(3, 1) * countSlope(5, 1) * countSlope(7, 1) * countSlope(1, 2);
    }

    private int countSlope(int xSlope, int ySLope) {
        var xCoordinate = 0;
        var yCoordinate = 0;
        var count = 0;
        while (yCoordinate < height) {
            if (grid[xCoordinate % width][yCoordinate] == '#') {
                count++;
            }
            xCoordinate += xSlope;
            yCoordinate += ySLope;
        }
        return count;
    }
}
