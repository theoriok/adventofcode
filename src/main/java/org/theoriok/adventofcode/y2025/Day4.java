package org.theoriok.adventofcode.y2025;

import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.theoriok.adventofcode.util.Utils.splitToList;

public class Day4 implements Day<Integer, Integer> {

    public static final String PAPER_ROLL_SYMBOL = "@";
    private final List<List<String>> grid;

    public Day4(List<String> input) {
        grid = input.stream()
            .map(line -> splitToList(line, "", Function.identity()))
            .toList();
    }

    @Override
    public Integer firstMethod() {
        int result = 0;
        for (int i = 0; i < grid.size(); i++) {
            List<String> row = grid.get(i);
            for (int j = 0; j < row.size(); j++) {
                String cell = row.get(j);
                if (PAPER_ROLL_SYMBOL.equals(cell)) {
                    int counter = checkCell(j, i, row, grid);
                    if (counter < 4) {
                        result++;
                    }
                }
            }
        }
        return result;
    }

    private int checkCell(int cellNum, int rowNum, List<String> row, List<List<String>> gridToCheck) {
        int counter = 0;
        if (cellNum > 0) {
            if (rowNum > 0) {
                if (PAPER_ROLL_SYMBOL.equals(gridToCheck.get(rowNum - 1).get(cellNum - 1))) {
                    counter++;
                }
            }
            if (PAPER_ROLL_SYMBOL.equals(gridToCheck.get(rowNum).get(cellNum - 1))) {
                counter++;
            }
            if (rowNum < gridToCheck.size() - 1) {
                if (PAPER_ROLL_SYMBOL.equals(gridToCheck.get(rowNum + 1).get(cellNum - 1))) {
                    counter++;
                }
            }
        }
        if (rowNum > 0) {
            if (PAPER_ROLL_SYMBOL.equals(gridToCheck.get(rowNum - 1).get(cellNum))) {
                counter++;
            }
        }

        if (rowNum < gridToCheck.size() - 1) {
            if (PAPER_ROLL_SYMBOL.equals(gridToCheck.get(rowNum + 1).get(cellNum))) {
                counter++;
            }
        }
        if (cellNum < row.size() - 1) {
            if (rowNum > 0) {
                if (PAPER_ROLL_SYMBOL.equals(gridToCheck.get(rowNum - 1).get(cellNum + 1))) {
                    counter++;
                }
            }
            if (PAPER_ROLL_SYMBOL.equals(gridToCheck.get(rowNum).get(cellNum + 1))) {
                counter++;
            }
            if (rowNum < gridToCheck.size() - 1) {
                if (PAPER_ROLL_SYMBOL.equals(gridToCheck.get(rowNum + 1).get(cellNum + 1))) {
                    counter++;
                }
            }
        }
        return counter;
    }

    @Override
    public Integer secondMethod() {
        int finalResult = 0;
        int result;
        var gridToCheck = grid;
        do {
            List<List<String>> gridToClean = gridToCheck.stream()
                .map(list -> (List<String>) new ArrayList<>(list))
                .toList();
            result = 0;
            for (int i = 0; i < gridToCheck.size(); i++) {
                List<String> row = gridToCheck.get(i);
                for (int j = 0; j < row.size(); j++) {
                    String cell = row.get(j);
                    if (PAPER_ROLL_SYMBOL.equals(cell)) {
                        int counter = checkCell(j, i, row, gridToCheck);
                        if (counter < 4) {
                            result++;
                            gridToClean.get(i).set(j, ".");
                        }
                    }
                }
            }
            finalResult += result;
            gridToCheck = gridToClean;
        } while (result > 0);
        return finalResult;
    }
}
