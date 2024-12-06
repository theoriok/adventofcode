package org.theoriok.adventofcode.y2024;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theoriok.adventofcode.Day;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Collections.emptySet;
import static org.theoriok.adventofcode.y2024.Day6.Direction.EAST;
import static org.theoriok.adventofcode.y2024.Day6.Direction.NORTH;
import static org.theoriok.adventofcode.y2024.Day6.Direction.SOUTH;
import static org.theoriok.adventofcode.y2024.Day6.Direction.WEST;

public class Day6 implements Day<Integer, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day6.class);
    private final Grid grid;
    private Position startPosition;

    public Day6(List<String> input) {
        var obstructions = new boolean[input.getFirst().length()][input.size()];
        for (int i = 0; i < input.size(); i++) {
            var line = input.get(i);
            var split = line.split("");
            for (var j = 0; j < split.length; j++) {
                if ("#".equals(split[j])) {
                    obstructions[j][i] = true;
                }
                if ("^".equals(split[j])) {
                    startPosition = new Position(j, i);
                }
            }
        }
        grid = new Grid(obstructions);
    }

    @Override
    public Integer firstMethod() {
        return grid.distinctPositions(startPosition).size();
    }

    @Override
    public Long secondMethod() {
        Set<Position> positions = grid.distinctPositions(startPosition);
        return positions.stream()
            .parallel()
            .map(grid::copyWithObstruction)
            .map(grid -> grid.distinctPositions(startPosition))
            .filter(Set::isEmpty)
            .count();
    }

    record Grid(boolean[][] obstructions) {
        public Set<Position> distinctPositions(Position startPosition) {
            var direction = NORTH;
            var position = startPosition;
            Set<Position> positions = new HashSet<>();
            int steps = 0;
            while (positionWithinGrid(position) && steps < 15_000) {
                positions.add(position);
                Position newPosition = switch (direction) {
                    case NORTH -> new Position(position.lat, position.lon - 1);
                    case EAST -> new Position(position.lat + 1, position.lon);
                    case SOUTH -> new Position(position.lat, position.lon + 1);
                    case WEST -> new Position(position.lat - 1, position.lon);
                };
                if (positionWithinGrid(newPosition) && obstructions[newPosition.lat][newPosition.lon]) {
                    direction = turnRight(direction);
                } else {
                    position = newPosition;
                }
                steps++;
            }
            return steps < 15_000 ? positions : emptySet();
        }

        private boolean positionWithinGrid(Position position) {
            return 0 <= position.lat && position.lat < gridWidth() && 0 <= position.lon && position.lon < gridHeight();
        }

        private Direction turnRight(Direction direction) {
            return switch (direction) {
                case NORTH -> EAST;
                case EAST -> SOUTH;
                case SOUTH -> WEST;
                case WEST -> NORTH;
            };
        }

        private int gridHeight() {
            return obstructions.length;
        }

        private int gridWidth() {
            return obstructions[0].length;
        }

        public Grid copyWithObstruction(Position position) {
            boolean[][] newObstructions = Arrays.stream(obstructions)
                .map(ArrayUtils::clone)
                .toArray(_ -> obstructions.clone());
            newObstructions[position.lat][position.lon] = true;
            return new Grid(newObstructions);
        }
    }

    record Position(int lat, int lon) {
    }

    enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }
}
