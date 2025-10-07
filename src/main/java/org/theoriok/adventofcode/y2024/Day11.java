package org.theoriok.adventofcode.y2024;

import org.theoriok.adventofcode.Day;

import java.util.List;
import java.util.Map;

import static org.theoriok.adventofcode.util.Utils.splitToList;

public class Day11 implements Day<Long, Long> {

    private final List<Long> stones;
    private final Map<String, Long> memo = new java.util.concurrent.ConcurrentHashMap<>();

    public Day11(List<String> input) {
        stones = splitToList(input.getFirst(), " ", Long::parseLong);
    }

    @Override
    public Long firstMethod() {
        return stones.stream().mapToLong(stone -> count(stone, 25)).sum();
    }

    @Override
    public Long secondMethod() {
        return stones.stream().mapToLong(stone -> count(stone, 75)).sum();
    }

    private long count(long stone, int blinks) {
        if (blinks == 0) {
            return 1;
        }

        String key = stone + "," + blinks;
        Long cached = memo.get(key);
        if (cached != null) {
            return cached;
        }

        long result;
        if (stone == 0) {
            result = count(1, blinks - 1);
        } else {
            String stoneAsString = Long.toString(stone);
            if (stoneAsString.length() % 2 == 0) {
                int mid = stoneAsString.length() / 2;
                result = count(Long.parseLong(stoneAsString.substring(0, mid)), blinks - 1)
                        + count(Long.parseLong(stoneAsString.substring(mid)), blinks - 1);
            } else {
                result = count(stone * 2024, blinks - 1);
            }
        }

        memo.put(key, result);
        return result;
    }
}
