package org.theoriok.adventofcode.y2022;

import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.theoriok.adventofcode.Day;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

public class Day3 extends Day<Integer, Integer> {

    private final List<Rucksack> rucksacks;

    public Day3(List<String> input) {
        rucksacks = input.stream()
            .map(this::mapToRucksacks)
            .toList();
    }

    private Rucksack mapToRucksacks(String line) {
        var compartments = Splitter.fixedLength(line.length() / 2).splitToList(line).stream()
            .map(this::mapToCompartment)
            .toList();

        return new Rucksack(compartments.get(0), compartments.get(1));
    }

    private Compartment mapToCompartment(String compartment) {
        var items = Arrays.stream(compartment.split(""))
            .map(this::mapToItem)
            .toList();
        return new Compartment(items);
    }

    private Item mapToItem(String code) {
        return new Item(code);
    }

    @Override
    public Integer firstMethod() {
        return rucksacks.stream()
            .map(Rucksack::findDuplicate)
            .mapToInt(Item::getPriority)
            .sum();
    }

    private record Rucksack(Compartment firstCompartment, Compartment secondCompartment) {
        Item findDuplicate() {
            return Sets.intersection(new HashSet<>(firstCompartment.items), new HashSet<>(secondCompartment.items)).stream().findFirst().orElseThrow();
        }
    }

    private record Compartment(List<Item> items) {
    }

    private record Item(String code) {
        int getPriority() {
            return switch (code.charAt(0)) {
                case 'a' -> 1;
                case 'b' -> 2;
                case 'c' -> 3;
                case 'd' -> 4;
                case 'e' -> 5;
                case 'f' -> 6;
                case 'g' -> 7;
                case 'h' -> 8;
                case 'i' -> 9;
                case 'j' -> 10;
                case 'k' -> 11;
                case 'l' -> 12;
                case 'm' -> 13;
                case 'n' -> 14;
                case 'o' -> 15;
                case 'p' -> 16;
                case 'q' -> 17;
                case 'r' -> 18;
                case 's' -> 19;
                case 't' -> 20;
                case 'u' -> 21;
                case 'v' -> 22;
                case 'w' -> 23;
                case 'x' -> 24;
                case 'y' -> 25;
                case 'z' -> 26;
                case 'A' -> 27;
                case 'B' -> 28;
                case 'C' -> 29;
                case 'D' -> 30;
                case 'E' -> 31;
                case 'F' -> 32;
                case 'G' -> 33;
                case 'H' -> 34;
                case 'I' -> 35;
                case 'J' -> 36;
                case 'K' -> 37;
                case 'L' -> 38;
                case 'M' -> 39;
                case 'N' -> 40;
                case 'O' -> 41;
                case 'P' -> 42;
                case 'Q' -> 43;
                case 'R' -> 44;
                case 'S' -> 45;
                case 'T' -> 46;
                case 'U' -> 47;
                case 'V' -> 48;
                case 'W' -> 49;
                case 'X' -> 50;
                case 'Y' -> 51;
                case 'Z' -> 52;
                default -> 0;
            };
        }
    }
}
