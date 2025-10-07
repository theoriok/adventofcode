package org.theoriok.adventofcode.y2024;

import static org.theoriok.adventofcode.util.Utils.splitToList;

import org.theoriok.adventofcode.Day;

import java.util.List;

public class Day7 implements Day<Long, Long> {

    private final List<Equation> equations;

    public Day7(List<String> input) {
        equations = input.stream()
            .map(Equation::parse)
            .toList();
    }

    @Override
    public Long firstMethod() {
        return equations.stream()
            .filter(Equation::isValid)
            .mapToLong(Equation::expectedResult)
            .sum();
    }

    @Override
    public Long secondMethod() {
        return equations.stream()
            .filter(Equation::isValidWithConcat)
            .mapToLong(Equation::expectedResult)
            .sum();
    }

    record Equation(long expectedResult, List<Long> terms) {
        static Equation parse(String equation) {
            String[] parts = equation.split(": ");
            var expectedResult = Long.parseLong(parts[0]);
            var terms = splitToList(parts[1], " ", Long::parseLong);
            return new Equation(expectedResult, terms);
        }

        public boolean isValid() {
            return tryAllPossibilities(terms.getFirst(), 1) == expectedResult;
        }

        private long tryAllPossibilities(long result, int index) {
            if (index >= terms.size()) {
                return result;
            }
            long next = terms.get(index);
            long plus = tryAllPossibilities(result + next, index + 1);
            long mul = tryAllPossibilities(result * next, index + 1);
            if (plus == expectedResult || mul == expectedResult) {
                return expectedResult;
            } else {
                return -1;
            }
        }

        public boolean isValidWithConcat() {
            return tryAllPossibilitiesWithConcat(terms.getFirst(), 1) == expectedResult;
        }

        private long tryAllPossibilitiesWithConcat(long result, int index) {
            if (index >= terms.size()) {
                return result;
            }
            long next = terms.get(index);
            long sum = tryAllPossibilitiesWithConcat(result + next, index + 1);
            long product = tryAllPossibilitiesWithConcat(result * next, index + 1);
            long concatenation = tryAllPossibilitiesWithConcat(Long.parseLong(result + "" + next), index + 1);
            if (sum == expectedResult || product == expectedResult || concatenation == expectedResult) {
                return expectedResult;
            } else {
                return -1;
            }
        }
    }
}
