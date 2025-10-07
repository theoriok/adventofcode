package org.theoriok.adventofcode.y2015;

import static org.theoriok.adventofcode.util.Utils.splitToList;

import com.google.common.collect.Collections2;
import org.theoriok.adventofcode.Day;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;

public class Day13 implements Day<Integer, Integer> {

    private final List<Person> people;

    public Day13(List<String> input) {
        people = input.stream()
            .gather(new LineToPersonGatherer())
            .collect(Collectors.toList());
    }

    @Override
    public Integer firstMethod() {
        return Collections2.permutations(people)
            .stream()
            .mapToInt(this::totalHappiness)
            .max()
            .orElse(0);
    }

    private int totalHappiness(List<Person> permutation) {
        return permutation.stream()
            .gather(Gatherers.windowSliding(2))
            .mapToInt(pair -> happinessBetween(pair.getFirst(), pair.getLast()))
            .sum()
            + happinessBetween(permutation.getFirst(), permutation.getLast());
    }

    private int happinessBetween(Person firstPerson, Person secondPerson) {
        return firstPerson.happinessFor(secondPerson) + secondPerson.happinessFor(firstPerson);
    }

    @Override
    public Integer secondMethod() {
        people.add(new Person("me", new HashMap<>()));
        return Collections2.permutations(people)
            .stream()
            .mapToInt(this::totalHappiness)
            .max()
            .orElse(0);
    }

    record Person(String name, Map<String, Integer> happiness) {
        public int happinessFor(Person person) {
            return happiness.getOrDefault(person.name, 0);
        }
    }

    private static class LineToPersonGatherer implements Gatherer<String, Map<String, Map<String, Integer>>, Person> {

        @Override
        public Supplier<Map<String, Map<String, Integer>>> initializer() {
            return HashMap::new;
        }

        @Override
        public Integrator<Map<String, Map<String, Integer>>, String, Person> integrator() {
            return (state, element, downstream) -> {
                var elements = splitToList(element.replace(".", ""), " ", Function.identity());
                var happiness = Integer.parseInt(elements.get(3)) * (elements.contains("gain") ? 1 : -1);
                state.computeIfAbsent(elements.getFirst(), _ -> new HashMap<>()).put(elements.getLast(), happiness);
                return true;
            };
        }

        @Override
        public BiConsumer<Map<String, Map<String, Integer>>, Downstream<? super Person>> finisher() {
            return (state, downstream) -> state.entrySet().stream()
                .map(entry -> new Person(entry.getKey(), entry.getValue()))
                .forEach(downstream::push);
        }
    }
}
