package org.theoriok.adventofcode.y2024;

import static org.theoriok.adventofcode.util.Utils.splitToList;

import org.theoriok.adventofcode.Day;

import java.util.List;
import java.util.Map;

public class Day11 implements Day<Long, Long> {
    private final List<Stone> stones;
    private final Map<String, Long> memo = new java.util.concurrent.ConcurrentHashMap<>();

    public Day11(List<String> input) {
        stones = splitToList(input.getFirst(), " ", Long::parseLong).stream()
            .map(Stone::new)
            .toList();
    }

    @Override
    public Long firstMethod() {
        return stones.stream()
            .mapToLong(stone -> count(stone.value, 25))
            .sum();
    }

    @Override
    public Long secondMethod() {
        return stones.stream()
            .mapToLong(stone -> count(stone.value, 75))
            .sum();
    }

    private long count(long value, int blinks) {
        if (blinks == 0) {
            return 1;
        }

        String key = value + "," + blinks;
        Long cached = memo.get(key);
        if (cached != null) {
            return cached;
        }

        long result = new Stone(value).transform().stream()
            .mapToLong(stone -> count(stone.value, blinks - 1))
            .sum();

        memo.put(key, result);
        return result;
    }

    record Stone(long value) {
        List<Stone> transform() {
            if (value == 0) {
                return List.of(new Stone(1));
            }

            String str = String.valueOf(value);
            if (str.length() % 2 == 0) {
                int mid = str.length() / 2;
                return List.of(
                    new Stone(Long.parseLong(str.substring(0, mid))),
                    new Stone(Long.parseLong(str.substring(mid)))
                );
            }
            return List.of(new Stone(value * 2024));
        }
    }
}
