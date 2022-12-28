package org.theoriok.adventofcode;

public interface Day<O1, O2> {
    default O1 firstMethod() {
        return null;
    }

    default O2 secondMethod() {
        return null;
    }
}
