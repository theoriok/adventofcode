package org.theoriok.adventofcode.y2015;

import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

public class Day15 implements Day<Integer, Integer> {


    private final List<Ingredient> ingredients;

    public Day15(List<String> input) {
        ingredients = input.stream()
            .map(Ingredient::from)
            .toList();
    }

    @Override
    public Integer firstMethod() {
        List<List<Integer>> options = divide(100, ingredients.size());
        return options.stream()
            .map(option -> new Cookie(
                ingredients.stream()
                    .collect(toMap(Function.identity(), ingredient -> option.get(ingredients.indexOf(ingredient))))
            ))
            .mapToInt(Cookie::score)
            .max().orElse(0);
    }

    @Override
    public Integer secondMethod() {
        List<List<Integer>> options = divide(100, ingredients.size());
        return options.stream()
            .map(option -> new Cookie(
                ingredients.stream()
                    .collect(toMap(Function.identity(), ingredient -> option.get(ingredients.indexOf(ingredient))))
            ))
            .filter(cookie -> cookie.calories() == 500)
            .mapToInt(Cookie::score)
            .max().orElse(0);
    }

    record Ingredient(int capacity, int durability, int flavor, int texture, int calories) {
        public static Ingredient from(String line) {
            String[] parts = line.replace(",", "").split(" ");
            return new Ingredient(
                Integer.parseInt(parts[2]),
                Integer.parseInt(parts[4]),
                Integer.parseInt(parts[6]),
                Integer.parseInt(parts[8]),
                Integer.parseInt(parts[10])
            );
        }
    }

    record Cookie(Map<Ingredient, Integer> recipe) {
        public int score() {
            return capacity() * durability() * flavor() * texture();
        }

        private int capacity() {
            return Math.max(0, fromIngredients(Ingredient::capacity));
        }

        private int durability() {
            return Math.max(0, fromIngredients(Ingredient::durability));
        }

        private int flavor() {
            return Math.max(0, fromIngredients(Ingredient::flavor));
        }

        private int texture() {
            return Math.max(0, fromIngredients(Ingredient::texture));
        }

        public int calories() {
            return fromIngredients(Ingredient::calories);
        }

        private int fromIngredients(ToIntFunction<Ingredient> toIntFunction) {
            return recipe.entrySet()
                .stream()
                .mapToInt(
                    entry -> entry.getValue() * toIntFunction.applyAsInt(entry.getKey())
                )
                .sum();
        }
    }

    private static List<List<Integer>> divide(int count, int groups) {
        if (groups == 1) {
            final List<Integer> inner = new ArrayList<>(1);
            inner.add(count);
            final List<List<Integer>> outer = new ArrayList<>(1);
            outer.add(inner);
            return outer;
        }
        return IntStream.rangeClosed(0, count)
            .boxed()
            .flatMap(p -> divide(count - p, groups - 1)
                .stream()
                .peek(q -> q.add(p))
            )
            .collect(Collectors.toList());
    }
}
