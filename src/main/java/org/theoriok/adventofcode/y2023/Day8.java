package org.theoriok.adventofcode.y2023;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theoriok.adventofcode.Day;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day8 implements Day<Long, Long> {
    private static final Logger logger = LoggerFactory.getLogger(Day8.class);

    private final List<String> directions;
    private final Map<String, Pair<String, String>> nodes;

    public Day8(List<String> input) {
        directions = Arrays.stream(input.getFirst().split("")).toList();
        nodes = input.subList(2, input.size()).stream()
            .map(Node::fromString)
            .collect(Collectors.toMap(Node::from, node -> Pair.of(node.left, node.right)));
    }

    private record Node(String from, String left, String right) {

        public static Node fromString(String line) {
            var firstSplit = line.split(" = ");
            var secondSplit = firstSplit[1].replace("(", "").replace(")", "").split(", ");
            return new Node(firstSplit[0], secondSplit[0], secondSplit[1]);
        }
    }

    @Override
    public Long firstMethod() {
        var current = "AAA";
        var counter = findLength(current, "ZZZ"::equals);
        logger.info("done with firstMethod");
        return counter;
    }

    private long findLength(String current, Predicate<String> endState) {
        long counter = 0;
        while (!endState.test(current)) {
            var direction = directions.get((int) (counter % directions.size()));
            current = step(current, direction);
            counter++;
        }
        return counter;
    }

    private String step(String current, String direction) {
        var node = nodes.get(current);
        return switch (direction) {
            case "L" -> node.getLeft();
            case "R" -> node.getRight();
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        };
    }

    @Override
    public Long secondMethod() {
        var current = nodes.keySet().stream()
            .filter(from -> from.endsWith("A"))
            .toList();
        logger.info("{} ending with A", current.size());
        return current.parallelStream()
            .map(thisCurrent -> findLength(thisCurrent, from -> from.endsWith("Z")))
            .reduce(this::lowestCommonMultiple)
            .orElseThrow();
    }

    long greatestCommonDivisor(long first, long second) {
        return second == 0 ? first : greatestCommonDivisor(second, first % second);
    }

    long lowestCommonMultiple(long first, long second) {
        return (first * second) / greatestCommonDivisor(first, second);
    }
}
