package org.theoriok.adventofcode.y2015;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theoriok.adventofcode.Day;

import java.util.List;

public class Day8 implements Day<Integer, Integer> {

    private final List<String> input;

    public Day8(List<String> input) {
        this.input = input;
    }

    @Override
    public Integer firstMethod() {
        return numberOfStringCharacters() - numberOfInMemoryCharacters();
    }

    private int numberOfStringCharacters() {
        return input.stream()
            .mapToInt(String::length)
            .sum();
    }

    private int numberOfInMemoryCharacters() {
        return input.stream()
            .map(this::removeOutsideQuotes)
            .map(this::replaceQuotes)
            .map(this::replaceSpecial)
            .mapToInt(String::length)
            .sum();
    }

    private String removeOutsideQuotes(String input) {
        return input
            .replaceFirst("\"", "")
            .replaceAll("\"$", "");
    }

    private String replaceQuotes(String input) {
        return input.replace("\\\"","\"");
    }

    private String replaceSpecial(String input) {
        return input.replaceAll("\\\\x[0-9a-f][0-9a-f]",",");
    }

    @Override
    public Integer secondMethod() {
        return 0;
    }
}
