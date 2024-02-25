package org.theoriok.adventofcode.y2021;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Day25 implements Day<Integer, Long> {

    private final List<String> input;

    public Day25(List<String> input) {
        this.input = input;
    }

    @Override
    public Integer firstMethod() {
        var grid = initializeGrid(input);
        var step = 0;
        SeaCucumber[][] before;
        SeaCucumber[][] after;
        do {
            before = grid.asArray();
            grid.step();
            after = grid.asArray();
            step++;
        } while (!Arrays.deepEquals(before, after));
        return step;
    }

    private Grid initializeGrid(List<String> input) {
        List<SeaCucumber> seaCucumbers = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            var line = input.get(i).split("");
            for (int j = 0; j < line.length; j++) {
                int row = i;
                int col = j;
                Orientation.fromString(line[j]).map(orientation -> new SeaCucumber(row, col, orientation)).ifPresent(seaCucumbers::add);
            }
        }
        return new Grid(seaCucumbers, input.getFirst().length(), input.size());
    }

    private static class Grid {
        private final List<SeaCucumber> seaCucumbers;
        private final int width;
        private final int height;

        public Grid(List<SeaCucumber> seaCucumbers, int width, int height) {
            this.seaCucumbers = seaCucumbers;
            this.width = width;
            this.height = height;
        }

        public SeaCucumber[][] asArray() {
            var seaCucumbersAsArray = new SeaCucumber[height][width];
            for (SeaCucumber seaCucumber : seaCucumbers) {
                seaCucumbersAsArray[seaCucumber.getRow()][seaCucumber.getCol()] = seaCucumber;
            }
            return seaCucumbersAsArray;
        }

        public void step() {
            var fieldBeforeEast = asArray();
            seaCucumbers.stream()
                .filter(seaCucumber -> seaCucumber.getOrientation() == Orientation.EAST)
                .forEach(seaCucumber -> moveEastIfPossible(seaCucumber, fieldBeforeEast));
            var fieldBeforeSouth = asArray();
            seaCucumbers.stream()
                .filter(seaCucumber -> seaCucumber.getOrientation() == Orientation.SOUTH)
                .forEach(seaCucumber -> moveSouthIfPossible(seaCucumber, fieldBeforeSouth));
        }

        private void moveSouthIfPossible(SeaCucumber seaCucumber, SeaCucumber[][] field) {
            var newRow = seaCucumber.getRow() + 1;
            if (newRow == height) {
                newRow = 0;
            }
            if (field[newRow][seaCucumber.getCol()] == null) {
                seaCucumber.setRow(newRow);
            }
        }

        private void moveEastIfPossible(SeaCucumber seaCucumber, SeaCucumber[][] field) {
            var newCol = seaCucumber.getCol() + 1;
            if (newCol == width) {
                newCol = 0;
            }
            if (field[seaCucumber.getRow()][newCol] == null) {
                seaCucumber.setCol(newCol);
            }
        }
    }

    private static class SeaCucumber {

        private int col;
        private int row;
        private final Orientation orientation;

        public SeaCucumber(int row, int col, Orientation orientation) {
            this.col = col;
            this.row = row;
            this.orientation = orientation;
        }

        public int getCol() {
            return col;
        }

        public void setCol(int col) {
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public Orientation getOrientation() {
            return orientation;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj instanceof SeaCucumber that) {
                return new EqualsBuilder()
                    .append(col, that.col)
                    .append(row, that.row)
                    .append(orientation, that.orientation)
                    .isEquals();
            }
            return false;
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                .append(col)
                .append(row)
                .append(orientation)
                .toHashCode();
        }

        @Override
        public String toString() {
            return orientation.stringRepresentation;
        }
    }

    private enum Orientation {
        EAST(">"),
        SOUTH("v");

        private final String stringRepresentation;

        Orientation(String stringRepresentation) {
            this.stringRepresentation = stringRepresentation;
        }

        public static Optional<Orientation> fromString(String stringRepresentation) {
            return Arrays.stream(values())
                .filter(orientation -> orientation.stringRepresentation.equals(stringRepresentation))
                .findFirst();
        }
    }
}
