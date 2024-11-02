package org.theoriok.adventofcode.y2015;

import org.theoriok.adventofcode.Day;

import java.util.List;

public class Day14 implements Day<Integer, Integer> {

    public static final int TRADITIONAL_RACE_TIME = 2503;
    private final List<Reindeer> reindeer;

    public Day14(List<String> input) {
        reindeer = input.stream()
                .map(Reindeer::from)
                .toList();
    }

    @Override
    public Integer firstMethod() {
        return firstMethod(TRADITIONAL_RACE_TIME);
    }

    Integer firstMethod(int seconds) {
        return reindeer.stream()
                .mapToInt(reindeer -> reindeer.distanceAfter(seconds))
                .max()
                .orElse(0);
    }

    @Override
    public Integer secondMethod() {
        return secondMethod(TRADITIONAL_RACE_TIME);
    }

    public Integer secondMethod(int seconds) {
        return seconds;
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