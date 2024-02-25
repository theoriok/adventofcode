package org.theoriok.adventofcode.y2021;

import static java.util.Comparator.comparing;
import static org.theoriok.adventofcode.util.Utils.splitToList;
import static org.theoriok.adventofcode.y2021.Day24Old.Variable.Z;

import com.google.common.collect.Iterators;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class Day24Old implements Day<Long, Long> {

    private final List<InstructionSet> instructionSets;

    public Day24Old(List<String> input) {
        instructionSets = new ArrayList<>();
        var operations = input.stream()
            .map(this::toOperation)
            .toList();
        Iterators.partition(operations.listIterator(), 18).forEachRemaining(
            instructions -> instructionSets.add(new InstructionSet(instructions))
        );
    }

    private Operation toOperation(String line) {
        var strings = split(line);
        var operator = Operator.fromString(strings.getFirst());
        return new Operation(operator, strings.subList(1, strings.size()));
    }

    private static List<String> split(String line) {
        return splitToList(line, " ", Function.identity());
    }

    @Override
    public Long firstMethod() {
        var instructionSet = instructionSets.getFirst();
        for (short i = 9; i > 0; i--) {
            instructionSet.run((short) 0, i);
            runForAllOutputs(1, instructionSet.getInputToOutput().values());
            if (anyValidValue()) {
                return findBiggestValidValue();
            }
        }

        return 0L;
    }

    private long findBiggestValidValue() {
        Map<Short, List<Triple<Short, Short, Short>>> biggestAndZByIndex = new HashMap<>();
        var index = (short) 13;
        var allForZ = instructionSets.get(index).findAllForZs(List.of((short) 0));
        biggestAndZByIndex.put(index, allForZ);
        do {
            index--;
            allForZ = instructionSets.get(index).findAllForZs(allForZ.stream().map(Triple::getLeft).toList());
            biggestAndZByIndex.put(index, allForZ);
        } while (index > 0);
        var sb = new StringBuilder();
        Triple<Short, Short, Short> triple = biggestAndZByIndex.get((short) 0).stream()
            .max(comparing(Triple::getMiddle))
            .orElseThrow();
        sb.append(triple.getMiddle());
        for (short i = 1; i < 14; i++) {
            Triple<Short, Short, Short> finalTriple = triple;
            triple = biggestAndZByIndex.get(i).stream()
                .filter(p -> Objects.equals(p.getLeft(), finalTriple.getRight()))
                .max(comparing(Triple::getMiddle))
                .orElseThrow();
            sb.append(triple.getMiddle());
        }
        return Long.parseLong(sb.toString());
    }

    @Override
    public Long secondMethod() {
        var instructionSet = instructionSets.getFirst();
        for (short i = 1; i <= 9; i++) {
            instructionSet.run((short) 0, i);
            runForAllOutputs(1, instructionSet.getInputToOutput().values());
            if (anyValidValue()) {
                var sb = new StringBuilder();
                findSmallest(sb, 13, (short) 0);
                return Long.parseLong(sb.toString());
            }
        }

        return 0L;
    }

    private void findSmallest(StringBuilder sb, int index, short zToFind) {
        var allForZ = instructionSets.get(index).findAllForZ(zToFind, comparing(Pair::getRight));
        if (index > 0 && sb.length() < index) {
            for (Pair<Short, Short> input : allForZ) {
                findSmallest(sb, index - 1, input.getLeft());
            }
        }
        if (sb.length() == index) {
            sb.append(
                allForZ.stream()
                    .mapToInt(Pair::getRight)
                    .min()
                    .orElseThrow()
            );
        }
    }

    private boolean anyValidValue() {
        return instructionSets.get(13).getInputToOutput().values().stream()
            .anyMatch(value -> value == 0);
    }

    private void runForAllOutputs(int index, Collection<Short> outputs) {
        var instructionSet = instructionSets.get(index);
        for (Short value : outputs) {
            for (short j = 9; j > 0; j--) {
                instructionSet.run(value, j);
            }
        }
        if (index + 1 < instructionSets.size()) {
            runForAllOutputs(index + 1, instructionSet.getInputToOutput().values());
        }
    }

    static record Operation(
        Operator operator,
        List<String> parameters
    ) {
        @SuppressWarnings("PMD.SwitchStmtsShouldHaveDefault") // PMD does not recognize Java 13+ enhanced switch statements
        public void apply(Map<Variable, Short> values, short newValue) {
            switch (operator) {
                case INPUT -> setInput(values, newValue);
                case ADD -> add(values);
                case MULTIPLY -> multiply(values);
                case DIVIDE -> divide(values);
                case MOD -> mod(values);
                case EQUAL -> isEqual(values);
                default -> throw new IllegalStateException("Unexpected value: " + operator);
            }
        }

        private void isEqual(Map<Variable, Short> values) {
            var variable = Variable.fromString(parameters.getFirst());
            var value1 = values.getOrDefault(variable, (short) 0);
            var value2 = getStringValueOrVariableValue(parameters.get(1), values);
            values.put(variable, (short) (value1 == value2 ? 1 : 0));
        }

        private void mod(Map<Variable, Short> values) {
            var variable = Variable.fromString(parameters.getFirst());
            var value1 = values.getOrDefault(variable, (short) 0);
            var value2 = getStringValueOrVariableValue(parameters.get(1), values);
            values.put(variable, (short) (value1 % value2));
        }

        private void divide(Map<Variable, Short> values) {
            var variable = Variable.fromString(parameters.getFirst());
            var value1 = values.getOrDefault(variable, (short) 0);
            var value2 = getStringValueOrVariableValue(parameters.get(1), values);
            values.put(variable, (short) (value1 / value2));
        }

        private void multiply(Map<Variable, Short> values) {
            var variable = Variable.fromString(parameters.getFirst());
            var value1 = values.getOrDefault(variable, (short) 0);
            var value2 = getStringValueOrVariableValue(parameters.get(1), values);
            values.put(variable, (short) (value1 * value2));
        }

        private void add(Map<Variable, Short> values) {
            var variable = Variable.fromString(parameters.getFirst());
            var value1 = values.getOrDefault(variable, (short) 0);
            var value2 = getStringValueOrVariableValue(parameters.get(1), values);
            values.put(variable, (short) (value1 + value2));
        }

        private short getStringValueOrVariableValue(String input, Map<Variable, Short> values) {
            if (NumberUtils.isCreatable(input)) {
                return Short.parseShort(input);
            } else {
                return values.getOrDefault(Variable.fromString(input), (short) 0);
            }
        }

        private void setInput(Map<Variable, Short> values, short newValue) {
            values.put(Variable.fromString(parameters.getFirst()), newValue);
        }
    }

    enum Operator {
        INPUT("inp"),
        ADD("add"),
        MULTIPLY("mul"),
        DIVIDE("div"),
        MOD("mod"),
        EQUAL("eql");

        private final String stringRepresentation;

        Operator(String stringRepresentation) {
            this.stringRepresentation = stringRepresentation;
        }

        public static Operator fromString(String stringRepresentation) {
            return Arrays.stream(values())
                .filter(operator -> operator.stringRepresentation.equals(stringRepresentation))
                .findFirst()
                .orElseThrow();
        }
    }

    enum Variable {
        W,
        X,
        Y,
        Z;

        public static Variable fromString(String stringRepresentation) {
            return Arrays.stream(values())
                .filter(operator -> operator.name().equalsIgnoreCase(stringRepresentation))
                .findFirst()
                .orElseThrow();
        }
    }

    private static class InstructionSet {
        private final List<Operation> operations;
        private final Map<Pair<Short, Short>, Short> inputToOutput = new HashMap<>();

        private InstructionSet(List<Operation> operations) {
            this.operations = operations;
        }

        public void run(Short zValue, short newValue) {
            inputToOutput.computeIfAbsent(Pair.of(zValue, newValue), any -> runOperations(zValue, newValue));
        }

        private Short runOperations(Short zValue, short newValue) {
            var output = new EnumMap<Variable, Short>(Variable.class);
            output.put(Z, zValue);
            operations.forEach(operation -> operation.apply(output, newValue));
            return output.get(Z);
        }

        public Map<Pair<Short, Short>, Short> getInputToOutput() {
            return inputToOutput;
        }

        public List<Pair<Short, Short>> findAllForZ(short zToFind, Comparator<Pair<Short, Short>> comparator) {
            return inputToOutput.entrySet().stream()
                .filter(entry -> entry.getValue() == zToFind)
                .map(Map.Entry::getKey)
                .sorted(comparator)
                .toList();
        }

        public List<Triple<Short, Short, Short>> findAllForZs(List<Short> zsToFind) {
            return inputToOutput.entrySet().stream()
                .filter(entry -> zsToFind.contains(entry.getValue()))
                .map(entry -> Triple.of(entry.getKey().getLeft(), entry.getKey().getRight(), entry.getValue()))
                .toList();
        }
    }
}
