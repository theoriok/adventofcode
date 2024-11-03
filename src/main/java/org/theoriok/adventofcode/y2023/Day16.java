package org.theoriok.adventofcode.y2023;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theoriok.adventofcode.Day;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.theoriok.adventofcode.y2023.Day16.Direction.EAST;
import static org.theoriok.adventofcode.y2023.Day16.Direction.NORTH;
import static org.theoriok.adventofcode.y2023.Day16.Direction.SOUTH;
import static org.theoriok.adventofcode.y2023.Day16.Direction.WEST;

public class Day16 implements Day<Long, Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day16.class);

    private final Grid grid;

    public Day16(List<String> input) {
        Point[][] points = new Point[input.getFirst().length()][input.size()];
        for (int j = 0; j < input.size(); j++) {
            String line = input.get(j);
            for (int i = 0; i < line.length(); i++) {
                points[i][j] = new Point(i, j, Type.fromString(String.valueOf(line.charAt(i))));
            }
        }
        grid = new Grid(points);
    }

    @Override
    public Long firstMethod() {
        return calculateEnergizedPoints(Pair.of(grid.topRight(), EAST));
    }

    private long calculateEnergizedPoints(Pair<Point, Direction> startingPair) {
        List<Pair<Point, Direction>> energizedPoints = new ArrayList<>();
        Deque<Pair<Point, Direction>> toVisit = new ArrayDeque<>();
        toVisit.add(startingPair);
        while (!toVisit.isEmpty()) {
            addAndMove(toVisit.pollFirst(), energizedPoints, toVisit);
        }
        return energizedPoints.stream()
            .map(Pair::getLeft)
            .distinct()
            .count();
    }

    private void addAndMove(Pair<Point, Direction> pair, List<Pair<Point, Direction>> energizedPoints, Deque<Pair<Point, Direction>> toVisit) {
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
        AtomicLong counter = new AtomicLong();
        List<Pair<Point, Direction>> edges = grid.edges();
        long answer = edges.stream()
            .peek(pair -> LOGGER.info("%d/%d: %s (%s)".formatted(counter.incrementAndGet(), edges.size(), pair.getLeft(), pair.getRight())))
            .mapToLong(this::calculateEnergizedPoints)
            .max()
            .orElse(0L);
        LOGGER.info("{}", answer);
        return answer;
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
                    case NORTH, SOUTH -> List.of(direction);
                };
            }
        },
        HORIZONTAL_SPLITTER("-") {
            @Override
            List<Direction> getDirectionsWhenHeading(Direction direction) {
                return switch (direction) {
                    case NORTH, SOUTH -> List.of(EAST, WEST);
                    case EAST, WEST -> List.of(direction);
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

    record Point(int xCoordinate, int yCoordinate, Type type) {
    }

    record Grid(Point[][] points) {
        Optional<Point> findPoint(Point origin, Direction direction) {
            return switch (direction) {
                case NORTH -> origin.yCoordinate > 0
                    ? Optional.of(points[origin.xCoordinate][origin.yCoordinate - 1])
                    : Optional.empty();
                case EAST -> origin.xCoordinate < points.length - 1
                    ? Optional.of(points[origin.xCoordinate + 1][origin.yCoordinate])
                    : Optional.empty();
                case SOUTH -> origin.yCoordinate < points[0].length - 1
                    ? Optional.of(points[origin.xCoordinate][origin.yCoordinate + 1])
                    : Optional.empty();
                case WEST -> origin.xCoordinate > 0
                    ? Optional.of(points[origin.xCoordinate - 1][origin.yCoordinate])
                    : Optional.empty();
            };
        }

        public Point topRight() {
            return points[0][0];
        }

        public List<Pair<Point, Direction>> edges() {
            List<Pair<Point, Direction>> edges = new ArrayList<>();
            for (int j = 0; j < points[0].length; j++) {
                edges.add(Pair.of(points[0][j], EAST));
                edges.add(Pair.of(points[points.length - 1][j], WEST));
            }
            for (Point[] point : points) {
                edges.add(Pair.of(point[0], SOUTH));
                edges.add(Pair.of(point[point.length - 1], NORTH));
            }
            return edges;
        }
    }
}
