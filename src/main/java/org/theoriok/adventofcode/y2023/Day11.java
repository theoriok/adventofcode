package org.theoriok.adventofcode.y2023;

import static java.util.Comparator.comparingInt;

import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.List;

public class Day11 implements Day<Long, Long> {

    private final List<Point> points;

    public Day11(List<String> input) {
        points = new ArrayList<>();
        for (int j = 0, inputSize = input.size(); j < inputSize; j++) {
            String line = input.get(j);
            char[] charArray = line.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                char character = charArray[i];
                if (character == '#') {
                    points.add(new Point(i, j));
                }
            }
        }
    }

    @Override
    public Long firstMethod() {
        List<Point> expandedPoints = expandEmpties(points, 1);
        return calculateDistance(expandedPoints);
    }

    @Override
    public Long secondMethod() {
        List<Point> expandedPoints = expandEmpties(points, 999_999);
        return calculateDistance(expandedPoints);
    }

    private List<Point> expandEmpties(List<Point> points, int factor) {
        return expandRows(expandCols(points, factor), factor);
    }

    private List<Point> expandCols(List<Point> points, int factor) {
        List<Point> expandedPoints = new ArrayList<>();
        int width = points.stream()
            .max(comparingInt(Point::x))
            .map(Point::x)
            .orElse(0);
        int numberToAdd = 0;
        for (int i = 0; i <= width; i++) {
            int finalI = i;
            List<Point> pointsOnCol = points.stream()
                .filter(point -> point.x == finalI)
                .toList();
            if (pointsOnCol.isEmpty()) {
                numberToAdd += factor;
            } else {
                int finalNumberToAdd = numberToAdd;
                expandedPoints.addAll(
                    pointsOnCol.stream()
                        .map(point -> new Point(point.x + finalNumberToAdd, point.y))
                        .toList()
                );
            }
        }
        return expandedPoints;
    }

    private List<Point> expandRows(List<Point> points, int factor) {
        List<Point> expandedPoints = new ArrayList<>();
        int height = points.stream()
            .max(comparingInt(Point::y))
            .map(Point::y)
            .orElse(0);
        int numberToAdd = 0;
        for (int i = 0; i <= height; i++) {
            int finalI = i;
            List<Point> pointsOnRow = points.stream()
                .filter(point -> point.y == finalI)
                .toList();
            if (pointsOnRow.isEmpty()) {
                numberToAdd += factor;
            } else {
                int finalNumberToAdd = numberToAdd;
                expandedPoints.addAll(
                    pointsOnRow.stream()
                        .map(point -> new Point(point.x, point.y + finalNumberToAdd))
                        .toList()
                );
            }
        }
        return expandedPoints;
    }

    private long calculateDistance(List<Point> points) {
        long distance = 0;
        for (int j = 0; j < points.size(); j++) {
            Point point1 = points.get(j);
            for (int i = j + 1; i < points.size(); i++) {
                Point point2 = points.get(i);
                distance += point1.distanceTo(point2);
            }
        }
        return distance;
    }

    private record Point(int x, int y) {
        public int distanceTo(Point point) {
            return Math.abs(x - point.x) + Math.abs(y - point.y);
        }
    }
}
