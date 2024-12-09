package org.theoriok.adventofcode.y2024;

import org.theoriok.adventofcode.Day;

import java.util.List;

import static org.theoriok.adventofcode.util.Utils.splitToList;

public class Day9 implements Day<Integer, Integer> {

    public Day9(List<String> input) {
        List<Integer> digits = splitToList(input.getFirst(), "", Integer::parseInt);
        for (int i = 0; i < digits.size(); i++) {
            Integer digit = digits.get(i);

        }

    }

    @Override
    public Integer firstMethod() {
        return 0;
    }

    @Override
    public Integer secondMethod() {
        return 0;
    }
}
