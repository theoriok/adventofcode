package org.theoriok.adventofcode.y2023;

import com.google.common.collect.Range;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.theoriok.adventofcode.util.Utils.splitToList;

public class Day5 implements Day<Long, Long> {
    private final List<Long> seeds;
    private final List<Mapper> seedToSoilMap;
    private final List<Mapper> soilToFertilizerMap;
    private final List<Mapper> fertilizerToWaterMap;
    private final List<Mapper> waterToLightMap;
    private final List<Mapper> lightToTemperatureMap;
    private final List<Mapper> temperatureToHumidityMap;
    private final List<Mapper> humidityToLocationMap;
    private final List<MyRange> seedRanges = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(Day5.class);

    public Day5(List<String> input) {
        seeds = splitStringToLongs(input.getFirst().replace("seeds: ", ""));
        mapSeedRanges();
        seedToSoilMap = fillMap(input, "seed-to-soil map:");
        soilToFertilizerMap = fillMap(input, "soil-to-fertilizer map:");
        fertilizerToWaterMap = fillMap(input, "fertilizer-to-water map:");
        waterToLightMap = fillMap(input, "water-to-light map:");
        lightToTemperatureMap = fillMap(input, "light-to-temperature map:");
        temperatureToHumidityMap = fillMap(input, "temperature-to-humidity map:");
        humidityToLocationMap = fillMap(input, "humidity-to-location map:");
    }

    private record Mapper(long source, long destination, long size) {
        public boolean matches(long aLong) {
            return source <= aLong && aLong < source + size;
        }

        public long map(long aLong) {
            return destination + (aLong - source);
        }

        public Range<Long> toSourceRange() {
            return Range.closedOpen(source, source + size);
        }

        private Range<Long> toDestinationRange() {
            return Range.closedOpen(destination, destination + size);
        }

        public Range<Long> mapRange(Range<Long> rangeToMap) {
            return Range.closedOpen(map(rangeToMap.lowerEndpoint()), map(rangeToMap.upperEndpoint()));
        }

        @Override
        public String toString() {
            return "Mapper{%s -> %s}".formatted(toSourceRange(), toDestinationRange());
        }
    }

    private record MyRange(long start, long size) {

        private Range<Long> toRange() {
            return Range.closedOpen(start, start + size);
        }

        private static ArrayList<Range<Long>> mapRange(List<Mapper> mappers, Range<Long> range) {
            var map = mappers.stream()
                .filter(mapper -> mapper.toSourceRange().isConnected(range))
                .map(mapper -> {
                    Range<Long> intersection = mapper.toSourceRange().intersection(range);
                    return Pair.of(intersection, mapper.mapRange(intersection));
                })
                .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
            Range<Long> nonMapped = range;
            for (Range<Long> mappedRange : map.keySet()) {
                nonMapped = complement(nonMapped, mappedRange);
            }
            if (!nonMapped.isEmpty()) {
                map.put(nonMapped, nonMapped);
            }
            return new ArrayList<>(map.values());
        }

        private static Range<Long> complement(Range<Long> firstRange, Range<Long> secondRange) {
            if (firstRange.isConnected(secondRange)) {
                Range<Long> intersection = firstRange.intersection(secondRange);
                if (firstRange.lowerEndpoint() < intersection.lowerEndpoint()) {
                    return Range.closedOpen(firstRange.lowerEndpoint(), intersection.lowerEndpoint());
                } else {
                    return Range.closedOpen(intersection.upperEndpoint(), firstRange.upperEndpoint());
                }
            }
            return firstRange;
        }

        @Override
        public String toString() {
            return "Range{%s}".formatted(toRange());
        }
    }

    private List<Mapper> fillMap(List<String> input, String type) {
        var mappers = new ArrayList<Mapper>();
        int start = input.indexOf(type);
        int end = input.subList(start, input.size()).indexOf("") + start;
        for (int i = start + 1; i < end; i++) {
            String[] split = input.get(i).split(" ");
            mappers.add(new Mapper(Long.parseLong(split[1]), Long.parseLong(split[0]), Long.parseLong(split[2])));
        }
        return mappers;
    }

    private void mapSeedRanges() {
        for (int i = 0; i < seeds.size(); i += 2) {
            seedRanges.add(new MyRange(seeds.get(i), seeds.get(i + 1)));
        }
    }

    private List<Long> splitStringToLongs(String string) {
        return splitToList(string, " ", Long::parseLong);
    }

    @Override
    public Long firstMethod() {
        return seeds.stream()
            .mapToLong(this::findLocation)
            .min()
            .orElseThrow();
    }

    private long findLocation(Long seed) {
        long soil = findAndMap(seed, seedToSoilMap);
        long fertilizer = findAndMap(soil, soilToFertilizerMap);
        long water = findAndMap(fertilizer, fertilizerToWaterMap);
        long light = findAndMap(water, waterToLightMap);
        long temperature = findAndMap(light, lightToTemperatureMap);
        long humidity = findAndMap(temperature, temperatureToHumidityMap);
        long location = findAndMap(humidity, humidityToLocationMap);
        LOGGER.info("Finding location for seed {} -> found location {}", seed, location);
        return location;
    }

    private Long findAndMap(Long aLong, List<Mapper> mappers) {
        return mappers.stream()
            .filter(mapper -> mapper.matches(aLong))
            .map(mapper -> mapper.map(aLong))
            .findFirst()
            .orElse(aLong);
    }

    @Override
    public Long secondMethod() {
        return seedRanges.stream()
            .flatMap(myRange -> MyRange.mapRange(seedToSoilMap, myRange.toRange()).stream())
            .flatMap(range -> MyRange.mapRange(soilToFertilizerMap, range).stream())
            .flatMap(range -> MyRange.mapRange(fertilizerToWaterMap, range).stream())
            .flatMap(range -> MyRange.mapRange(waterToLightMap, range).stream())
            .flatMap(range -> MyRange.mapRange(lightToTemperatureMap, range).stream())
            .flatMap(range -> MyRange.mapRange(temperatureToHumidityMap, range).stream())
            .flatMap(range -> MyRange.mapRange(humidityToLocationMap, range).stream())
            .min(Comparator.comparingLong(Range::lowerEndpoint))
            .map(Range::lowerEndpoint)
            .orElse(0L);
    }
}
