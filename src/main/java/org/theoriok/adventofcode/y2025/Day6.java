package org.theoriok.adventofcode.y2025;

import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Gatherer;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.joining;

public class Day6 implements Day<Long, Long> {

    private final List<String> input;

    public Day6(List<String> input) {
        this.input = input;
    }

    @Override
    public Long firstMethod() {
        return getMathAssignmentsForPart1()
            .mapToLong(MathAssignment::calculate)
            .sum();
    }

    private Stream<MathAssignment> getMathAssignmentsForPart1() {
        return input.stream()
            .gather(
                Gatherer.<String, List<List<String>>, MathAssignment>ofSequential(
                    ArrayList::new,
                    (state, element, _) -> {
                        List<String> strings = Arrays.stream(element.split("\\s+"))
                            .filter(s -> !s.isEmpty())
                            .toList();
                        for (int i = 0; i < strings.size(); i++) {
                            if (state.size() <= i) {
                                state.add(new ArrayList<>());
                            }
                            state.get(i).add(strings.get(i));
                        }
                        return true;
                    },
                    (state, downstream) -> state.forEach(list -> {
                        List<Integer> numbers = list.subList(0, list.size() - 1).stream()
                            .map(Integer::parseInt)
                            .toList();
                        var mathAssignment = new MathAssignment(numbers, Operator.from(list.getLast()));
                        downstream.push(mathAssignment);
                    })
                ));
    }

    @Override
    public Long secondMethod() {
        return getMathAssignmentsForPart2()
            .mapToLong(MathAssignment::calculate)
            .sum();
    }

    private Stream<MathAssignment> getMathAssignmentsForPart2() {
        return input.stream()
            .gather(
                Gatherer.<String, List<List<String>>, MathAssignment>ofSequential(
                    ArrayList::new,
                    (state, element, _) -> {
                        List<String> strings = Arrays.stream(element.split("", -1)).toList();
                        for (int i = 0; i < strings.size(); i++) {
                            if (state.size() <= i) {
                                state.add(new ArrayList<>());
                            }
                            state.get(i).add(strings.get(i));
                        }
                        return true;
                    },
                    (state, downstream) -> {
                        var numbers = new ArrayList<Integer>();
                        for (List<String> list : state.reversed()) {
                            if (list.stream().anyMatch(not(String::isBlank))) {
                                String numberAsString = list.subList(0, list.size() - 1).stream()
                                    .map(String::trim)
                                    .filter(not(String::isBlank))
                                    .collect(joining());
                                numbers.add(Integer.parseInt(numberAsString));
                                if (Operator.canParse(list.getLast())) {
                                    var mathAssignment = new MathAssignment(numbers, Operator.from(list.getLast()));
                                    downstream.push(mathAssignment);
                                    numbers = new ArrayList<>();
                                }
                            }
                        }
                    }
                ));
    }

    private record MathAssignment(List<Integer> numbers, Operator operator) {
        public Long calculate() {
            return switch (operator) {
                case ADD -> numbers.stream().mapToLong(Integer::longValue).sum();
                case MULTIPLY -> numbers.stream().mapToLong(Integer::longValue).reduce(1L, Math::multiplyExact);
            };
        }
    }

    private enum Operator {
        ADD, MULTIPLY;

        static Operator from(String input) {
            return switch (input) {
                case "+" -> ADD;
                case "*" -> MULTIPLY;
                default -> throw new IllegalArgumentException("Unknown operator: %s".formatted(input));
            };
        }

        public static boolean canParse(String input) {
            return "+".equals(input) || "*".equals(input);
        }
    }
}
