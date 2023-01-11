package org.theoriok.adventofcode.y2022;

import com.google.common.collect.Sets;
import org.theoriok.adventofcode.Day;

import java.util.List;

public class Day6 implements Day<Integer, Integer> {
    public static final int NUMBER_FOR_PART1 = 4;
    public static final int NUMBER_FOR_PART2 = 14;
    private final String inputString;

    public Day6(List<String> input) {
        inputString = input.get(0);
    }

    @Override
    public Integer firstMethod() {
        return checkForMarker(NUMBER_FOR_PART1);
    }

    @Override
    public Integer secondMethod() {
        return checkForMarker(NUMBER_FOR_PART2);
    }

    private Integer checkForMarker(int nulberOfCharactersForMarker) {
        for (int i = 0; i < inputString.length() - nulberOfCharactersForMarker; i++) {
            var substring = Sets.newHashSet(inputString.substring(i, i + nulberOfCharactersForMarker).split(""));
            if (substring.size() == nulberOfCharactersForMarker) {
                return i + nulberOfCharactersForMarker;
            }
        }

        return -1;
    }
}
