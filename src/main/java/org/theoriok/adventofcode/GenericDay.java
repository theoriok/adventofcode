package org.theoriok.adventofcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GenericDay implements Day<Integer, Integer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericDay.class);

    public GenericDay(List<String> input) {
        LOGGER.info("Size: {}", input.size());
    }

    @Override
    public Integer firstMethod() {
        return 0;
    }

    @Override
    public Integer secondMethod() {
        return 0;
    }
}
