package org.theoriok.adventofcode.y2021;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class Day14 extends Day {

    public static final String ARROW = " -> ";
    private final Map<String, String> formulas;

    public Day14(List<String> input) {
        super(input);
        formulas = getFormulas();
    }

    @Override
    public long firstMethod() {
        return iterateTimes(10);
    }

    @Override
    public long secondMethod() {
        return iterateTimes(40);
    }

    private long iterateTimes(int times) {
        var polymer = input.get(0);
        Map<String, AtomicLong> pairs = new HashMap<>();
        var polymerLetters = polymer.split("");
        for (int i = 1; i < polymerLetters.length; i++) {
            var key = polymerLetters[i - 1] + polymerLetters[i];
            pairs.computeIfAbsent(key, newAtomicLong()).incrementAndGet();
        }
        for (int i = 0; i < times; i++) {
            pairs = iterate(pairs);
        }
        var letterCounts = pairs.keySet().stream()
            .flatMap(key -> Arrays.stream(key.split("")))
            .distinct()
            .collect(toMap(Function.identity(), newAtomicLong()));
        pairs.forEach((pair, count) -> {
            letterCounts.get(pair.substring(0, 1)).addAndGet(count.get());
            letterCounts.get(pair.substring(1, 2)).addAndGet(count.get());
        });

        return ((highestLetterCount(letterCounts) - lowestLetterCount(letterCounts)) / 2) + 1;
    }

    private long lowestLetterCount(Map<String, AtomicLong> letterCounts) {
        return letterCounts.values().stream()
            .mapToLong(AtomicLong::get)
            .min().orElse(0L);
    }

    private long highestLetterCount(Map<String, AtomicLong> letterCounts) {
        return letterCounts.values().stream()
            .mapToLong(AtomicLong::get).max()
            .orElse(0L);
    }

    private Function<String, AtomicLong> newAtomicLong() {
        return any -> new AtomicLong();
    }

    private Map<String, AtomicLong> iterate(Map<String, AtomicLong> pairs) {
        Map<String, AtomicLong> newPairs = new HashMap<>();
        pairs.forEach((pair, count) -> {
            if (formulas.containsKey(pair)) {
                var newLetter = formulas.get(pair);
                newPairs.computeIfAbsent(pair.charAt(0) + newLetter, newAtomicLong()).addAndGet(count.get());
                newPairs.computeIfAbsent(newLetter + pair.charAt(1), newAtomicLong()).addAndGet(count.get());
            } else {
                newPairs.computeIfAbsent(pair, newAtomicLong()).addAndGet(count.get());
            }
        });
        return newPairs;
    }

    private String iterate1(String polymer) {
        var polymerLetters = polymer.split("");
        List<String> newPolymer = new ArrayList<>();
        newPolymer.add(polymerLetters[0]);
        for (int i = 1; i < polymerLetters.length; i++) {
            var key = polymerLetters[i - 1] + polymerLetters[i];
            if (formulas.containsKey(key)) {
                newPolymer.add(formulas.get(key));
            }
            newPolymer.add(polymerLetters[i]);
        }
        return String.join("", newPolymer);
    }

    private Map<String, String> getFormulas() {
        return input.stream()
            .filter(line -> line.contains(ARROW))
            .map(line -> line.split(ARROW))
            .collect(toMap(split -> split[0], split -> split[1]));
    }
}
