package org.theoriok.adventofcode.y2015;

import static java.util.stream.Collectors.groupingBy;

import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Day14 implements Day<Integer, Integer> {

    public static final int TRADITIONAL_RACE_TIME = 2503;
    private final List<Reindeer> reindeers;

    public Day14(List<String> input) {
        reindeers = input.stream()
            .map(Reindeer::from)
            .toList();
    }

    @Override
    public Integer firstMethod() {
        return firstMethod(TRADITIONAL_RACE_TIME);
    }

    Integer firstMethod(int seconds) {
        return reindeers.stream()
            .mapToInt(reindeer -> reindeer.distanceAfter(seconds))
            .max()
            .orElse(0);
    }

    @Override
    public Integer secondMethod() {
        return secondMethod(TRADITIONAL_RACE_TIME);
    }

    public Integer secondMethod(int seconds) {
        Map<Reindeer, AtomicInteger> score = new HashMap<>();
        IntStream.rangeClosed(1, seconds)
            .forEach(second ->
                leadingReindeerAt(second)
                    .forEach(reindeer -> score.computeIfAbsent(reindeer, _ -> new AtomicInteger(0)).incrementAndGet())
            );

        return score.values().stream()
            .mapToInt(AtomicInteger::get)
            .max().orElse(0);
    }

    private List<Reindeer> leadingReindeerAt(int seconds) {
        return reindeers.stream()
            .collect(groupingBy(reindeer -> reindeer.distanceAfter(seconds)))
            .entrySet()
            .stream()
            .max(Map.Entry.comparingByKey())
            .map(Map.Entry::getValue)
            .orElse(new ArrayList<>());
    }

    record Reindeer(String name, int speedInKmPerSecond, int secondsToFly, int secondsToRest) {
        public static Reindeer from(String line) {
            String[] elements = line.split(" ");
            return new Reindeer(elements[0], Integer.parseInt(elements[3]), Integer.parseInt(elements[6]), Integer.parseInt(elements[13]));
        }

        public int distanceAfter(int seconds) {
            int distance = 0;
            int tick = 0;

            while (tick + getTick() < seconds) {
                distance += speedInKmPerSecond * secondsToFly;
                tick += getTick();
            }
            int secondsLeft = seconds - tick;
            distance += speedInKmPerSecond * Math.min(secondsLeft, secondsToFly);
            return distance;
        }

        private int getTick() {
            return secondsToFly + secondsToRest;
        }
    }
}