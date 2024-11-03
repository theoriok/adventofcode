package org.theoriok.adventofcode.y2021;

import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public class Day3 implements Day<Integer, Integer> {

    private final List<List<Integer>> binaryLists;

    public Day3(List<String> input) {
        binaryLists = input.stream()
            .map(this::toBinaryList)
            .toList();
    }

    @Override
    public Integer firstMethod() {
        var maxCountValuePerIndex = maxCountValuePerIndex(binaryLists);
        var minCountValuePerIndex = minCountValuePerIndex(binaryLists);

        return toNumber(maxCountValuePerIndex) * toNumber(minCountValuePerIndex);
    }

    private int toNumber(List<Integer> binarylist) {
        var number = 0;
        for (int i = 0; i < binarylist.size(); i++) {
            number += (int) (binarylist.get(binarylist.size() - (i + 1)) * Math.pow(2, i));
        }
        return number;
    }

    private List<Integer> toBinaryList(String str) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            list.add(Integer.parseInt(str.substring(i, i + 1), 2));
        }
        return list;
    }

    private List<Integer> maxCountValuePerIndex(List<List<Integer>> binaryLists) {
        List<Integer> maxCountValuePerIndex = new ArrayList<>();
        var size = binaryLists.getFirst().size();
        for (int i = 0; i < size; i++) {
            maxCountValuePerIndex.add(getMaxCountValue(binaryLists, i));
        }
        return maxCountValuePerIndex;
    }

    private Integer getMaxCountValue(List<List<Integer>> binaryLists, int index) {
        return valuesByCount(binaryLists, index, Math::max)
            .entrySet().stream()
            .max(Map.Entry.comparingByKey())
            .map(Map.Entry::getValue)
            .orElseThrow();
    }

    private List<Integer> minCountValuePerIndex(List<List<Integer>> binaryLists) {
        List<Integer> minCountValuePerIndex = new ArrayList<>();
        var size = binaryLists.getFirst().size();
        for (int i = 0; i < size; i++) {
            minCountValuePerIndex.add(getMinCountValue(binaryLists, i));
        }
        return minCountValuePerIndex;
    }

    private Integer getMinCountValue(List<List<Integer>> binaryLists, int index) {
        return valuesByCount(binaryLists, index, Math::min)
            .entrySet().stream()
            .min(Map.Entry.comparingByKey())
            .map(Map.Entry::getValue)
            .orElseThrow();
    }

    private Map<Integer, Integer> valuesByCount(List<List<Integer>> binaryLists, int index, BinaryOperator<Integer> mergeFunction) {
        return binaryLists.stream()
            .collect(groupingBy(list -> list.get(index)))
            .entrySet().stream()
            .collect(toMap(entry -> entry.getValue().size(), Map.Entry::getKey, mergeFunction));
    }

    @Override
    public Integer secondMethod() {
        List<Integer> filteredByMax = filterByList(binaryLists, this::getMaxCountValue);
        List<Integer> filteredByMin = filterByList(binaryLists, this::getMinCountValue);

        return toNumber(filteredByMax) * toNumber(filteredByMin);
    }

    private List<Integer> filterByList(List<List<Integer>> binaryLists, BiFunction<List<List<Integer>>, Integer, Integer> countFunction) {
        var filteredLists = binaryLists;
        var startIndex = 0;
        while (filteredLists.size() != 1) {
            for (int i = startIndex; i < filteredLists.getFirst().size(); i++) {
                filteredLists = filterLists(filteredLists, i, countFunction);
                if (filteredLists.isEmpty()) {
                    filteredLists = binaryLists;
                    startIndex++;
                }
                if (filteredLists.size() <= 1) {
                    break;
                }
            }
        }
        return filteredLists.getFirst();
    }

    private List<List<Integer>> filterLists(List<List<Integer>> filteredLists, int index, BiFunction<List<List<Integer>>, Integer, Integer> countFunction) {
        var count = countFunction.apply(filteredLists, index);
        filteredLists = filteredLists.stream()
            .filter(list -> Objects.equals(list.get(index), count))
            .toList();
        return filteredLists;
    }
}
