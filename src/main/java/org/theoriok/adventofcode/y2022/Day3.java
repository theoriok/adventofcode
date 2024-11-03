package org.theoriok.adventofcode.y2022;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.theoriok.adventofcode.Day;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.theoriok.adventofcode.util.Utils.splitToList;

public class Day3 implements Day<Integer, Integer> {

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

        return new Rucksack(compartments.getFirst(), compartments.get(1));
    }

    private Compartment mapToCompartment(String compartment) {
        var items = splitToItems(compartment);
        return new Compartment(items);
    }

    private List<Item> splitToItems(String compartment) {
        return splitToList(compartment, "", this::mapToItem);
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

    @Override
    public Integer secondMethod() {
        return Lists.partition(rucksacks, 3).stream()
            .map(this::findCommon)
            .mapToInt(Item::getPriority)
            .sum();
    }

    private Item findCommon(List<Rucksack> rucksacks) {
        List<Set<Item>> items = rucksacks.stream()
            .map(Rucksack::allItems)
            .toList();
        Sets.SetView<Item> intersectionFirstTwoLists = Sets.intersection(new HashSet<>(items.getFirst()), new HashSet<>(items.get(1)));
        return Sets.intersection(intersectionFirstTwoLists, new HashSet<>(items.get(2))).stream().findFirst().orElseThrow();
    }

    private record Rucksack(Compartment firstCompartment, Compartment secondCompartment) {
        Item findDuplicate() {
            return Sets.intersection(new HashSet<>(firstCompartment.items), new HashSet<>(secondCompartment.items)).stream().findFirst().orElseThrow();
        }

        Set<Item> allItems() {
            return Sets.union(new HashSet<>(firstCompartment.items), new HashSet<>(secondCompartment.items));
        }
    }

    private record Compartment(List<Item> items) {
    }

    private record Item(String code) {
        int getPriority() {
            char c = code.charAt(0);
            return 1 + Character.toLowerCase(c) - 'a' + (Character.isUpperCase(c) ? 26 : 0);
        }
    }
}
