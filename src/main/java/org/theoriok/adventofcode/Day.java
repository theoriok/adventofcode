package org.theoriok.adventofcode;

import java.util.List;

public abstract class Day<O1, O2> {
    protected final List<String> input;

    protected Day(List<String> input) {
        this.input = input;
    }

    public O1 firstMethod() {
        return null;
    }

    public O2 secondMethod() {
        return null;
    }
}
