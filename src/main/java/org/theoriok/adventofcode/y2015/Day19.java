package org.theoriok.adventofcode.y2015;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theoriok.adventofcode.Day;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day19 implements Day<Integer, Integer> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day19.class);
    private final String startingMolecule;
    private final List<Replacement> replacements;

    public Day19(List<String> input) {
        startingMolecule = input.getLast();
        replacements = input.stream()
            .filter(line -> !line.equals(startingMolecule))
            .filter(Predicate.not(String::isEmpty))
            .map(Replacement::from)
            .collect(Collectors.toList());
    }

    @Override
    public Integer firstMethod() {
        Set<String> replacedMolecules = new HashSet<>();
        for (int i = 0; i < startingMolecule.length(); i++) {
            for (Replacement replacement : replacements) {
                if (startingMolecule.length() >= i + replacement.from.length()
                    && startingMolecule.startsWith(replacement.from, i)) {
                    String replacedMolecule = startingMolecule.substring(0, i)
                        + replacement.to
                        + startingMolecule.substring(i + replacement.from.length());
                    replacedMolecules.add(replacedMolecule);
                }
            }
        }
        return replacedMolecules.size();
    }

    @Override
    public Integer secondMethod() {
        int counter = 0;
        var reduce = startingMolecule;
        while (!"e".equals(reduce)) {
            var newMolecule = reduce;
            for (Replacement replacement : replacements) {
                if (reduce.contains(replacement.to)) {
                    newMolecule = newMolecule.replaceFirst(replacement.to, replacement.from);
                    counter++;
                    break;
                }
            }
            if (newMolecule.equals(reduce)) {
                LOGGER.info("starting over");
                reduce = startingMolecule;
                counter = 0;
                Collections.shuffle(replacements);
            } else {
                reduce = newMolecule;
            }
        }
        LOGGER.info("found {}", counter);
        return counter;
    }

    record Replacement(String from, String to) {
        static Replacement from(String line) {
            String[] split = line.split(" => ");
            return new Replacement(split[0], split[1]);
        }
    }
}
