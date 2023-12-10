package org.theoriok.adventofcode.y2023;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Day1 implements Day<Long, Long> {

    public static final String[] NUMBERS = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    private final List<String> input;

    public Day1(List<String> input) {
        this.input = input;
    }

    @Override
    public Long firstMethod() {
        return input.stream()
                .mapToLong(this::doStuff)
                .sum();
    }

    private long doStuff(String inputLine) {
        var numbers = new ArrayList<>();
        for (char character : inputLine.toCharArray()) {
            if (isDigit(character)) {
                numbers.add(character);
            }
        }
        return Long.parseLong(numbers.getFirst().toString() + numbers.getLast().toString());
    }

    private boolean isDigit(char character) {
        return '1' <= character && character <= '9';
    }

    @Override
    public Long secondMethod() {
        return input.stream()
                .mapToLong(this::doStuffDifferently)
                .sum();
    }

    private long doStuffDifferently(String inputLine) {
        var numbers = new ArrayList<String>();
        var string = "";
        char[] charArray = inputLine.toCharArray();
        int checkpoint = 0;
        for (int i = 0; i < charArray.length; i++) {
            char character = charArray[i];
            if (isDigit(character)) {
                numbers.add(Character.toString(character));
                string = "";
                checkpoint = i;
            } else {
                string += character;
                if (isNumber(string)) {
                    string = stripToNumberString(string);
                    numbers.add(replaceWithDigit(string));
                    i = inputLine.indexOf(string, checkpoint) + 1;
                    string = "";
                    checkpoint = i;
                }
            }
        }
        return Long.parseLong(numbers.getFirst() + numbers.getLast());
    }

    private String stripToNumberString(String string) {
        if (ArrayUtils.contains(NUMBERS, string)) {
            return string;
        } else {
            return stripToNumberString(string.substring(1));
        }
    }

    private String replaceWithDigit(String string) {
        return switch (string.toLowerCase(Locale.ROOT)) {
            case "one" -> "1";
            case "two" -> "2";
            case "three" -> "3";
            case "four" -> "4";
            case "five" -> "5";
            case "six" -> "6";
            case "seven" -> "7";
            case "eight" -> "8";
            case "nine" -> "9";
            default -> "";
        };
    }

    private boolean isNumber(String string) {
        return StringUtils.containsAny(string, NUMBERS);
    }
}
