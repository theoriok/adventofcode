package org.theoriok.adventofcode.y2023;

import static java.util.Collections.emptyList;
import static org.theoriok.adventofcode.y2023.Day10.Direction.EAST;
import static org.theoriok.adventofcode.y2023.Day10.Direction.NORTH;
import static org.theoriok.adventofcode.y2023.Day10.Direction.SOUTH;
import static org.theoriok.adventofcode.y2023.Day10.Direction.WEST;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.theoriok.adventofcode.Day;

import java.util.List;
import java.util.Optional;

public class Day10 implements Day<Long, Long> {

    private final List<String> input;
    private final Grid grid;

    public Day10(List<String> input) {
        this.input = input;
        var points = new Point[input.getFirst().length()][input.size()];
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            String[] split = line.split("");
            for (int j = 0; j < split.length; j++) {
                points[j][i] = new Point(j, i, split[j]);
            }
        }
        grid = new Grid(points);
    }

    @Override
    public Long firstMethod() {
        var graph = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        Point start = grid.findStart();
        graph.addVertex(start.toString());
        List<Direction> connectingDirections = start.connections().stream()
            .filter(direction -> grid.pointInDirectionOf(start, direction)
                .map(point -> point.connections().contains(direction.inverse()))
                .orElse(false))
            .toList();
        assert connectingDirections.size() == 2;
        Direction direction = connectingDirections.getFirst();
        Point point = grid.pointInDirectionOf(start, direction).orElseThrow();
        graph.addVertex(point.toString());
        graph.addEdge(start.toString(), point.toString());
        while (point != start) {
            Direction finalDirection = direction;
            direction = point.connections().stream()
                .filter(dir -> dir != finalDirection.inverse())
                .findFirst()
                .orElseThrow();
            Point newPoint = grid.pointInDirectionOf(point, direction).orElseThrow();
            graph.addVertex(newPoint.toString());
            graph.addEdge(point.toString(), newPoint.toString());
            point = newPoint;
        }

        return graph.vertexSet().size() / 2L;
    }

    @Override
    public Long secondMethod() {
        return 0L;
    }

    private record Point(int x, int y, String value) {

        List<Direction> connections() {
            return switch (value) {
                case "S" -> List.of(Direction.values());
                case "|" -> List.of(NORTH, SOUTH);
                case "-" -> List.of(EAST, WEST);
                case "J" -> List.of(NORTH, WEST);
                case "L" -> List.of(NORTH, EAST);
                case "F" -> List.of(EAST, SOUTH);
                case "7" -> List.of(SOUTH, WEST);
                case "." -> emptyList();
                default -> throw new IllegalStateException("Unexpected value: " + value);
            };
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
        Point findPoint(int x, int y) {
            return points[x][y];
        }

        private Point findStart() {
            for (Point[] row : points) {
                for (Point point : row) {
                    if (point.value.equals("S")) {
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
