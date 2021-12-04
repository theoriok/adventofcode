package org.theoriok.adventofcode.y2021;

import org.theoriok.adventofcode.Day;

import java.util.List;

public class Day1 extends Day {

    public Day1(List<String> input) {
        super(input);
    }

    @Override
    public int firstMethod() {
        var increases = 0;
        for (int i = 1; i < input.size(); i++) {
            var prev = Integer.parseInt(input.get(i - 1));
            var curr = Integer.parseInt(input.get(i));
            if (curr > prev) {
                increases++;
            }
        }
        return increases;
    }

    @Override
    public int secondMethod() {
        var increases = 0;
        for (int i = 3; i < input.size(); i++) {
            var prev3 = Integer.parseInt(input.get(i - 3));
            var prev2 = Integer.parseInt(input.get(i - 2));
            var prev = Integer.parseInt(input.get(i - 1));
            var curr = Integer.parseInt(input.get(i));
            if (curr + prev + prev2 > prev + prev2 + prev3) {
                increases++;
            }
        }
        return increases;
    }
}