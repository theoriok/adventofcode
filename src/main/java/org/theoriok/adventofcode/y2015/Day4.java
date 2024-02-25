package org.theoriok.adventofcode.y2015;

import org.springframework.util.DigestUtils;
import org.theoriok.adventofcode.Day;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class Day4 implements Day<Long, Long> {

    private final String word;

    public Day4(List<String> input) {
        word = input.getFirst();
    }

    @Override
    public Long firstMethod() {
        long number = 1;
        while (!hashStartsWithFiveZeroes(number)) {
            number++;
        }
        return number;
    }

    private boolean hashStartsWithFiveZeroes(long number) {
        return hashStartsWithZeroes(word + number, "00000");
    }

    private boolean hashStartsWithSixZeroes(long number) {
        return hashStartsWithZeroes(word + number, "000000");
    }

    private boolean hashStartsWithZeroes(String word, String number) {
        return DigestUtils.md5DigestAsHex(word.getBytes(StandardCharsets.UTF_8)).startsWith(number);
    }

    @Override
    public Long secondMethod() {
        long number = 1;
        while (!hashStartsWithSixZeroes(number)) {
            number++;
        }
        return number;
    }
}
