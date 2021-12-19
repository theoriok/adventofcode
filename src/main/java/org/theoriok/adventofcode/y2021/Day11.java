package org.theoriok.adventofcode.y2021;

import org.theoriok.adventofcode.Day;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

public class Day11 extends Day<Integer, Long> {

    public static final int GRID_SIZE = 10;

    public Day11(List<String> input) {
        super(input);
    }

    @Override
    public Integer firstMethod() {
        Grid grid = initializeGrid();
        grid.iterateTimes(100);
        return grid.flashes;
    }

    @Override
    public Long secondMethod() {
        Grid grid = initializeGrid();
        return grid.iterateUntilSynced();
    }

    private Grid initializeGrid() {
        var octopodes = new Octopus[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < input.size(); i++) {
            String[] row = input.get(i).split("");
            for (int j = 0; j < row.length; j++) {
                octopodes[i][j] = new Octopus(Integer.parseInt(row[j]));
            }
        }
        return new Grid(octopodes);
    }

    private static class Grid {
        private final Octopus[][] octopodes;
        private int flashes;

        private Grid(Octopus[][] octopodes) {
            this.octopodes = octopodes;
        }

        public void iterateTimes(int time) {
            for (int i = 0; i < time; i++) {
                flashes += iterate();
            }
        }

        private int iterate() {
            Set<Octopus> flashers = new HashSet<>();
            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
                    increaseEnergyAndFlashIfPossible(flashers, i, j);
                }
            }
            flashers.forEach(Octopus::resetEnergy);
            return flashers.size();
        }

        public long iterateUntilSynced() {
            var step = 0;
            var newFlashes = 0;
            while (newFlashes != 100) {
                newFlashes = iterate();
                step++;
            }
            return step;
        }

        private void increaseEnergyAndFlashIfPossible(Set<Octopus> flashers, int row, int col) {
            var octopus = octopodes[row][col];
            octopus.increaseEnergy();
            if (octopus.canFlash() && flashers.add(octopus)) {
                if (row > 0) {
                    row(flashers, row - 1, col);
                }
                if (col > 0) {
                    increaseEnergyAndFlashIfPossible(flashers, row, col - 1);
                }
                if (col < GRID_SIZE - 1) {
                    increaseEnergyAndFlashIfPossible(flashers, row, col + 1);
                }
                if (row < GRID_SIZE - 1) {
                    row(flashers, row + 1, col);
                }
            }
        }

        private void row(Set<Octopus> flashers, int row, int col) {
            if (col > 0) {
                increaseEnergyAndFlashIfPossible(flashers, row, col - 1);
            }
            increaseEnergyAndFlashIfPossible(flashers, row, col);
            if (col < GRID_SIZE - 1) {
                increaseEnergyAndFlashIfPossible(flashers, row, col + 1);
            }
        }
    }

    private static class Octopus {
        private int energyLevel;

        private Octopus(int energyLevel) {
            this.energyLevel = energyLevel;
        }

        public void increaseEnergy() {
            energyLevel++;
        }

        public void resetEnergy() {
            energyLevel = 0;
        }

        public boolean canFlash() {
            return energyLevel > 9;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Octopus.class.getSimpleName() + "[", "]")
                .add("energyLevel=" + energyLevel)
                .toString();
        }
    }
}
