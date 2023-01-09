package org.theoriok.adventofcode.y2022;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.theoriok.adventofcode.Day;

import java.util.HashSet;
import java.util.List;

public class Day6 implements Day<Integer, Integer> {
    private final String inputString;

    public Day6(List<String> input) {
        inputString = input.get(0);
    }

    @Override
    public Integer firstMethod() {
        for (int i = 0; i < inputString.length() - 4; i++) {
            var substring = Sets.newHashSet(inputString.substring(i, i + 4).split(""));
            if (substring.size() == 4) {
                return i + 4;
            }
        }

        return -1;
    }
}
