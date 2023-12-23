package org.theoriok.adventofcode.util;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Utils {
    private Utils() {
    }

    public static <T> List<T> splitToList(String input, String regex, Function<String, T> mapper) {
        return Arrays.stream(input.split(regex))
            .map(mapper)
            .toList();
    }
}
