package org.theoriok.adventofcode.y2015;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theoriok.adventofcode.Day;

import java.util.List;
import java.util.function.BiPredicate;

public class Day20 implements Day<Integer, Integer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day20.class);

    private final int number;

    public Day20(List<String> input) {
        number = Integer.parseInt(input.getFirst());
    }

    @Override
    public Integer firstMethod() {
        return extracted(10, (houseNumber, elfNumber) -> houseNumber % elfNumber == 0);
    }

    @Override
    public Integer secondMethod() {
        return extracted(11, (houseNumber, elfNumber) -> houseNumber % elfNumber == 0 && houseNumber / elfNumber <= 50);
    }

    private int extracted(int presentsToDeliver, BiPredicate<Integer, Integer> shouldDeliver) {
        int houseNumber = 0;
        var presents = new int[number];
        presents[0] = 0;
        while (presents[houseNumber] < number) {
            houseNumber++;
            for (int elf = 1; elf <= houseNumber; elf++) {
                if (shouldDeliver.test(houseNumber, elf)) {
                    presents[houseNumber] += elf * presentsToDeliver;
                }
            }
            if (houseNumber % 1000 == 0) {
                LOGGER.debug("{}", houseNumber);
            }
        }
        LOGGER.info("done: {}", houseNumber);
        return houseNumber;
    }
}
