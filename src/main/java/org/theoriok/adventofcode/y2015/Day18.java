package org.theoriok.adventofcode.y2015;

import org.theoriok.adventofcode.Day;

import java.util.Arrays;
import java.util.List;

public class Day18 implements Day<Long, Long> {


    private final List<String> input;

    public Day18(List<String> input) {
        this.input = input;
    }

    @Override
    public Long firstMethod() {
        return firstMethod(100);
    }

    long firstMethod(int steps) {
        Grid grid = Grid.from(this.input);
        for (int i = 0; i < steps; i++) {
            grid = grid.step();
        }
        return grid.numberOfLightsOn();
    }

    @Override
    public Long secondMethod() {
        return secondMethod(100);
    }

    long secondMethod(int steps) {
        Grid grid = Grid.from(this.input);
        for (int i = 0; i < steps; i++) {
            grid = grid.step2();
        }
        return grid.numberOfLightsOn();
    }

    record Light(boolean on) {
    }

    record Grid(Light[][] lights) {
        static Grid from(List<String> input) {
            var lights = new Light[input.size()][input.getFirst().length()];
            for (int i = 0; i < input.size(); i++) {
                String line = input.get(i);
                for (int j = 0; j < line.length(); j++) {
                    lights[i][j] = new Light(line.charAt(j) == '#');
                }
            }
            return new Grid(lights);
        }

        public long numberOfLightsOn() {
            return Arrays.stream(lights)
                    .flatMap(Arrays::stream)
                    .filter(Light::on)
                    .count();
        }

        public Grid step() {
            var newLights = new Light[lights.length][lights[0].length];
            for (int i = 0; i < lights.length; i++) {
                for (int j = 0; j < lights[i].length; j++) {
                    var numberOfNeighboursOn = countNeighboursOn(i, j);
                    Light light = lights[i][j];
                    newLights[i][j] = new Light(isNewLightOn(light.on, numberOfNeighboursOn));
                }
            }
            return new Grid(newLights);
        }

        private int countNeighboursOn(int row, int column) {
            int neighboursOn = 0;
            if (row > 0) {
                neighboursOn = +getNeighboursOnRowBiggerThanTopRow(row, column);
            }
            if (column > 0 && lights[row][column - 1].on) {
                neighboursOn++;
            }
            if (column < lights.length - 1 && lights[row][column + 1].on) {
                neighboursOn++;
            }
            if (row < lights.length - 1) {
                neighboursOn += getNeighboursOnRowSmallerThanMaxRow(row, column);
            }
            return neighboursOn;
        }

        private int getNeighboursOnRowBiggerThanTopRow(int row, int column) {
            int neighboursOn = 0;
            if (column > 0 && lights[row - 1][column - 1].on) {
                neighboursOn++;
            }
            if (lights[row - 1][column].on) {
                neighboursOn++;
            }
            if (column < lights.length - 1 && lights[row - 1][column + 1].on) {
                neighboursOn++;
            }
            return neighboursOn;
        }

        private int getNeighboursOnRowSmallerThanMaxRow(int row, int column) {
            int neighboursOn = 0;
            if (column > 0 && lights[row + 1][column - 1].on) {
                neighboursOn++;
            }
            if (lights[row + 1][column].on) {
                neighboursOn++;
            }
            if (column < lights.length - 1 && lights[row + 1][column + 1].on) {
                neighboursOn++;
            }
            return neighboursOn;
        }

        private boolean isNewLightOn(boolean originalLightOn, int numberOfNeighboursOn) {
            return switch (originalLightOn) {
                case true -> 2 <= numberOfNeighboursOn && numberOfNeighboursOn <= 3;
                case false -> numberOfNeighboursOn == 3;
            };
        }

        public Grid step2() {
            var newLights = new Light[lights.length][lights[0].length];
            for (int i = 0; i < lights.length; i++) {
                for (int j = 0; j < lights[i].length; j++) {
                    if (isCorner(i, j)) {
                        newLights[i][j] = new Light(true);
                    } else {
                        var numberOfNeighboursOn = countNeighboursOn(i, j);
                        Light light = lights[i][j];
                        newLights[i][j] = new Light(isNewLightOn(light.on, numberOfNeighboursOn));
                    }
                }
            }
            return new Grid(newLights);
        }

        private boolean isCorner(int row, int column) {
            return (row == 0 || row == lights.length - 1) && (column == 0 || column == lights.length - 1);
        }
    }
}
