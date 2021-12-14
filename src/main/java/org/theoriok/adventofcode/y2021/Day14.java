package org.theoriok.adventofcode.y2021;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toMap;

import org.theoriok.adventofcode.Day;

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

        return getCount(polymer, highestLetterCountEntry(letterCounts)) - getCount(polymer, lowestLetterCountEntry(letterCounts));
    }

    private long getCount(String polymer, Map.Entry<String, AtomicLong> entry) {
        var count = entry.getValue().get() / 2;
        if (entry.getKey().equals(polymer.substring(0, 1))) {
            count++;
        }
        if (entry.getKey().equals(polymer.substring(polymer.length() - 1))) {
            count++;
        }
        return count;
    }

    private Map.Entry<String, AtomicLong> lowestLetterCountEntry(Map<String, AtomicLong> letterCounts) {
        return letterCounts.entrySet().stream()
            .min(comparing(entry -> entry.getValue().get()))
            .orElseThrow();
    }

    private Map.Entry<String, AtomicLong> highestLetterCountEntry(Map<String, AtomicLong> letterCounts) {
        return letterCounts.entrySet().stream()
            .max(comparing(entry -> entry.getValue().get()))
            .orElseThrow();
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

    private Map<String, String> getFormulas() {
        return input.stream()
            .filter(line -> line.contains(ARROW))
            .map(line -> line.split(ARROW))
            .collect(toMap(split -> split[0], split -> split[1]));
    }
}
