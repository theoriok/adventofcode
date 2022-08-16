package org.theoriok.adventofcode.y2020;

import org.theoriok.adventofcode.Day;

import java.util.List;

public class Day3 extends Day<Integer, Long> {

    private int height;
    private int width;
    private char[][] grid;

    public Day3(List<String> input) {
        initializeGrid(input);
    }

    private void initializeGrid(List<String> input) {
        width = input.get(0).length();
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
        var x = 0;
        var y = 0;
        var count = 0;
        while (y < height) {
            if (grid[x % width][y] == '#') {
                count++;
            }
            x += 3;
            y += 1;
        }
        return count;
    }
}
