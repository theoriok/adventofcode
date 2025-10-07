package org.theoriok.adventofcode.y2015;

import static org.theoriok.adventofcode.y2015.Day16.Operation.EQUALS;
import static org.theoriok.adventofcode.y2015.Day16.Operation.GREATER_THAN;
import static org.theoriok.adventofcode.y2015.Day16.Operation.LESS_THAN;

import org.theoriok.adventofcode.Day;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day16 implements Day<Integer, Integer> {

    private static final Map<String, PropertyValue> KNOWN_PROPERTIES = Map.of(
        "children", new PropertyValue(EQUALS, 3),
        "cats", new PropertyValue(GREATER_THAN, 7),
        "samoyeds", new PropertyValue(EQUALS, 2),
        "pomeranians", new PropertyValue(LESS_THAN, 3),
        "akitas", new PropertyValue(EQUALS, 0),
        "vizslas", new PropertyValue(EQUALS, 0),
        "goldfish", new PropertyValue(LESS_THAN, 5),
        "trees", new PropertyValue(GREATER_THAN, 3),
        "cars", new PropertyValue(EQUALS, 2),
        "perfumes", new PropertyValue(EQUALS, 1)
    );
    private final List<Sue> sues;

    public Day16(List<String> input) {
        sues = input.stream()
            .map(Sue::from)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList();
    }

    @Override
    public Integer firstMethod() {
        return sues.stream()
            .filter(Sue::matchesKnownProperties)
            .findFirst()
            .map(Sue::number)
            .orElse(0);
    }

    @Override
    public Integer secondMethod() {
        return sues.stream()
            .filter(Sue::matchesKnownPropertiesMoreThoroughly)
            .findFirst()
            .map(Sue::number)
            .orElse(0);
    }

    record Sue(int number, Map<String, Integer> properties) {

        private static final Pattern SUE_PATTERN =
            Pattern.compile("Sue (\\d+): (\\w+): (\\d+), (\\w+): (\\d+), (\\w+): (\\d+)");

        public static Optional<Sue> from(String line) {
            Matcher matcher = SUE_PATTERN.matcher(line);
            if (matcher.find()) {
                return Optional.of(new Sue(
                    Integer.parseInt(matcher.group(1)),
                    Map.of(
                        matcher.group(2), Integer.parseInt(matcher.group(3)),
                        matcher.group(4), Integer.parseInt(matcher.group(5)),
                        matcher.group(6), Integer.parseInt(matcher.group(7))
                    )
                ));
            } else {
                return Optional.empty();
            }
        }

        public boolean matchesKnownProperties() {
            return properties.entrySet().stream()
                .allMatch(property -> property.getValue().equals(KNOWN_PROPERTIES.get(property.getKey()).value));
        }

        public boolean matchesKnownPropertiesMoreThoroughly() {
            return properties.entrySet().stream()
                .allMatch(property -> KNOWN_PROPERTIES.get(property.getKey()).matches(property.getValue()));
        }
    }

    record PropertyValue(Operation operation, Integer value) {
        public boolean matches(Integer otherValue) {
            return switch (operation) {
                case GREATER_THAN -> otherValue > value;
                case EQUALS -> value.equals(otherValue);
                case LESS_THAN -> otherValue < value;
            };
        }
    }

    enum Operation {
        GREATER_THAN,
        EQUALS,
        LESS_THAN,
    }
}
