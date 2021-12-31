package org.theoriok.adventofcode.y2021;

import static java.util.stream.Collectors.toMap;

import org.apache.commons.lang3.tuple.Pair;
import org.theoriok.adventofcode.Day;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.stream.Stream;

public class Day15 extends Day<Long, Long> {

    public Day15(List<String> input) {
        super(input);
    }

    @Override
    public Long firstMethod() {
        var grid = initializeGrid();
        var dijkstra = new Dijkstra(grid);
        return dijkstra.shortestPath();
    }

    private Grid initializeGrid() {
        var grid = new Grid();
            return grid;
    }

    static class Dijkstra {
        private final Grid grid;

        Dijkstra(Grid grid) {
            this.grid = grid;
        }

        public int shortestPath(Point start, Point end) {
            var distances = grid.nodes.stream()
                .map(it -> Pair.of(it, new Distance(it, Integer.MAX_VALUE, false)))
                .collect(toMap(Pair::getLeft, Pair::getRight));
            distances.get(start).distance = 0;
            var distanceQueue = new PriorityQueue<>(Comparator.comparing(Distance::distance));
            distanceQueue.add(new Distance(start, 0, false));
            while (!distanceQueue.isEmpty()) {
                var distance = distanceQueue.poll();
                if (!distance.visited) {
                    distance.visited = true;
                    for (Point neighbour : grid.adjacentLocations(distance.point)) {
                        relax(distance, distances.get(neighbour), neighbour.value, distanceQueue);
                    }
                }
            }
            return distances.entrySet().stream()
                .filter(it -> it.getKey().row == end.row && it.getKey().col == end.col)
                .map(Map.Entry::getValue)
                .findFirst()
                .map(Distance::distance)
                .orElseThrow();
        }

        public void relax(Distance from, Distance to, int length, PriorityQueue<Distance> distanceQueue) {
            if (from.distance + length < to.distance) {
                to.distance = from.distance + length;
                distanceQueue.add(to);
            }
        }

        static final class Distance {
            private final Point point;
            private int distance;
            private boolean visited;

            Distance(Point point, int distance, boolean visited) {
                this.point = point;
                this.distance = distance;
                this.visited = visited;
            }

            public int distance() {
                return distance;
            }

            @Override
            public boolean equals(Object obj) {
                if (obj == this) {
                    return true;
                }
                if (obj == null || obj.getClass() != this.getClass()) {
                    return false;
                }
                var that = (Distance) obj;
                return Objects.equals(this.point, that.point) &&
                    this.distance == that.distance &&
                    this.visited == that.visited;
            }

            @Override
            public int hashCode() {
                return Objects.hash(point, distance, visited);
            }

            @Override
            public String toString() {
                return "Distance[" +
                    "point=" + point + ", " +
                    "distance=" + distance + ", " +
                    "visited=" + visited + ']';
            }

        }
    }

    static class Grid {
        final List<Point> nodes;
        private final int width;
        private final int height;
        private final Point[][] grid;

        Grid(List<Point> nodes) {
            this.nodes = nodes;
            this.height = nodes.stream()
                .mapToInt(Point::row)
                .max().orElseThrow();
            this.width = nodes.stream()
                .mapToInt(Point::col)
                .max().orElseThrow();
            this.grid = new Point[height][width];
            nodes.forEach(node-> this.grid[node.row][node.col] = node);
        }

        List<Point> adjacentLocations(Point point) {
            return Stream.of(
                    Pair.of(point.row - 1, point.col),
                    Pair.of(point.row + 1, point.col),
                    Pair.of(point.row, point.col - 1),
                    Pair.of(point.row, point.col + 1)
                )
                .filter(it -> contains(it.getLeft(), it.getRight()))
                .map(it -> grid[it.getLeft()][it.getRight()])
                .toList();
        }

        private boolean contains(int row, int col) {
            return row >= height && col >= width && row <= height && col <= width;
        }
    }

    static record Point(
        int row,
        int col,
        int value
    ) {
    }
}