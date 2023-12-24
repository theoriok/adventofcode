package org.theoriok.adventofcode.y2023;

import static org.theoriok.adventofcode.util.Utils.splitToList;

import org.theoriok.adventofcode.Day;

import java.util.List;

public class Day24 implements Day<Long, Long> {

    private static final long MIN = 200000000000000L;
    private static final long MAX = 400000000000000L;
    private final List<String> input;

    public Day24(List<String> input) {
        this.input = input;
    }

    private record Line(long px, long py, long vx, long vy) {

        public static Line fromString(String line) {
            String[] split = line.split(" @ ");
            List<Long> positionLongs = mapToLongs(split[0]);
            List<Long> directionLongs = mapToLongs(split[1]);
            return new Line(positionLongs.getFirst(), positionLongs.get(1), directionLongs.getFirst(), directionLongs.get(1));
        }

        private static List<Long> mapToLongs(String input) {
            return splitToList(input, ", ", s -> Long.parseLong(s.trim()));
        }
    }

    @Override
    public Long firstMethod() {
        return firstMethod(MIN, MAX);
    }

    Long firstMethod(long min, long max) {
        List<Line> lines = input.stream()
            .map(Line::fromString)
            .toList();
        long counter = 0L;
        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);
            for (int j = i + 1; j < lines.size(); j++) {
                Line otherLine = lines.get(j);
                /* here be borrowed code */
                double denom = (line.vx() * otherLine.vy()) - (line.vy() * otherLine.vx());
                double numer1 = ((otherLine.px() - line.px()) * otherLine.vy()) - ((otherLine.py() - line.py()) * otherLine.vx());
                double numer2 = ((line.px() - otherLine.px()) * line.vy()) - ((line.py() - otherLine.py()) * line.vx());
                double intersectionX = (numer1 / denom) * line.vx() + line.px();
                double intersectionY = (numer1 / denom) * line.vy() + line.py();
                if (between(intersectionX, min, max) && between(intersectionY, min, max)) {
                    if ((numer1 / denom) > 0 && (numer2 / denom) < 0) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    private boolean between(double number, long smallest, long biggest) {
        return smallest <= number && number <= biggest;
    }

    @Override
    public Long secondMethod() {

        return 0L;
    }
}
