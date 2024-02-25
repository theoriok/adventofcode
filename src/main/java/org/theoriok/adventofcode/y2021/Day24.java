package org.theoriok.adventofcode.y2021;

import com.google.common.collect.Iterators;
import org.apache.commons.lang3.tuple.Pair;
import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Day24 implements Day<Long, Long> {

    private final List<Parameters> parameters;

    public Day24(List<String> input) {
        parameters = new ArrayList<>();
        Iterators.partition(input.listIterator(), 18).forEachRemaining(
            instructions -> parameters.add(parseMagicParameters(instructions))
        );
    }

    @Override
    public Long firstMethod() {
        return solve().getRight();
    }

    @Override
    public Long secondMethod() {
        return solve().getLeft();
    }

    private static record Parameters(int a, int b, int c) {
    }

    private Parameters parseMagicParameters(List<String> input) {
        return new Parameters(
            findLastNumber(input.get(4)),
            findLastNumber(input.get(5)),
            findLastNumber(input.get(15))
        );
    }

    private int findLastNumber(String line) {
        return Integer.parseInt(line.substring(line.lastIndexOf(" ") + 1));
    }

    private long magicFunction(Parameters parameters, long zValue, long wValue) {
        if (zValue % 26 + parameters.b != wValue) {
            return ((zValue / parameters.a) * 26) + wValue + parameters.c;
        }
        return zValue / parameters.a;
    }

    private Pair<Long, Long> solve() {
        var zValues = Map.of(0L, Pair.of(0L, 0L));
        for (Parameters parameter : parameters) {
            Map<Long, Pair<Long, Long>> zValuesThisRound = new HashMap<>();
            zValues.forEach((z, minMax) ->
                IntStream.rangeClosed(1, 9).forEach(digit -> {
                    var newValueForZ = magicFunction(parameter, z, digit);
                    if (parameter.a == 1 || (parameter.a == 26 && newValueForZ < z)) {
                        var oldMinMax = zValuesThisRound.getOrDefault(newValueForZ, Pair.of(Long.MAX_VALUE, Long.MIN_VALUE));
                        var newMin = Math.min(oldMinMax.getLeft(), minMax.getLeft() * 10 + digit);
                        var newMax = Math.max(oldMinMax.getRight(), minMax.getRight() * 10 + digit);
                        zValuesThisRound.put(newValueForZ, Pair.of(newMin, newMax));
                    }
                })
            );
            zValues = zValuesThisRound;
        }
        return zValues.get(0L);
    }
}
