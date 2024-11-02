package org.theoriok.adventofcode.y2021;

import org.theoriok.adventofcode.Day;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.function.Function;

import static org.theoriok.adventofcode.util.Utils.splitToList;

public class Day10 implements Day<Integer, Long> {
    public static final List<String> START_CHARACTERS = List.of("(", "[", "{", "<");
    public static final List<String> END_CHARACTERS = List.of(")", "]", "}", ">");
    private final List<String> input;

    public Day10(List<String> input) {
        this.input = input;
    }

    @Override
    public Integer firstMethod() {
        return input.stream()
            .mapToInt(this::errorForLine)
            .sum();
    }

    private int errorForLine(String line) {
        List<String> characters = splitToList(line, "", Function.identity());
        Deque<String> stack = new ArrayDeque<>();
        for (String character : characters) {
            if (START_CHARACTERS.contains(character)) {
                stack.push(character);
            } else {
                String characterFromStack = stack.pop();
                String oppositeCharacter = END_CHARACTERS.get(START_CHARACTERS.indexOf(characterFromStack));
                if (!character.equals(oppositeCharacter)) {
                    return errorFor(character);
                }
            }
        }
        return 0;
    }

    private int errorFor(String character) {
        return switch (character) {
            case ")" -> 3;
            case "]" -> 57;
            case "}" -> 1197;
            case ">" -> 25137;
            default -> throw new IllegalStateException("Unexpected value: " + character);
        };
    }

    @Override
    public Long secondMethod() {
        List<Long> completionScores = input.stream()
            .filter(line -> errorForLine(line) == 0)
            .map(this::completeAndScore)
            .sorted()
            .toList();
        return completionScores.get(completionScores.size() / 2);
    }

    private long completeAndScore(String line) {
        List<String> characters = splitToList(line, "", Function.identity());
        Deque<String> stack = populateStack(characters);
        List<String> completers = completeLine(stack);
        return calculatescore(completers);
    }

    private long calculatescore(List<String> completers) {
        long score = 0;
        for (String completer : completers) {
            score *= 5;
            score += completionScore(completer);
        }
        return score;
    }

    private static List<String> completeLine(Deque<String> stack) {
        List<String> completers = new ArrayList<>();
        while (!stack.isEmpty()) {
            String startCharacter = stack.pop();
            completers.add(END_CHARACTERS.get(START_CHARACTERS.indexOf(startCharacter)));
        }
        return completers;
    }

    private Deque<String> populateStack(List<String> characters) {
        Deque<String> stack = new ArrayDeque<>();
        for (String character : characters) {
            if (START_CHARACTERS.contains(character)) {
                stack.push(character);
            } else {
                stack.pop();
            }
        }
        return stack;
    }

    private int completionScore(String character) {
        return switch (character) {
            case ")" -> 1;
            case "]" -> 2;
            case "}" -> 3;
            case ">" -> 4;
            default -> throw new IllegalStateException("Unexpected value: " + character);
        };
    }
}
