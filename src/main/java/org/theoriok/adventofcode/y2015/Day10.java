package org.theoriok.adventofcode.y2015;

import static org.theoriok.adventofcode.util.Utils.splitToList;

import org.theoriok.adventofcode.Day;

import java.util.List;

public class Day10 implements Day<Integer, Integer> {

    private final String line;

    public Day10(List<String> input) {
        line = input.getFirst();
    }

    @Override
    public Integer firstMethod() {
        return iterateSomething(40);
    }

    Integer iterateSomething(int iterations) {
        String input = line;
        for (int i = 0; i < iterations; i++) {
            input = doSomething(input);
        }
        return input.length();
    }

    private String doSomething(String input) {
        StringBuilder output = new StringBuilder();
        List<Integer> numbers = splitToList(input, "", Integer::parseInt);
        if (numbers.isEmpty()) {
            return output.toString();
        }
        int currentNumber = numbers.getFirst();
        int counter = 1;
        for (int i = 1; i < numbers.size(); i++) {
            int newNumber = numbers.get(i);
            if (newNumber == currentNumber) {
                counter++;
            } else {
                output.append(counter).append(currentNumber);
                currentNumber = newNumber;
                counter = 1;
            }
        }
        output.append(counter).append(currentNumber);
        return output.toString();
    }

    @Override
    public Integer secondMethod() {
        return iterateSomething(50);
    }
}
