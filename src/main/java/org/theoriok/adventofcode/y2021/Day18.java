package org.theoriok.adventofcode.y2021;

import org.theoriok.adventofcode.Day;

import java.util.List;

public class Day18 extends Day<Integer, Integer> {

    public Day18(List<String> input) {
        super(input);
        var pairs 
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
            if (value instanceof Integer) {
                return (Integer) value;
            } else if (value instanceof Pair) {
                return ((Pair<?, ?>) value).magnitude();
            } else {
                throw new IllegalArgumentException();
            }
        }
    }
}
