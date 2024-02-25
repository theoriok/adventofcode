package org.theoriok.adventofcode.y2015;

import static org.theoriok.adventofcode.util.Utils.splitToList;

import org.theoriok.adventofcode.Day;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Day3 implements Day<Integer, Integer> {

    private final List<Direction> path;

    public Day3(List<String> input) {
        path = splitToList(input.getFirst(), "", Direction::fromCharacter);
    }

    @Override
    public Integer firstMethod() {
        Map<Point, AtomicInteger> visits = new HashMap<>();
        Point point = new Point(0, 0);
        visit(visits, point);
        for (Direction direction : path) {
            point = direction.moveFrom(point);
            visit(visits, point);
        }
        return visits.size();
    }

    private static void visit(Map<Point, AtomicInteger> visits, Point point) {
        visits.computeIfAbsent(point, Day3::newAtomicInteger).incrementAndGet();
    }

    private static AtomicInteger newAtomicInteger(Point point) {
        return new AtomicInteger(0);
    }

    @Override
    public Integer secondMethod() {
        Map<Point, AtomicInteger> visits = new HashMap<>();
        Point santaPoint = new Point(0, 0);
        Point roboSantaPoint = new Point(0, 0);
        visit(visits, santaPoint);
        visit(visits, roboSantaPoint);
        for (int i = 0; i < path.size(); i++) {
            Direction direction = path.get(i);
            if (i % 2 == 0) {
                santaPoint = direction.moveFrom(santaPoint);
                visit(visits, santaPoint);
            } else {
                roboSantaPoint = direction.moveFrom(roboSantaPoint);
                visit(visits, roboSantaPoint);
            }
        }
        return visits.size();
    }

    enum Direction {
        NORTH("^") {
            @Override
            Point moveFrom(Point point) {
                return new Point(point.lat, point.lon - 1);
            }
        },
        EAST(">") {
            @Override
            Point moveFrom(Point point) {
                return new Point(point.lat + 1, point.lon);
            }
        },
        SOUTH("v") {
            @Override
            Point moveFrom(Point point) {
                return new Point(point.lat, point.lon + 1);
            }
        },
        WEST("<") {
            @Override
            Point moveFrom(Point point) {
                return new Point(point.lat - 1, point.lon);
            }
        };

        private final String character;

        Direction(String character) {
            this.character = character;
        }

        abstract Point moveFrom(Point point);

        static Direction fromCharacter(String character) {
            return Arrays.stream(values())
                .filter(direction -> direction.character.equals(character))
                .findFirst()
                .orElseThrow();
        }
    }

    record Point(int lat, int lon) {
    }
}
