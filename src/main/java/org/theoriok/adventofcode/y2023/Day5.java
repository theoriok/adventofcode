package org.theoriok.adventofcode.y2023;

import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Day5 implements Day<Long, Long> {

    private final List<Long> seeds;
    private final List<Mapper> seedToSoilMap;
    private final List<Mapper> soilToFertilizerMap;
    private final List<Mapper> fertilizerToWaterMap;
    private final List<Mapper> waterToLightMap;
    private final List<Mapper> lightToTemperatureMap;
    private final List<Mapper> temperatureToHumidityMap;
    private final List<Mapper> humidityToLocationMap;

    public Day5(List<String> input) {
        seeds = getSeeds(input);
        seedToSoilMap = fillMap(input, "seed-to-soil map:");
        soilToFertilizerMap = fillMap(input, "soil-to-fertilizer map:");
        fertilizerToWaterMap = fillMap(input, "fertilizer-to-water map:");
        waterToLightMap = fillMap(input, "water-to-light map:");
        lightToTemperatureMap = fillMap(input, "light-to-temperature map:");
        temperatureToHumidityMap = fillMap(input, "temperature-to-humidity map:");
        humidityToLocationMap = fillMap(input, "humidity-to-location map:");
    }

    private record Mapper(long source, long destination, long size) {
        public boolean matches(Long aLong) {
            return source <= aLong && aLong < source + size;
        }

        public Long map(Long aLong) {
            return aLong - source + destination;
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

    private List<Long> getSeeds(List<String> input) {
        return splitStringToLongs(input.getFirst().replace("seeds: ", ""));
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
        return Optional.ofNullable(findAndMap(seed, seedToSoilMap))
            .map(soil -> findAndMap(soil, soilToFertilizerMap))
            .map(fertilizer -> findAndMap(fertilizer, fertilizerToWaterMap))
            .map(water -> findAndMap(water, waterToLightMap))
            .map(light -> findAndMap(light, lightToTemperatureMap))
            .map(temperature -> findAndMap(temperature, temperatureToHumidityMap))
            .map(humidity -> findAndMap(humidity, humidityToLocationMap))
            .orElseThrow();
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
        return 0L;
    }
}
