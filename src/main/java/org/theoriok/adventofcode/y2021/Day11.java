package org.theoriok.adventofcode.y2021;

import org.theoriok.adventofcode.Day;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

public class Day11 extends Day {

    public static final int GRID_SIZE = 10;

    public Day11(List<String> input) {
        super(input);
    }

    @Override
    public long firstMethod() {
        Grid grid = initializeGrid();
        grid.iterate(100);
        return grid.flashes;
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

        public void iterate(int time) {
            for (int i = 0; i < time; i++) {
                iterate();
            }
        }

        private void iterate() {
            Set<Octopus> flashers = new HashSet<>();
            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
                    increaseEnergyAndFlashIfPossible(flashers, i, j);
                }
            }
            flashers.forEach(Octopus::resetEnergy);
            flashes += flashers.size();
        }

        private void increaseEnergyAndFlashIfPossible(Set<Octopus> flashers, int x, int y) {
            var octopus = octopodes[x][y];
            octopus.increaseEnergy();
            if (octopus.canFlash() && flashers.add(octopus)) {
                if (x > 0) {
                    row(flashers, x - 1, y);
                }
                if (y > 0) {
                    increaseEnergyAndFlashIfPossible(flashers, x, y - 1);
                }
                if (y < GRID_SIZE - 1) {
                    increaseEnergyAndFlashIfPossible(flashers, x, y + 1);
                }
                if (x < GRID_SIZE - 1) {
                    row(flashers, x + 1, y);
                }
            }
        }

        private void row(Set<Octopus> flashers, int x, int y) {
            if (y > 0) {
                increaseEnergyAndFlashIfPossible(flashers, x, y - 1);
            }
            increaseEnergyAndFlashIfPossible(flashers, x, y);
            if (y < GRID_SIZE - 1) {
                increaseEnergyAndFlashIfPossible(flashers, x, y + 1);
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
