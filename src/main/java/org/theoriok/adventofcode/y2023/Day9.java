package org.theoriok.adventofcode.y2023;

import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import static org.theoriok.adventofcode.util.Utils.splitToList;

public class Day9 implements Day<Long, Long> {

    private final List<String> input;

    public Day9(List<String> input) {
        this.input = input;
    }

    @Override
    public Long firstMethod() {
        return input.stream()
            .mapToLong(line -> doStuff(line, List::getLast, List::addLast, this::plus))
            .sum();
    }

    @Override
    public Long secondMethod() {
        return input.stream()
            .mapToLong(line -> doStuff(line, List::getFirst, List::addFirst, this::minus))
            .sum();
    }

    private long doStuff(String line, Function<List<Long>, Long> getter, BiConsumer<List<Long>, Long> setter, BinaryOperator<Long> calculator) {
        List<Long> numbers = new ArrayList<>(splitStringToLongs(line));
        doStuffWithNumbers(numbers, getter, setter, calculator);
        return getter.apply(numbers);
    }

    private static List<Long> splitStringToLongs(String line) {
        return splitToList(line, " ", Long::parseLong);
    }

    private void doStuffWithNumbers(List<Long> numbers, Function<List<Long>, Long> getter, BiConsumer<List<Long>, Long> setter,
        BinaryOperator<Long> calculator) {
        List<Long> otherNumbers = new ArrayList<>();
        for (int i = 1; i < numbers.size(); i++) {
            otherNumbers.add(numbers.get(i) - numbers.get(i - 1));
        }
        if (!new HashSet<>(otherNumbers).stream().allMatch(l -> l == 0)) {
            doStuffWithNumbers(otherNumbers, getter, setter, calculator);
        }
        Long firstNumber = getter.apply(numbers);
        Long secondNumber = getter.apply(otherNumbers);
        long newValue = calculator.apply(firstNumber, secondNumber);
        setter.accept(numbers, newValue);
    }

    private long plus(Long firstNumber, Long secondNumber) {
        return firstNumber + secondNumber;
    }

    private long minus(Long firstNumber, Long secondNumber) {
        return firstNumber - secondNumber;
    }
}
