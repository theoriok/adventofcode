package org.theoriok.adventofcode.y2015;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theoriok.adventofcode.Day;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day6 implements Day<Long, Long> {

    private static final Logger logger = LoggerFactory.getLogger(Day6.class);
    public static final Pattern PATTERN =
        Pattern.compile("(?<command>[a-zA-Z ]+) (?<start1>\\d+),(?<start2>\\d+) through (?<end1>\\d+),(?<end2>\\d+)");
    private final List<String> input;

    public Day6(List<String> input) {
        this.input = input;
        logger.info("Size: {}", this.input.size());
    }

    @Override
    public Long firstMethod() {
        var lights = initLights();

        for (String line : input) {
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.find()) {
                var command = matcher.group("command");
                var start1 = Integer.parseInt(matcher.group("start1"));
                var start2 = Integer.parseInt(matcher.group("start2"));
                var end1 = Integer.parseInt(matcher.group("end1"));
                var end2 = Integer.parseInt(matcher.group("end2"));
                switch (command) {
                    case "turn on" -> turnOn(lights, start1, start2, end1, end2);
                    case "turn off" -> turnOff(lights, start1, start2, end1, end2);
                    case "toggle" -> toggle(lights, start1, start2, end1, end2);
                    default -> {
                    }
                }
            }
        }

        return Arrays.stream(lights)
            .flatMap(Arrays::stream)
            .filter(it -> it)
            .count();
    }

    private void turnOn(Boolean[][] lights, int start1, int start2, int end1, int end2) {
        for (int i = start1; i <= end1; i++) {
            for (int j = start2; j <= end2; j++) {
                lights[i][j] = true;
            }
        }
    }

    private void turnOff(Boolean[][] lights, int start1, int start2, int end1, int end2) {
        for (int i = start1; i <= end1; i++) {
            for (int j = start2; j <= end2; j++) {
                lights[i][j] = false;
            }
        }
    }

    private void toggle(Boolean[][] lights, int start1, int start2, int end1, int end2) {
        for (int i = start1; i <= end1; i++) {
            for (int j = start2; j <= end2; j++) {
                lights[i][j] = !lights[i][j];
            }
        }
    }

    private Boolean[][] initLights() {
        var lights = new Boolean[1000][1000];
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                lights[i][j] = false;
            }
        }
        return lights;
    }

    @Override
    public Long secondMethod() {
        var lights = new long[1000][1000];

        for (String line : input) {
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.find()) {
                var command = matcher.group("command");
                var start1 = Integer.parseInt(matcher.group("start1"));
                var start2 = Integer.parseInt(matcher.group("start2"));
                var end1 = Integer.parseInt(matcher.group("end1"));
                var end2 = Integer.parseInt(matcher.group("end2"));
                switch (command) {
                    case "turn on" -> turnOn2(lights, start1, start2, end1, end2);
                    case "turn off" -> turnOff2(lights, start1, start2, end1, end2);
                    case "toggle" -> toggle2(lights, start1, start2, end1, end2);
                    default -> {
                    }
                }
            }
        }

        return Arrays.stream(lights)
            .mapToLong(array -> Arrays.stream(array).sum())
            .sum();
    }

    private void turnOn2(long[][] lights, int start1, int start2, int end1, int end2) {
        for (int i = start1; i <= end1; i++) {
            for (int j = start2; j <= end2; j++) {
                lights[i][j] += 1;
            }
        }
    }

    private void turnOff2(long[][] lights, int start1, int start2, int end1, int end2) {
        for (int i = start1; i <= end1; i++) {
            for (int j = start2; j <= end2; j++) {
                if (lights[i][j] > 0) {
                    lights[i][j] -= 1;
                }
            }
        }
    }

    private void toggle2(long[][] lights, int start1, int start2, int end1, int end2) {
        for (int i = start1; i <= end1; i++) {
            for (int j = start2; j <= end2; j++) {
                lights[i][j] += 2;
            }
        }
    }
}
