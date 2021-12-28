package org.theoriok.adventofcode.y2021;

import com.google.common.collect.Iterators;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day24 extends Day<Long, Long> {

    private final List<InstructionSet> instructionSets;

    public Day24(List<String> input) {
        super(input);
        instructionSets = new ArrayList<>();
        var operations = input.stream()
            .map(this::toOperation)
            .toList();
        Iterators.partition(operations.listIterator(), 18).forEachRemaining(
            instructions -> instructionSets.add(new InstructionSet(instructions))
        );
    }

    private Operation toOperation(String line) {
        var strings = Arrays.stream(line.split(" ")).toList();
        var operator = Operator.fromString(strings.get(0));
        return new Operation(operator, strings.subList(1, strings.size()));
    }

    @Override
    public Long firstMethod() {
        EnumMap<Variable, Short> values = new EnumMap<>(Variable.class);
        Long number = 99999999999999L;
        do {
            if (!number.toString().contains("0")) {
                System.out.println(number);
                values = new EnumMap<>(Variable.class);
                for (int i = 0; i < instructionSets.size(); i++) {
                    values = instructionSets.get(i).run(values, Short.parseShort(number.toString().split("")[i]));
                }
            }
            number--;
        } while (values.get(Variable.Z) != 0);

        return number + 1;
    }

    @Override
    public Long secondMethod() {
        EnumMap<Variable, Short> values = new EnumMap<>(Variable.class);
        Long number = 11111111111111L;
        do {
            if (!number.toString().contains("0")) {
                System.out.println(number);
                values = new EnumMap<>(Variable.class);
                for (int i = 0; i < instructionSets.size(); i++) {
                    values = instructionSets.get(i).run(values, Short.parseShort(number.toString().split("")[i]));
                }
            }
            number++;
        } while (values.get(Variable.Z) != 0);

        return number - 1;
    }

    private static record Operation(
        Operator operator,
        List<String> parameters
    ) {
        public void apply(EnumMap<Variable, Short> values, short newValue) {
            switch (operator) {
                case INPUT -> setInput(values, newValue);
                case ADD -> add(values);
                case MULTIPLY -> multiply(values);
                case DIVIDE -> divide(values);
                case MOD -> mod(values);
                case EQUAL -> equal(values);
            }
        }

        private void equal(EnumMap<Variable, Short> values) {
            var variable = Variable.fromString(parameters.get(0));
            var value1 = values.getOrDefault(variable, (short) 0);
            var value2 = getStringValueOrVariableValue(parameters.get(1), values);
            values.put(variable, (short) (value1 == value2 ? 1 : 0));
        }

        private void mod(EnumMap<Variable, Short> values) {
            var variable = Variable.fromString(parameters.get(0));
            var value1 = values.getOrDefault(variable, (short) 0);
            var value2 = getStringValueOrVariableValue(parameters.get(1), values);
            values.put(variable, (short) (value1 % value2));
        }

        private void divide(EnumMap<Variable, Short> values) {
            var variable = Variable.fromString(parameters.get(0));
            var value1 = values.getOrDefault(variable, (short) 0);
            var value2 = getStringValueOrVariableValue(parameters.get(1), values);
            values.put(variable, (short) (value1 / value2));
        }

        private void multiply(EnumMap<Variable, Short> values) {
            var variable = Variable.fromString(parameters.get(0));
            var value1 = values.getOrDefault(variable, (short) 0);
            var value2 = getStringValueOrVariableValue(parameters.get(1), values);
            values.put(variable, (short) (value1 * value2));
        }

        private void add(EnumMap<Variable, Short> values) {
            var variable = Variable.fromString(parameters.get(0));
            var value1 = values.getOrDefault(variable, (short) 0);
            var value2 = getStringValueOrVariableValue(parameters.get(1), values);
            values.put(variable, (short) (value1 + value2));
        }

        private short getStringValueOrVariableValue(String input, EnumMap<Variable, Short> values) {
            if (NumberUtils.isCreatable(input)) {
                return Short.parseShort(input);
            } else {
                return values.getOrDefault(Variable.fromString(input), (short) 0);
            }
        }

        private void setInput(EnumMap<Variable, Short> values, short newValue) {
            values.put(Variable.fromString(parameters.get(0)), newValue);
        }
    }

    private enum Operator {
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

    private enum Variable {
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
        private final Map<Pair<EnumMap<Variable, Short>, Short>, EnumMap<Variable, Short>> inputToOutput = new HashMap<>();

        private InstructionSet(List<Operation> operations) {
            this.operations = operations;
        }

        public EnumMap<Variable, Short> run(EnumMap<Variable, Short> values, short newValue) {
            var input = Pair.of(values.clone(), newValue);
            if (inputToOutput.containsKey(input)) {
                return inputToOutput.get(input);
            } else {
                var output = values.clone();
                operations.forEach(operation -> operation.apply(output, newValue));
                inputToOutput.put(input, output);
                return output;
            }
        }
    }
}
