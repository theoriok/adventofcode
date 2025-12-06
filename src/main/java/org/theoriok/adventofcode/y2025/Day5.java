package org.theoriok.adventofcode.y2025;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Gatherer;

public class Day5 implements Day<Long, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day5.class);
    private final List<Range> ranges;
    private final List<Long> ingredients;

    public Day5(List<String> input) {
        int splits = input.indexOf("");
        ranges = input.subList(0, splits).stream()
            .map(Range::from)
            .sorted(Comparator.comparing(Range::start).thenComparing(Range::end))
            .gather(
                Gatherer.<Range, ArrayList<Range>, Range>ofSequential(
                    ArrayList::new,
                    (state, range, _) -> integrate(state, range),
                    (state, downstream) -> state.forEach(downstream::push)
                )
            )
            .toList();
        ingredients = input.subList(splits + 1, input.size()).stream()
            .map(Long::parseLong)
            .toList();
    }

    private boolean integrate(ArrayList<Range> state, Range range) {
        if (state.isEmpty() || !state.getLast().overlaps(range)) {
            state.add(range);
        } else {
            state.set(state.size() - 1, state.getLast().merge(range));
        }
        return true;
    }

    @Override
    public Long firstMethod() {
        return ingredients.stream()
            .filter(ingredient -> ranges.stream().anyMatch(range -> range.contains(ingredient)))
            .count();
    }

    @Override
    public Long secondMethod() {
        return ranges.stream()
            .mapToLong(Range::count)
            .sum();
    }

    private record Range(Long start, Long end) {

        public static Range from(String input) {
            String[] split = input.split("-");
            return new Range(Long.parseLong(split[0]), Long.parseLong(split[1]));
        }

        public boolean contains(Long ingredient) {
            return start <= ingredient && end >= ingredient;
        }

        public boolean overlaps(Range range) {
            return (end >= range.start && start <= range.end)
                || start == range.end + 1
                || end == range.start - 1
                || start.equals(range.start)
                || end.equals(range.end);
        }

        public Range merge(Range range) {
            return new Range(Math.min(start, range.start), Math.max(end, range.end));
        }

        public long count() {
            return end - start + 1;
        }
    }
}
