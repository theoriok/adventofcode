package org.theoriok.adventofcode.y2015;

import static java.util.stream.Collectors.groupingBy;

import com.google.common.collect.Sets;
import org.theoriok.adventofcode.Day;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

public class Day17 implements Day<Long, Integer> {

    private static final int EGGNOG_VOLUME = 150;
    private final List<Jar> jars;

    public Day17(List<String> input) {
        jars = input.stream()
            .map(Jar::from)
            .toList();
    }

    @Override
    public Long firstMethod() {
        return firstMethod(EGGNOG_VOLUME);
    }

    public Long firstMethod(int volume) {
        return allSubsets(jars).stream()
            .filter(subset -> fitsVolumeExactly(subset, volume))
            .count();
    }

    private boolean fitsVolumeExactly(Collection<Jar> subset, int volume) {
        return subset.stream()
            .mapToInt(Jar::volume)
            .sum() == volume;
    }

    private Collection<? extends Collection<Jar>> allSubsets(List<Jar> jars) {
        return IntStream.range(1, jars.size())
            .boxed()
            .flatMap(size -> Sets.combinations(new HashSet<>(jars), size).stream())
            .toList();
    }

    @Override
    public Integer secondMethod() {
        return secondMethod(EGGNOG_VOLUME);
    }

    public int secondMethod(int volume) {
        return allSubsets(jars).stream()
            .filter(subset -> fitsVolumeExactly(subset, volume))
            .collect(groupingBy(Collection::size))
            .entrySet().stream()
            .min(Map.Entry.comparingByKey())
            .orElseThrow()
            .getValue()
            .size();
    }

    record Jar(int volume, UUID id) {
        public static Jar from(String line) {
            return new Jar(Integer.parseInt(line), UUID.randomUUID());
        }
    }
}
