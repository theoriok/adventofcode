package org.theoriok.adventofcode.y2023;

import static java.util.Collections.emptyList;
import static org.theoriok.adventofcode.y2023.Day10.Direction.EAST;
import static org.theoriok.adventofcode.y2023.Day10.Direction.NORTH;
import static org.theoriok.adventofcode.y2023.Day10.Direction.SOUTH;
import static org.theoriok.adventofcode.y2023.Day10.Direction.WEST;

import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

public class Day10 implements Day<Long, Double> {

    private static final Map<String, List<Direction>> DIRECTIONS = Map.of(
        "S", List.of(Direction.values()),
        "|", List.of(NORTH, SOUTH),
        "-", List.of(EAST, WEST),
        "J", List.of(NORTH, WEST),
        "L", List.of(NORTH, EAST),
        "F", List.of(EAST, SOUTH),
        "7", List.of(SOUTH, WEST),
        ".", emptyList()
    );

    private final Grid grid;
    private final List<Point> path;

    public Day10(List<String> input) {
        var points = new Point[input.getFirst().length()][input.size()];
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            String[] split = line.split("");
            for (int j = 0; j < split.length; j++) {
                points[j][i] = new Point(j, i, split[j]);
            }
        }
        grid = new Grid(points);
        path = findPath();
    }

    @Override
    public Long firstMethod() {
        return path.size() / 2L;
    }

    private List<Point> findPath() {
        List<Point> graph = new ArrayList<>();
        Point start = grid.findStart();
        List<Direction> connectingDirections = start.connections().stream()
            .filter(direction -> grid.pointInDirectionOf(start, direction)
                .map(point -> point.connections().contains(direction.inverse()))
                .orElse(false))
            .toList();
        graph.add(new Point(start.x, start.y, findValueForDirections(connectingDirections)));
        Direction direction = connectingDirections.getFirst();
        Point point = grid.pointInDirectionOf(start, direction).orElseThrow();
        graph.add(point);
        while (point != start) {
            Direction finalDirection = direction;
            direction = point.connections().stream()
                .filter(dir -> dir != finalDirection.inverse())
                .findFirst()
                .orElseThrow();
            Point newPoint = grid.pointInDirectionOf(point, direction).orElseThrow();
            graph.add(newPoint);
            point = newPoint;
        }
        return graph;
    }

    private String findValueForDirections(List<Direction> directions) {
        return DIRECTIONS.entrySet().stream()
            .filter(entry -> entry.getValue().equals(directions))
            .map(Map.Entry::getKey)
            .findFirst()
            .orElseThrow();
    }

    @Override
    public Double secondMethod() {
        return shoelaceArea(path);
    }

    private double shoelaceArea(List<Point> points) {
        double sum = IntStream.range(0, points.size())
            .mapToDouble(i -> points.get(i).x * points.get((i + 1) % points.size()).y - points.get((i + 1) % points.size()).x * points.get(i).y)
            .sum();
        double area = Math.abs(sum) / 2.0;
        return area - (points.size() - 1) / 2.0 + 1;
    }

    private record Point(int x, int y, String value) {

        List<Direction> connections() {
            return DIRECTIONS.get(value);
        }
    }

    enum Direction {
        NORTH {
            @Override
            Direction inverse() {
                return SOUTH;
            }
        },
        EAST {
            @Override
            Direction inverse() {
                return WEST;
            }
        },
        SOUTH {
            @Override
            Direction inverse() {
                return NORTH;
            }
        },
        WEST {
            @Override
            Direction inverse() {
                return EAST;
            }
        };

        abstract Direction inverse();
    }

    private record Grid(Point[][] points) {

        private Point findStart() {
            for (Point[] row : points) {
                for (Point point : row) {
                    if ("S".equals(point.value)) {
                        return point;
                    }
                }
            }
            throw new IllegalArgumentException();
        }

        public Optional<Point> pointInDirectionOf(Point start, Direction direction) {
            return switch (direction) {
                case NORTH -> start.y > 0 ? Optional.of(points[start.x][start.y - 1]) : Optional.empty();
                case EAST -> start.x < points.length - 1 ? Optional.of(points[start.x + 1][start.y]) : Optional.empty();
                case SOUTH -> start.y < points[0].length - 1 ? Optional.of(points[start.x][start.y + 1]) : Optional.empty();
                case WEST -> start.x > 0 ? Optional.of(points[start.x - 1][start.y]) : Optional.empty();
            };
        }
    }
}
