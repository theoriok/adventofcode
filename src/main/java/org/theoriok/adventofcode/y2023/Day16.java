package org.theoriok.adventofcode.y2023;

import static org.theoriok.adventofcode.y2023.Day16.Direction.EAST;
import static org.theoriok.adventofcode.y2023.Day16.Direction.NORTH;
import static org.theoriok.adventofcode.y2023.Day16.Direction.SOUTH;
import static org.theoriok.adventofcode.y2023.Day16.Direction.WEST;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theoriok.adventofcode.Day;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Day16 implements Day<Integer, Long> {

    private final List<String> input;
    private final Grid grid;
    private final List<Pair<Point, Direction>> energizedPoints;
    private static final Logger logger = LoggerFactory.getLogger(Day16.class);
    private final ArrayDeque<Pair<Point, Direction>>
        toVisit;

    public Day16(List<String> input) {
        Point[][] points = new Point[input.getFirst().length()][input.size()];
        for (int j = 0; j < input.size(); j++) {
            String line = input.get(j);
            for (int i = 0; i < line.length(); i++) {
                points[i][j] = new Point(i, j, Type.fromString(String.valueOf(line.charAt(i))));
            }
        }
        grid = new Grid(points);
        this.input = input;
        energizedPoints = new ArrayList<>();
        toVisit = new ArrayDeque<>();
    }

    @Override
    public Integer firstMethod() {
        toVisit.add(Pair.of(grid.topRight(), EAST));
        while (!toVisit.isEmpty()) {
            addAndMove(toVisit.pollFirst());
        }
        List<Point> distinctPoints = getDistinctPoints();
        logger.info(grid.toString(distinctPoints));
        return distinctPoints.size();
    }

    private List<Point> getDistinctPoints() {
        return energizedPoints.stream()
            .map(Pair::getLeft)
            .distinct()
            .toList();
    }

    private void addAndMove(Pair<Point, Direction> pair) {
        energizedPoints.add(pair);
        List<Direction> directions = pair.getLeft().type.getDirectionsWhenHeading(pair.getRight());
        for (Direction directionToGo : directions) {
            grid.findPoint(pair.getLeft(), directionToGo).ifPresent(nextPoint -> {
                Pair<Point, Direction> nextOne = Pair.of(nextPoint, directionToGo);
                if (!energizedPoints.contains(nextOne)) {
                    toVisit.add(nextOne);
                }
            });
        }
    }

    @Override
    public Long secondMethod() {
        return 0L;
    }

    enum Type {
        SPACE(".") {
            @Override
            List<Direction> getDirectionsWhenHeading(Direction direction) {
                return List.of(direction);
            }
        },
        LURD_MIRROR("/") {
            @Override
            List<Direction> getDirectionsWhenHeading(Direction direction) {
                return switch (direction) {
                    case NORTH -> List.of(EAST);
                    case EAST -> List.of(NORTH);
                    case SOUTH -> List.of(WEST);
                    case WEST -> List.of(SOUTH);
                };
            }
        },
        RULD_MIRROR("\\") {
            @Override
            List<Direction> getDirectionsWhenHeading(Direction direction) {
                return switch (direction) {
                    case NORTH -> List.of(WEST);
                    case EAST -> List.of(SOUTH);
                    case SOUTH -> List.of(EAST);
                    case WEST -> List.of(NORTH);
                };
            }
        },
        VERTICAL_SPLITTER("|") {
            @Override
            List<Direction> getDirectionsWhenHeading(Direction direction) {
                return switch (direction) {
                    case EAST, WEST -> List.of(NORTH, SOUTH);
                    default -> List.of(direction);
                };
            }
        },
        HORIZONTAL_SPLITTER("-") {
            @Override
            List<Direction> getDirectionsWhenHeading(Direction direction) {
                return switch (direction) {
                    case NORTH, SOUTH -> List.of(EAST, WEST);
                    default -> List.of(direction);
                };
            }
        };

        final String character;

        abstract List<Direction> getDirectionsWhenHeading(Direction direction);

        Type(String character) {
            this.character = character;
        }

        static Type fromString(String input) {
            return Arrays.stream(values())
                .filter(type -> type.character.equals(input))
                .findFirst()
                .orElseThrow();
        }
    }

    enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }

    record Point(int xCoord, int yCoord, Type type) {

    }

    record Grid(Point[][] points) {
        Optional<Point> findPoint(Point origin, Direction direction) {
            switch (direction) {
                case NORTH -> {
                    if (origin.yCoord > 0) {
                        return Optional.of(points[origin.xCoord][origin.yCoord - 1]);
                    }
                }
                case EAST -> {
                    if (origin.xCoord < points.length - 1) {
                        return Optional.of(points[origin.xCoord + 1][origin.yCoord]);
                    }
                }
                case SOUTH -> {
                    if (origin.yCoord < points[0].length - 1) {
                        return Optional.of(points[origin.xCoord][origin.yCoord + 1]);
                    }
                }
                case WEST -> {
                    if (origin.xCoord > 0) {
                        return Optional.of(points[origin.xCoord - 1][origin.yCoord]);
                    }
                }
            }
            return Optional.empty();
        }

        public Point topRight() {
            return points[0][0];
        }

        public String toString(List<Point> distinctPoints) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < points[0].length; j++) {
                for (int i = 0; i < points.length; i++) {
                    Point point = points[i][j];
                    stringBuilder.append(distinctPoints.contains(point) ? "#" : point.type.character);
                }
                stringBuilder.append(System.lineSeparator());
            }

            return stringBuilder.toString();
        }
    }
}
