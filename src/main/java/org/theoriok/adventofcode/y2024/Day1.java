package org.theoriok.adventofcode.y2024;

import org.jgrapht.alg.util.Pair;
import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Gatherers;
import java.util.stream.IntStream;

public class Day1 implements Day<Integer, Long> {

    private final Lists lists;

    public Day1(List<String> input) {
        lists = input.stream()
            .gather(
                Gatherers.fold(
                    () -> Pair.of(new ArrayList<Integer>(), new ArrayList<Integer>()),
                    (result, element) -> {
                        String[] split = element.split(" {3}");
                        result.getFirst().add(Integer.parseInt(split[0]));
                        result.getSecond().add(Integer.parseInt(split[1]));
                        return result;
                    }
                )
            )
            .findFirst()
            .map(pair -> new Lists(pair.getFirst(), pair.getSecond()))
            .orElseGet(() -> new Lists(List.of(), List.of()));
    }

    @Override
    public Integer firstMethod() {
        return lists.compareSorted();
    }

    @Override
    public Long secondMethod() {
        return lists.similarity();
    }

    record Lists(List<Integer> first, List<Integer> second) {
        public int compareSorted() {
            List<Integer> firstSorted = first.stream()
                .sorted()
                .toList();
            List<Integer> secondSorted = second.stream()
                .sorted()
                .toList();
            return IntStream.range(0, first.size())
                .map(index -> Math.abs(firstSorted.get(index) - secondSorted.get(index)))
                .sum();
        }

        public long similarity() {
            return first.stream()
                .mapToLong(number -> number * countInSecond(number))
                .sum();
        }

        private long countInSecond(int numberFromFirstList) {
            return second.stream()
                .filter(number -> number.equals(numberFromFirstList))
                .count();
        }
    }

}
