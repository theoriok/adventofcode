package org.theoriok.adventofcode.y2025;

import static org.theoriok.adventofcode.util.Utils.splitToList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theoriok.adventofcode.Day;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Day2 implements Day<Long, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day2.class);
    private final List<Range> ranges;

    public Day2(List<String> input) {
        ranges = input.stream()
            .flatMap(line -> splitToList(line, ",", Range::from).stream())
            .toList();
    }

    @Override
    public Long firstMethod() {
        return ranges.stream()
            .flatMapToLong(Range::invalidIds)
            .sum();
    }

    @Override
    public Long secondMethod() {
        return  ranges.stream()
            .flatMapToLong(Range::reallyInvalidIds)
            .sum();
    }

    private record Range(Long start, Long end) {
        static Range from(String input) {
            var longs = splitToList(input, "-", Long::parseLong);
            return new Range(longs.get(0), longs.get(1));
        }

        public LongStream invalidIds() {
            return LongStream.rangeClosed(start, end)
                .mapToObj(Id::from)
                .filter(Id::invalid)
                .mapToLong(Id::toLong);
        }

        public LongStream reallyInvalidIds() {
            return LongStream.rangeClosed(start, end)
                .mapToObj(Id::from)
                .filter(Id::reallyInvalid)
                .mapToLong(Id::toLong);
        }
    }

    private record Id(String number) {

        public boolean invalid() {
            return number.length() % 2 == 0 && substringsOfSizeAllEqual(number.length() / 2);
        }

        public boolean reallyInvalid() {
            for (int i = 1; i <= number.length() / 2; i++) {
                if (substringsOfSizeAllEqual(i)) {
                    return true;
                }
            }
            return false;
        }

        private boolean substringsOfSizeAllEqual(int substringSize) {
            if (number.length() % substringSize == 0) {
                var substrings = splitToList(number, "(?<=\\G.{%d})".formatted(substringSize), Function.identity()).stream()
                    .collect(Collectors.groupingBy(Function.identity()));
                return substrings.size() == 1;
            }
            return false;
        }

        public static Id from(long l) {
            return new Id(String.valueOf(l));
        }

        public long toLong() {
            return Long.parseLong(number);
        }
    }
}
