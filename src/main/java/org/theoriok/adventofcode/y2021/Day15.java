package org.theoriok.adventofcode.y2021;

import static java.util.stream.Collectors.toMap;

import org.apache.commons.lang3.tuple.Pair;
import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.stream.Stream;

public class Day15 implements Day<Integer, Integer> {

    private final List<String> input;

    public Day15(List<String> input) {
        this.input = input;
    }

    @Override
    public Integer firstMethod() {
        var grid = initializeGrid();
        var dijkstra = new Dijkstra(grid);
        return dijkstra.shortestPath(grid.start(), grid.end());
    }

    @Override
    public Integer secondMethod() {
        var grid = initializeGridExpanded();
        var dijkstra = new Dijkstra(grid);
        return dijkstra.shortestPath(grid.start(), grid.end());
    }

    private Grid initializeGrid() {
        var nodes = new ArrayList<Point>();
        for (int i = 0; i < input.size(); i++) {
            var line = input.get(i);
            var chars = line.split("");
            for (int j = 0; j < chars.length; j++) {
                nodes.add(new Point(i, j, Integer.parseInt(chars[j])));
            }
        }
        return new Grid(nodes);
    }

    private Grid initializeGridExpanded() {
        var nodes = new ArrayList<Point>();
        for (int i = 0; i < input.size(); i++) {
            var line = input.get(i);
            var chars = line.split("");
            for (int j = 0; j < chars.length; j++) {
                for (int k = 0; k < 5; k++) {
                    for (int l = 0; l < 5; l++) {
                        nodes.add(new Point(i + k * input.size(), j + l * line.length(), (Integer.parseInt(chars[j]) + k + l - 1) % 9 + 1));
                    }
                }
            }
        }
        return new Grid(nodes);
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
            distances.get(start).dist = 0;
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
            if (from.dist + length < to.dist) {
                to.dist = from.dist + length;
                distanceQueue.add(to);
            }
        }

        static final class Distance {
            private final Point point;
            private int dist;
            private boolean visited;

            Distance(Point point, int distance, boolean visited) {
                this.point = point;
                this.dist = distance;
                this.visited = visited;
            }

            public int distance() {
                return dist;
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
                return Objects.equals(this.point, that.point)
                    && this.dist == that.dist
                    && this.visited == that.visited;
            }

            @Override
            public int hashCode() {
                return Objects.hash(point, dist, visited);
            }

            @Override
            public String toString() {
                return "Distance["
                    + "point=" + point + ", "
                    + "distance=" + dist + ", "
                    + "visited=" + visited + ']';
            }
        }
    }

    static class Grid {
        final List<Point> nodes;
        private final int width;
        private final int height;
        private final Point[][] pointsGrid;

        Grid(List<Point> nodes) {
            this.nodes = nodes;
            this.height = nodes.stream()
                .mapToInt(Point::row)
                .max().orElseThrow() + 1;
            this.width = nodes.stream()
                .mapToInt(Point::col)
                .max().orElseThrow() + 1;
            this.pointsGrid = new Point[height][width];
            nodes.forEach(node -> this.pointsGrid[node.row][node.col] = node);
        }

        List<Point> adjacentLocations(Point point) {
            return Stream.of(
                    Pair.of(point.row - 1, point.col),
                    Pair.of(point.row + 1, point.col),
                    Pair.of(point.row, point.col - 1),
                    Pair.of(point.row, point.col + 1)
                )
                .filter(it -> contains(it.getLeft(), it.getRight()))
                .map(it -> pointsGrid[it.getLeft()][it.getRight()])
                .toList();
        }

        private boolean contains(int row, int col) {
            return row >= 0 && col >= 0 && row < height && col < width;
        }

        public Point start() {
            return pointsGrid[0][0];
        }

        public Point end() {
            return pointsGrid[height - 1][width - 1];
        }
    }

    static record Point(
        int row,
        int col,
        int value
    ) {
    }
}