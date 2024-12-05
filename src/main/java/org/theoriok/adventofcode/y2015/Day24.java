package org.theoriok.adventofcode.y2015;

import static java.util.stream.Collectors.toSet;

import org.paukov.combinatorics3.Generator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day24 implements Day<Integer, Integer> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day24.class);
    private final List<Integer> presentWeights;

    public Day24(List<String> input) {
        presentWeights = input.stream()
            .map(Integer::parseInt)
            .toList();
    }

    @Override
    public Integer firstMethod() {
        List<List<Integer>> allCombinations = new ArrayList<>();
        for (int i = 1; i <= presentWeights.size() / 2; i++) {
            Generator.combination(presentWeights)
                .simple(i)
                .stream()
                .forEach(allCombinations::add);
            LOGGER.debug("Generated all combinations with {} elements", i);
        }
        LOGGER.debug("Generated all combinations");
        var combos = Generator.combination(allCombinations)
            .simple(3)
            .stream()
            .filter(this::containsAllWeights)
            .filter(this::isEvenlySplit)
            .map(Combo::from)
            .toList();
        LOGGER.debug("Generated all combos");

        var answer = combos.stream()
            .min(getComparator())
            .map(Combo::quantumEntanglement)
            .orElse(0);
        LOGGER.info("Answer = {}", answer);
        return answer;
    }

    private Comparator<Combo> getComparator() {
        return Comparator.comparing((Combo combo) -> combo.passenger.size())
            .thenComparing(Combo::quantumEntanglement);
    }

    private boolean containsAllWeights(List<List<Integer>> combo) {
        return combo.stream()
            .flatMap(List::stream)
            .collect(toSet())
            .containsAll(presentWeights);
    }

    private boolean isEvenlySplit(List<List<Integer>> combo) {
        return combo.stream()
            .map(this::sumList)
            .collect(toSet())
            .size() == 1;
    }

    private int sumList(List<Integer> list) {
        return list.stream()
            .mapToInt(Integer::intValue)
            .sum();
    }

    @Override
    public Integer secondMethod() {
        return 0;
    }

    record Combo(List<Integer> passenger, List<Integer> left, List<Integer> right) {

        public static Combo from(List<List<Integer>> combo) {
            return new Combo(combo.get(0), combo.get(1), combo.get(2));
        }

        public Integer quantumEntanglement() {
            return passenger.stream()
                .mapToInt(Integer::intValue)
                .reduce(1, (weight1, weight2) -> weight1 * weight2);
        }
    }
}
