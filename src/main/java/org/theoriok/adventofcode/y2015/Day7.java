package org.theoriok.adventofcode.y2015;

import org.theoriok.adventofcode.Day;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day7 implements Day<Integer, Integer> {

    private final Map<String, String> functions;
    private Map<String, Integer> values;

    public Day7(List<String> input) {
        functions = input.stream()
            .map(line -> line.split(" -> "))
            .collect(Collectors.toMap(split -> split[1], split -> split[0]));
    }

    @Override
    public Integer firstMethod() {
        values = new HashMap<>();
        return decypher("a");
    }

    private Integer decypher(String input) {
        var value = functions.getOrDefault(input, input);
        if (value.contains("NOT")) {
            String not = value.replace("NOT ", "");
            return ~getOrCompute(not);
        }
        if (value.contains("AND")) {
            String first = value.substring(0, value.indexOf(" AND "));
            String second = value.substring(value.indexOf(" AND ") + 5);
            return getOrCompute(first) & getOrCompute(second);
        }
        if (value.contains("OR")) {
            String first = value.substring(0, value.indexOf(" OR "));
            String second = value.substring(value.indexOf(" OR ") + 4);
            return getOrCompute(first) | getOrCompute(second);
        }
        if (value.contains("LSHIFT")) {
            String first = value.substring(0, value.indexOf(" LSHIFT "));
            String second = value.substring(value.indexOf(" LSHIFT ") + 8);
            return getOrCompute(first) << getOrCompute(second);
        }
        if (value.contains("RSHIFT")) {
            String first = value.substring(0, value.indexOf(" RSHIFT "));
            String second = value.substring(value.indexOf(" RSHIFT ") + 8);
            return getOrCompute(first) >>> getOrCompute(second);
        }
        if (functions.containsKey(value)) {
            return decypher(value);
        }
        return Integer.parseInt(value);
    }

    private Integer getOrCompute(String value) {
        if (values.containsKey(value)) {
            return values.get(value);
        } else {
            Integer decypher = decypher(value);
            values.put(value, decypher);
            return decypher;
        }
    }

    @Override
    public Integer secondMethod() {
        functions.put("b", Integer.toString(firstMethod()));
        values = new HashMap<>();
        return decypher("a");
    }
}
