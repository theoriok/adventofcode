package org.theoriok.adventofcode.y2021;

import static java.util.Collections.emptyList;

import org.apache.commons.lang3.math.NumberUtils;
import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

public class Day18 extends Day<Integer, Integer> {

    public Day18(List<String> input) {
        super(input);
    }

    abstract static class SnailFishNumber {
        SnailFishNumber parent;

        static SnailFishNumber fromString(String input) {
            var stack = new Stack<SnailFishNumber>();
            Arrays.stream(input.split("")).forEach(character -> {
                if (NumberUtils.isCreatable(character)) {
                    stack.add(new RegularNumber(Integer.parseInt(character)));
                }
                if ("]".equals(character)) {
                    var right = stack.pop();
                    var left = stack.pop();
                    stack.add(new PairNumber(left, right));
                }
            });
            return stack.firstElement();
        }

        abstract int magnitude();

        abstract boolean split();

        abstract List<RegularNumber> regularsInOrder();

        abstract List<PairNumberDepth> pairsInOrderWithDepth(int depth);

        public abstract SnailFishNumber copy();

        SnailFishNumber root() {
            return Optional.ofNullable(parent).map(SnailFishNumber::root).orElse(this);
        }

        @SuppressWarnings("SuspiciousMethodCalls")
        Boolean explode() {
            var pairs = root().pairsInOrderWithDepth(0);
            var explodingPair = pairs.stream()
                .filter(it -> it.depth == 4)
                .findFirst()
                .map(PairNumberDepth::pair);

            if (explodingPair.isPresent()) {
                var regulars = root().regularsInOrder();
                var pairNumber = explodingPair.get();
                var indexOfLeft = regulars.indexOf(pairNumber.leftNumber);
                if (indexOfLeft > 0) {
                    regulars.get(indexOfLeft - 1).addValue((RegularNumber) pairNumber.leftNumber);
                }
                var indexOfRight = regulars.indexOf(pairNumber.rightNumber);
                if (indexOfRight < regulars.size() - 1) {
                    regulars.get(indexOfRight + 1).addValue((RegularNumber) pairNumber.rightNumber);
                }

                ((PairNumber) pairNumber.parent).childHasExploded(pairNumber);
                return true;
            }
            return false;
        }

        void reduce() {
            boolean didSomething;
            do {
                didSomething = explode() || split();
            } while (didSomething);
        }

        static PairNumber add(SnailFishNumber one, SnailFishNumber other) {
            var pairNumber = new PairNumber(one, other);
            pairNumber.reduce();
            return pairNumber;
        }
    }

    static class RegularNumber extends SnailFishNumber {
        private int value;

        RegularNumber(int value) {
            this.value = value;
        }

        @Override
        int magnitude() {
            return value;
        }

        @Override
        boolean split() {
            return false;
        }

        @Override
        List<RegularNumber> regularsInOrder() {
            return List.of(this);
        }

        @Override
        List<PairNumberDepth> pairsInOrderWithDepth(int depth) {
            return emptyList();
        }

        @Override
        public SnailFishNumber copy() {
            return new RegularNumber(value);
        }

        void addValue(RegularNumber amount) {
            this.value += amount.value;
        }

        PairNumber splitToPair(SnailFishNumber splitParent) {
            var pairNumber = new PairNumber(new RegularNumber((int) Math.floor(value / 2.0)), new RegularNumber((int) Math.ceil(value / 2.0)));
            pairNumber.parent = splitParent;
            return pairNumber;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    static class PairNumber extends SnailFishNumber {
        private SnailFishNumber leftNumber;
        private SnailFishNumber rightNumber;

        PairNumber(SnailFishNumber leftNumber, SnailFishNumber rightNumber) {
            this.leftNumber = leftNumber;
            this.rightNumber = rightNumber;
            this.leftNumber.parent = this;
            this.rightNumber.parent = this;
        }

        @Override
        int magnitude() {
            return leftNumber.magnitude() * 3 + rightNumber.magnitude() * 2;
        }

        @Override
        boolean split() {
            if (leftNumber instanceof RegularNumber left && left.value >= 10) {
                leftNumber = left.splitToPair(this);
                return true;
            }
            var didSplit = leftNumber.split();
            if (didSplit) {
                return true;
            }
            if (rightNumber instanceof RegularNumber right && right.value >= 10) {
                rightNumber = right.splitToPair(this);
                return true;
            }
            return rightNumber.split();
        }

        @Override
        List<RegularNumber> regularsInOrder() {
            List<RegularNumber> regularsInOrder = new ArrayList<>();
            regularsInOrder.addAll(leftNumber.regularsInOrder());
            regularsInOrder.addAll(rightNumber.regularsInOrder());
            return regularsInOrder;
        }

        @Override
        List<PairNumberDepth> pairsInOrderWithDepth(int depth) {
            List<PairNumberDepth> pairsInOrderWithDepth = new ArrayList<>();
            pairsInOrderWithDepth.addAll(leftNumber.pairsInOrderWithDepth(depth + 1));
            pairsInOrderWithDepth.addAll(List.of(new PairNumberDepth(depth, this)));
            pairsInOrderWithDepth.addAll(rightNumber.pairsInOrderWithDepth(depth + 1));
            return pairsInOrderWithDepth;
        }

        @Override
        public SnailFishNumber copy() {
            return new PairNumber(leftNumber.copy(), rightNumber.copy());
        }

        public void childHasExploded(PairNumber child) {
            var replacement = new RegularNumber(0);
            replacement.parent = child.parent;
            if (leftNumber == child) {
                leftNumber = replacement;
            } else {
                rightNumber = replacement;
            }
        }

        @Override
        public String toString() {
            return "[" + leftNumber + "," + rightNumber + "]";
        }
    }

    static record PairNumberDepth(int depth, PairNumber pair) {
    }

    @Override
    public Integer firstMethod() {
        return input.stream()
            .map(SnailFishNumber::fromString)
            .reduce(SnailFishNumber::add)
            .map(SnailFishNumber::magnitude)
            .orElse(0);
    }

    @Override
    public Integer secondMethod() {
        var snailFishNumbers = input.stream()
            .map(SnailFishNumber::fromString)
            .toList();
        List<SnailFishNumber> addedSnailFishNumbers = new ArrayList<>();
        for (int i = 0; i < snailFishNumbers.size(); i++) {
            for (int j = i + 1; j < snailFishNumbers.size(); j++) {
                addedSnailFishNumbers.add(SnailFishNumber.add(snailFishNumbers.get(i).copy(), snailFishNumbers.get(j).copy()));
                addedSnailFishNumbers.add(SnailFishNumber.add(snailFishNumbers.get(j).copy(), snailFishNumbers.get(i).copy()));
            }
        }

        return addedSnailFishNumbers.stream()
            .mapToInt(SnailFishNumber::magnitude)
            .max().orElse(0);
    }
}
