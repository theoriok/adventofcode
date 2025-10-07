package org.theoriok.adventofcode.y2024;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;
import static org.theoriok.adventofcode.util.Utils.splitToList;

import org.theoriok.adventofcode.Day;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class Day9 implements Day<Long, Long> {

    public static final int EMPTY_BLOCK = -1;
    private final List<Integer> digits;

    public Day9(List<String> input) {
        digits = splitToList(input.getFirst(), "", Integer::parseInt);
    }

    @Override
    public Long firstMethod() {
        List<String> code = new ArrayList<>();
        for (int i = 0; i < digits.size(); i++) {
            Integer digit = digits.get(i);
            var string = i % 2 == 0 ? "" + i / 2 : "";
            IntStream.range(0, digit)
                .mapToObj(_ -> string)
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
        List<Block> blocks = digits.stream()
            .map(digit -> {
                var counterValue = counter.getAndIncrement();
                return new Block(counterValue % 2 == 0 ? counterValue / 2 : EMPTY_BLOCK, digit);
            })
            .filter(block -> block.size > 0)
            .collect(toList());
        for (Block block : new ArrayList<>(blocks.reversed())) {
            if (block.id > EMPTY_BLOCK) {
                List<Block> list = blocks.subList(0, blocks.indexOf(block)).stream()
                    .filter(b -> b.id == EMPTY_BLOCK)
                    .filter(b -> b.size >= block.size)
                    .toList();
                if (!list.isEmpty()) {
                    Block emptyBlock = list.getFirst();
                    blocks.add(blocks.indexOf(block), new Block(EMPTY_BLOCK, block.size));
                    blocks.remove(block);
                    int emptyBlockIndex = blocks.indexOf(emptyBlock);
                    blocks.add(emptyBlockIndex, block);
                    if (emptyBlock.size > block.size) {
                        blocks.add(emptyBlockIndex + 1, new Block(EMPTY_BLOCK, emptyBlock.size - block.size));
                    }
                    blocks.remove(emptyBlock);
                }
            }
        }
        AtomicLong counter2 = new AtomicLong();
        long checksum = 0L;
        for (Block block : blocks) {
            for (int i = 0; i < block.size; i++) {
                checksum += Math.max(0, block.id) * counter2.getAndIncrement();
            }
        }
        return checksum;
    }

    record Block(int id, int size) {

    }
}
