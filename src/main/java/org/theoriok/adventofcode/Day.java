package org.theoriok.adventofcode;

import java.util.List;

public abstract class Day {
    protected final List<String> input;

    protected Day(List<String> input) {
        this.input = input;
    }

    public int firstMethod() {
        return -1;
    }

    public int secondMethod() {
        return -1;
    }
}
