package org.theoriok.adventofcode.y2023;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theoriok.adventofcode.Day;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day8 implements Day<Integer, Integer> {
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
    public Integer firstMethod() {
        var current = "AAA";
        var counter = 0;
        while (!"ZZZ".equals(current)) {
            var direction = directions.get(counter % directions.size());
            current = step(current, direction);
            counter++;
        }
        logger.info("done with firstMethod");
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
    public Integer secondMethod() {
        var current = nodes.keySet().stream()
            .filter(from -> from.endsWith("A"))
            .toList();
        logger.info("{} ending with A", current.size());
        var counter = 0;
        while (!current.parallelStream().allMatch(from -> from.endsWith("Z"))) {
            var direction = directions.get(counter % directions.size());
            current = current.parallelStream()
                .map(thisCurrent -> step(thisCurrent, direction))
                .toList();
            counter++;
            logger.info("{} steps", counter);
        }
        return counter;
    }
}
