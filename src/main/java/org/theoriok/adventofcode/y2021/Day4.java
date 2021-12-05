package org.theoriok.adventofcode.y2021;

import static java.util.stream.Collectors.toList;

import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Day4 extends Day {

    public static final int BOARD_SIZE = 5;

    public Day4(List<String> input) {
        super(input);
    }

    private static class Board {
        private final int[][] grid;
        private final Boolean[][] status;
        private int solvedIndex;

        private Board(int[][] grid) {
            this.grid = grid;
            status = new Boolean[grid.length][grid.length];
            solvedIndex = -1;
        }

        public void markValue(int value) {
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (grid[i][j] == value) {
                        status[i][j] = true;
                        return;
                    }
                }
            }
        }

        public boolean solved() {
            return Arrays.stream(status).anyMatch(this::allCellsTrue) || Arrays.stream(transpose(status)).anyMatch(this::allCellsTrue);
        }

        private Boolean[][] transpose(Boolean[][] original) {
            var transpose = new Boolean[BOARD_SIZE][BOARD_SIZE];
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    transpose[j][i] = original[i][j];
                }
            }
            return transpose;
        }

        private boolean allCellsTrue(Boolean[] row) {
            return Arrays.stream(row).allMatch(Boolean.TRUE::equals);
        }

        public int unmarkedValuesSummed() {
            var sum = 0;
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (!Boolean.TRUE.equals(status[i][j])) {
                        sum += grid[i][j];
                    }
                }
            }
            return sum;
        }

        public int getSolvedIndex() {
            return solvedIndex;
        }

        public boolean solved(int solvedIndex) {
            if (this.solvedIndex>-1) {
                return true;
            }
            if (solved()) {
                this.solvedIndex = solvedIndex;
            }
            return solved();
        }
    }

    @Override
    public int firstMethod() {
        var numbers = Arrays.stream(input.get(0).split(","))
            .map(Integer::parseInt)
            .toList();
        var boards = initializeBoards();
        var index = -1;
        var number = -1;
        List<Board> solvedBoards = new ArrayList<>();
        while (solvedBoards.isEmpty()) {
            index++;
            number = numbers.get(index);
            for (Board board : boards) {
                board.markValue(number);
            }
            solvedBoards = boards.stream()
                .filter(Board::solved)
                .collect(toList());
        }
        return number * solvedBoards.get(0).unmarkedValuesSummed();
    }

    private List<Board> initializeBoards() {
        List<Board> boards = new ArrayList<>();
        var grid = new int[BOARD_SIZE][BOARD_SIZE];
        for (int i = 2; i < input.size(); i++) {
            var line = input.get(i);
            if (line.isBlank()) {
                boards.add(new Board(grid));
                grid = new int[BOARD_SIZE][BOARD_SIZE];
            } else {
                grid[(i - 2) % 6] = Arrays.stream(line.replace("  ", " ").trim().split(" ")).mapToInt(Integer::parseInt).toArray();
            }
        }
        boards.add(new Board(grid));
        return boards;
    }

    @Override
    public int secondMethod() {
        var numbers = Arrays.stream(input.get(0).split(","))
            .map(Integer::parseInt)
            .toList();
        var boards = initializeBoards();
        var index = -1;
        var number = -1;
        List<Board> solvedBoards = new ArrayList<>();
        while (solvedBoards.size() < boards.size()) {
            index++;
            number = numbers.get(index);
            for (Board board : boards) {
                board.markValue(number);
            }
            int solvedIndex = index;
            solvedBoards = boards.stream()
                .filter(board -> board.solved(solvedIndex))
                .sorted(Comparator.comparing(Board::getSolvedIndex).reversed())
                .collect(toList());
        }
        return number * solvedBoards.get(0).unmarkedValuesSummed();
    }
}
