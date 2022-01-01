package org.theoriok.adventofcode.y2021;

import org.theoriok.adventofcode.Day;

import java.util.List;

public class Day18 extends Day<Integer, Integer> {

    private final List<Pair<Object, Object>> pairs;

    public Day18(List<String> input) {
        super(input);
        pairs = input.stream()
            .map(Pair::fromString)
            .toList();
    }

    @Override
    public Integer firstMethod() {
        return 0;
    }

    private static class Pair<L, R> {
        private final L left;
        private final R right;

        private Pair(L left, R right) {
            this.left = left;
            this.right = right;
        }

        public long magnitude() {
            return magnitude(left) * 3 + magnitude(right) * 2;
        }

        private long magnitude(Object value) {
            if (value instanceof Number number) {
                return number.intValue();
            } else if (value instanceof Pair pair) {
                return pair.magnitude();
            }
            throw new IllegalArgumentException();
        }

        public static Pair<Object, Object> fromString(String string) {
            return new Pair<>(0, 0);
        }
    }
}
