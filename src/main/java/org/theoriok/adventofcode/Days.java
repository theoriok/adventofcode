package org.theoriok.adventofcode;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.stream.IntStream;

public class Days {
    public List<Integer> availableDays(int year) {
        try {
            return IntStream.rangeClosed(1, 25)
                .filter(day -> {
                    try {
                        Class.forName("org.theoriok.adventofcode.y%d.Day%d".formatted(year, day));
                        return true;
                    } catch (ClassNotFoundException e) {
                        return false;
                    }
                })
                .boxed()
                .toList();
        } catch (Exception ignored) {
            return emptyList();
        }
    }
}
