package org.theoriok.adventofcode.y2021;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day3 extends Day {

    public Day3(List<String> input) {
        super(input);
    }

    @Override
    public int firstMethod() {
        var binaryLists = input.stream()
            .map(this::toBinaryList)
            .toList();
        var maxCountValuePerIndex = maxCountValuePerIndex(binaryLists);
        var minCountValuePerIndex = minCountValuePerIndex(binaryLists);

        return toNumber(maxCountValuePerIndex) * toNumber(minCountValuePerIndex);
    }

    private int toNumber(List<Integer> binarylist) {
        var number = 0;
        for (int i = 0; i < binarylist.size(); i++) {
            number += binarylist.get(binarylist.size() - (i+1)) * Math.pow(2, i);
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
        var size = binaryLists.get(0).size();
        for (int i = 0; i < size; i++) {
            var maxCountValue = valuesByCount(binaryLists, i)
                .entrySet().stream()
                .max(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElseThrow();
            maxCountValuePerIndex.add(maxCountValue);
        }
        return maxCountValuePerIndex;
    }

    private List<Integer> minCountValuePerIndex(List<List<Integer>> binaryLists) {
        List<Integer> minCountValuePerIndex = new ArrayList<>();
        var size = binaryLists.get(0).size();
        for (int i = 0; i < size; i++) {
            var minCountValue = valuesByCount(binaryLists, i)
                .entrySet().stream()
                .min(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElseThrow();
            minCountValuePerIndex.add(minCountValue);
        }
        return minCountValuePerIndex;
    }

    private Map<Integer, Integer> valuesByCount(List<List<Integer>> binaryLists, int index) {
        return binaryLists.stream()
            .collect(groupingBy(list -> list.get(index)))
            .entrySet().stream()
            .collect(toMap(entry -> entry.getValue().size(), Map.Entry::getKey));
    }

    @Override
    public int secondMethod() {
        var binaryLists = input.stream()
            .map(this::toBinaryList)
            .toList();
        var maxCountValuePerIndex = maxCountValuePerIndex(binaryLists);
        var minCountValuePerIndex = minCountValuePerIndex(binaryLists);

        List<Integer> filteredByMax = filterByList(binaryLists, maxCountValuePerIndex);
        List<Integer> filteredByMin = filterByList(binaryLists, minCountValuePerIndex);

        return toNumber(filteredByMax) * toNumber(filteredByMin);
    }

    private List<Integer> filterByList(List<List<Integer>> binaryLists, List<Integer> filterList) {
        var filteredLists = binaryLists;
        var startIndex = 0;
        while(filteredLists.size() != 1){
            for (int i = startIndex; i < filterList.size(); i++) {
                int index = i;
                filteredLists = filteredLists.stream()
                    .filter(list -> Objects.equals(list.get(index), filterList.get(index)))
                    .toList();
                if (filteredLists.isEmpty()) {
                    filteredLists = binaryLists;
                    startIndex++;
                    break;
                }
                if (filteredLists.size() == 1) {
                    break;
                }
            }
        }
        return filteredLists.get(0);
    }
}
