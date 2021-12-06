package org.theoriok.adventofcode;

import java.util.List;

public abstract class Day {
    protected final List<String> input;

    protected Day(List<String> input) {
        this.input = input;
    }

    public long firstMethod() {
        return -1;
    }

    public long secondMethod() {
        return -1;
    }
}
