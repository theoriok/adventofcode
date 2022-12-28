package org.theoriok.adventofcode.y2021;

import static org.assertj.core.api.Assertions.assertThat;
import static org.theoriok.adventofcode.y2021.Day24Old.Variable.X;
import static org.theoriok.adventofcode.y2021.Day24Old.Variable.Y;

import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.List;

class Day24OldTest {
    @Test
    void operationTest() {
        var variableShortEnumMap = new EnumMap<Day24Old.Variable, Short>(Day24Old.Variable.class);
        variableShortEnumMap.put(X, (short) 5);
        variableShortEnumMap.put(Y, (short) 2);
        var operation = new Day24Old.Operation(Day24Old.Operator.DIVIDE, List.of("x", "y"));

        operation.apply(variableShortEnumMap, (short) 0);

        assertThat(variableShortEnumMap).containsEntry(X, (short) 2);
    }

    @Test
    void operationTest2() {
        var variableShortEnumMap = new EnumMap<Day24Old.Variable, Short>(Day24Old.Variable.class);
        variableShortEnumMap.put(X, (short) 5);
        variableShortEnumMap.put(Y, (short) 2);
        var operation = new Day24Old.Operation(Day24Old.Operator.EQUAL, List.of("x", "y"));

        operation.apply(variableShortEnumMap, (short) 0);

        assertThat(variableShortEnumMap).containsEntry(X, (short) 0);
    }
}