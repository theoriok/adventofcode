package org.theoriok.adventofcode.y2024;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toCollection;
import static org.theoriok.adventofcode.util.Utils.splitToList;

import org.theoriok.adventofcode.Day;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Day9 implements Day<Long, Long> {

    private final List<Integer> digits;

    public Day9(List<String> input) {
        digits = splitToList(input.getFirst(), "", Integer::parseInt);
    }

    @Override
    public Long firstMethod() {
        List<String> code = new ArrayList<>();
        for (int i = 0; i < digits.size(); i++) {
            Integer digit = digits.get(i);
            var s = i % 2 == 0 ? "" + i / 2 : "";
            IntStream.range(0, digit)
                .mapToObj(_ -> s)
                .forEach(code::add);
        }
        Deque<String> codeDeque = new ArrayDeque<>(code.stream().filter(not(String::isEmpty)).toList());
        List<Integer> compactedCode = new ArrayList<>();
        int iterator = 0;
        while (!codeDeque.isEmpty()) {
            var item = code.get(iterator);
            if (item.isEmpty()) {
                compactedCode.add(Integer.parseInt(codeDeque.removeLast()));
            } else {
                compactedCode.add(Integer.parseInt(codeDeque.removeFirst()));
            }
            iterator++;
        }
        long checksum = 0L;
        for (int i = 0; i < compactedCode.size(); i++) {
            var digit = compactedCode.get(i);
            checksum += digit * i;
        }
        return checksum;
    }

    @Override
    public Long secondMethod() {
        AtomicInteger counter = new AtomicInteger(0);
        Deque<Block> blocks = digits.stream()
            .map(digit -> {
                var counterValue = counter.getAndIncrement();
                return new Block(counterValue % 2 == 0 ? counterValue / 2 : -1, digit);
            })
            .filter(block -> block.size > 0)
            .collect(toCollection(ArrayDeque::new));
        return 0L;
    }

    record Block(int id, int size) {
    }
}
