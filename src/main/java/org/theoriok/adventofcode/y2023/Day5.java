package org.theoriok.adventofcode.y2023;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

public class Day5 implements Day<Long, Long> {
    private final List<Long> seeds;
    private final List<Mapper> seedToSoilMap;
    private final List<Mapper> soilToFertilizerMap;
    private final List<Mapper> fertilizerToWaterMap;
    private final List<Mapper> waterToLightMap;
    private final List<Mapper> lightToTemperatureMap;
    private final List<Mapper> temperatureToHumidityMap;
    private final List<Mapper> humidityToLocationMap;
    private final List<SeedRange> seedRanges;
    private static final Logger logger = LoggerFactory.getLogger(Day5.class);

    public Day5(List<String> input) {
        seeds = getSeeds(input);
        seedRanges = getSeedRanges(input);
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
    }

    private record SeedRange(long start, long size) {
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

    private List<Long> getSeeds(List<String> input) {
        return splitStringToLongs(input.getFirst().replace("seeds: ", ""));
    }

    private List<SeedRange> getSeedRanges(List<String> input) {
        List<SeedRange> seedRanges = new ArrayList<>();
        List<Long> seeds = getSeeds(input);
        for (int i = 0; i < seeds.size(); i += 2) {
            seedRanges.add(new SeedRange(seeds.get(i), seeds.get(i + 1)));
        }
        return seedRanges;
    }

    private List<Long> splitStringToLongs(String string) {
        return Arrays.stream(string.split(" "))
            .map(Long::parseLong)
            .toList();
    }

    @Override
    public Long firstMethod() {
        return seeds.stream()
            .mapToLong(this::findLocation)
            .min()
            .orElseThrow();
    }

    private long findLocation(Long seed) {
        logger.info("Finding location for {}", seed);
        long soil = findAndMap(seed, seedToSoilMap);
        long fertilizer = findAndMap(soil, soilToFertilizerMap);
        long water = findAndMap(fertilizer, fertilizerToWaterMap);
        long light = findAndMap(water, waterToLightMap);
        long temperature = findAndMap(light, lightToTemperatureMap);
        long humidity = findAndMap(temperature, temperatureToHumidityMap);
        return findAndMap(humidity, humidityToLocationMap);
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
        return seedRanges.parallelStream()
            .flatMap(seedRange -> LongStream.range(seedRange.start, seedRange.start + seedRange.size).boxed())
            .mapToLong(this::findLocation)
            .min()
            .orElseThrow();
    }
}
