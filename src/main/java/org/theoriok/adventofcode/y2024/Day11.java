package org.theoriok.adventofcode.y2024;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.theoriok.adventofcode.util.Utils.splitToList;

public class Day11 implements Day<Integer, Integer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day11.class);
    private final List<Stone> stones;

    public Day11(List<String> input) {
        stones = splitToList(input.getFirst(), " ", Stone::from);
    }

    @Override
    public Integer firstMethod() {
        return dewitt(25);
    }

    @Override
    public Integer secondMethod() {
        return dewitt(75);
    }

    private int dewitt(int times) {
        List<Stone> newStones = new ArrayList<>(stones);
        for (int i = 0; i < times; i++) {
            newStones = newStones.stream()
                .flatMap(Stone::blink)
                .toList();
            LOGGER.info("Loop {}: {} stones", i, newStones.size());
        }
        return newStones.size();
    }

    record Stone(long number) {
        static Map<Long, List<Stone>> map = new HashMap<>();

        public static Stone from(String line) {
            return new Stone(Long.parseLong(line));
        }

        public Stream<Stone> blink() {
            return map.computeIfAbsent(number, _ -> {
                if (number == 0) {
                    return List.of(new Stone(1));
                }
                String numberAsString = Long.toString(number);
                if (numberAsString.length() % 2 == 0) {
                    return List.of(
                        new Stone(Long.parseLong(numberAsString.substring(0, numberAsString.length() / 2))),
                        new Stone(Long.parseLong(numberAsString.substring(numberAsString.length() / 2)))
                    );
                }
                return List.of(new Stone(number * 2024));
            }).stream();
        }
    }
}
