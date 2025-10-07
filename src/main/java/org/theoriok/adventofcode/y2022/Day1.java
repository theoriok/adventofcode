package org.theoriok.adventofcode.y2022;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day1 implements Day<Integer, Integer> {

    private final List<Elf> elfs = new ArrayList<>();

    public Day1(List<String> input) {
        var chunk = new ArrayList<Integer>();
        for (String line : input) {
            if (isNotBlank(line)) {
                chunk.add(Integer.parseInt(line));
            } else {
                elfs.add(new Elf(chunk));
                chunk = new ArrayList<>();
            }
        }
        elfs.add(new Elf(chunk));
    }

    @Override
    public Integer firstMethod() {
        return elfs.stream()
            .mapToInt(Elf::getTotal)
            .max()
            .orElse(0);
    }

    @Override
    public Integer secondMethod() {
        return elfs.stream()
            .sorted(Comparator.comparing(Elf::getTotal).reversed())
            .mapToInt(Elf::getTotal)
            .limit(3)
            .sum();
    }

    private record Elf(
        List<Integer> calories
    ) {
        int getTotal() {
            return calories.stream().mapToInt(Integer::intValue).sum();
        }
    }
}
