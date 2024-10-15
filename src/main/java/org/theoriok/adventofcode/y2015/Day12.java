package org.theoriok.adventofcode.y2015;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theoriok.adventofcode.Day;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Gatherers;
import java.util.stream.StreamSupport;

public class Day12 implements Day<Integer, Integer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day12.class);
    private final String jsonAsString;

    public Day12(List<String> input) {
        LOGGER.info("Size: {}", input.size());
        jsonAsString = input.getFirst();
    }

    @Override
    public Integer firstMethod() {
        try {
            JsonNode jsonNode = new ObjectMapper().readValue(jsonAsString, JsonNode.class);
            return countForNode(jsonNode, _ -> false, countFunctionFirstMethod());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private int countForNode(JsonNode jsonNode, Predicate<JsonNode> predicate, BiFunction<Integer, JsonNode, Integer> countFunction) {
        if (jsonNode.isObject() && StreamSupport.stream(jsonNode.spliterator(), false).anyMatch(predicate)) {
            return 0;
        }
        return StreamSupport.stream(jsonNode.spliterator(), false)
                .gather(Gatherers.fold(() -> 0, countFunction))
                .reduce(Integer::sum)
                .orElse(0);
    }

    private BiFunction<Integer, JsonNode, Integer> countFunctionFirstMethod() {
        return (counter, node) -> node.isInt()
                ? counter + node.asInt()
                : counter + countForNode(node, _ -> false, countFunctionFirstMethod());
    }

    @Override
    public Integer secondMethod() {
        try {
            JsonNode jsonNode = new ObjectMapper().readValue(jsonAsString, JsonNode.class);
            Predicate<JsonNode> predicate = subNode -> "red".equals(subNode.textValue());
            return countForNode(jsonNode, predicate, countFunctionSecondMethod(predicate));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private BiFunction<Integer, JsonNode, Integer> countFunctionSecondMethod(Predicate<JsonNode> predicate) {
        return (counter, node) -> node.isInt()
                ? counter + node.asInt()
                : node.isArray() || StreamSupport.stream(node.spliterator(), false).noneMatch(predicate)
                ? counter + countForNode(node, predicate, countFunctionSecondMethod(predicate))
                : counter;
    }
}
