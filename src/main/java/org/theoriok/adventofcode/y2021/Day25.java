package org.theoriok.adventofcode.y2021;

import static java.util.Comparator.comparing;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Day25 extends Day<Integer, Long> {

    public Day25(List<String> input) {
        super(input);

    }

    @Override
    public Integer firstMethod() {
        var grid = initializeGrid(input);
        var step = 1;
        SeaCucumber[][] before;
        SeaCucumber[][] after;
        do {
            before = grid.asArray();
            grid.step();
            after = grid.asArray();
            step++;
            System.out.println(step);
        } while (!Arrays.deepEquals(before, after));
        return step;
    }

    private Grid initializeGrid(List<String> input) {
        List<SeaCucumber> seaCucumbers = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            var line = input.get(i).split("");
            for (int j = 0; j < line.length; j++) {
                int xCoord = j;
                int yCoord = i;
                Orientation.fromString(line[j]).map(orientation -> new SeaCucumber(xCoord, yCoord, orientation)).ifPresent(seaCucumbers::add);
            }
        }
        return new Grid(seaCucumbers, input.get(0).length(), input.size());
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
                seaCucumbersAsArray[seaCucumber.getY()][seaCucumber.getX()] = seaCucumber;
            }
            return seaCucumbersAsArray;
        }

        public void step() {
            seaCucumbers.stream()
                .filter(seaCucumber -> seaCucumber.getOrientation() == Orientation.EAST)
                .sorted(comparing(SeaCucumber::getY).thenComparing(SeaCucumber::getX))
                .forEach(seaCucumber -> {
                    var field = asArray();
                    var newX = seaCucumber.getX() + 1;
                    if (newX == width) {
                        newX = 0;
                    }
                    if (field[seaCucumber.getY()][newX] == null) {
                        seaCucumber.setX(newX);
                    }
                });
            seaCucumbers.stream()
                .filter(seaCucumber -> seaCucumber.getOrientation() == Orientation.SOUTH)
                .sorted(comparing(SeaCucumber::getY).thenComparing(SeaCucumber::getX))
                .forEach(seaCucumber -> {
                    var field = asArray();
                    var newY = seaCucumber.getY() + 1;
                    if (newY == height) {
                        newY = 0;
                    }
                    if (field[newY][seaCucumber.getX()] == null) {
                        seaCucumber.setY(newY);
                    }
                });
        }
    }

    private static class SeaCucumber {

        private int x;
        private int y;
        private final Orientation orientation;

        public SeaCucumber(int x, int y, Orientation orientation) {
            this.x = x;
            this.y = y;
            this.orientation = orientation;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
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
                    .append(x, that.x)
                    .append(y, that.y)
                    .append(orientation, that.orientation)
                    .isEquals();
            }
            return false;
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                .append(x)
                .append(y)
                .append(orientation)
                .toHashCode();
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
