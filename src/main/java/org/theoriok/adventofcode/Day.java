package org.theoriok.adventofcode;

import java.util.List;

public abstract class Day<OUTPUT1, OUTPUT2> {
    protected final List<String> input;

    protected Day(List<String> input) {
        this.input = input;
    }

    public OUTPUT1 firstMethod() {
        return null;
    }

    public OUTPUT2 secondMethod() {
        return null;
    }
}
