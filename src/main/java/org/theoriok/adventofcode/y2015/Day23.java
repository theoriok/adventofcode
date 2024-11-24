package org.theoriok.adventofcode.y2015;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theoriok.adventofcode.Day;

import java.util.List;

public class Day23 implements Day<Integer, Integer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day23.class);
    private final List<Command> commands;

    public Day23(List<String> input) {
        commands = input.stream()
            .map(Command::parse)
            .toList();
    }

    @Override
    public Integer firstMethod() {
        return beTheComputer(0, 0);
    }

    private int beTheComputer(int startingA, int startingB) {
        int registerA = startingA;
        int registerB = startingB;
        int counter = 0;
        while (true) {
            if (counter >= commands.size()) {
                LOGGER.debug("A: {}\nB: {}", registerA, registerB);
                return registerB;
            }
            Command command = commands.get(counter);
            switch (command.operation) {
                case HALF -> {
                    if (command.register.equalsIgnoreCase("a")) {
                        registerA /= 2;
                    }
                    if (command.register.equalsIgnoreCase("b")) {
                        registerB /= 2;
                    }
                    counter++;
                }
                case TRIPLE -> {
                    if (command.register.equalsIgnoreCase("a")) {
                        registerA *= 3;
                    }
                    if (command.register.equalsIgnoreCase("b")) {
                        registerB *= 3;
                    }
                    counter++;
                }
                case INCREMENT -> {
                    if (command.register.equalsIgnoreCase("a")) {
                        registerA += 1;
                    }
                    if (command.register.equalsIgnoreCase("b")) {
                        registerB += 1;
                    }
                    counter++;
                }
                case JUMP -> counter += command.amount;
                case JUMP_IF_EVEN -> {
                    if (
                        (command.register.equalsIgnoreCase("a") && registerA % 2 == 0)
                            || (command.register.equalsIgnoreCase("b") && registerB % 2 == 0)
                    ) {
                        counter += command.amount;
                    } else {
                        counter++;
                    }
                }
                case JUMP_IF_ONE -> {
                    if (
                        (command.register.equalsIgnoreCase("a") && registerA == 1)
                            || (command.register.equalsIgnoreCase("b") && registerB == 1)
                    ) {
                        counter += command.amount;
                    } else {
                        counter++;
                    }
                }
            }
        }
    }

    @Override
    public Integer secondMethod() {
        return beTheComputer(1, 0);
    }

    enum Operation {
        HALF("hlf"),
        TRIPLE("tpl"),
        INCREMENT("inc"),
        JUMP("jmp"),
        JUMP_IF_EVEN("jie"),
        JUMP_IF_ONE("jio");

        private final String command;

        Operation(String command) {
            this.command = command;
        }

        static Operation parse(String command) {
            for (Operation op : Operation.values()) {
                if (op.command.equals(command)) {
                    return op;
                }
            }
            throw new IllegalArgumentException("Unknown operation: " + command);
        }
    }

    record Command(Operation operation, String register, int amount) {
        static Command parse(String line) {
            String[] split = line.split(" ");
            return switch (Operation.parse(split[0])) {
                case HALF -> new Command(Operation.HALF, split[1], 0);
                case TRIPLE -> new Command(Operation.TRIPLE, split[1], 0);
                case INCREMENT -> new Command(Operation.INCREMENT, split[1], 0);
                case JUMP -> new Command(Operation.JUMP, "", Integer.parseInt(split[1]));
                case JUMP_IF_EVEN ->
                    new Command(Operation.JUMP_IF_EVEN, split[1].substring(0, 1), Integer.parseInt(split[2]));
                case JUMP_IF_ONE ->
                    new Command(Operation.JUMP_IF_ONE, split[1].substring(0, 1), Integer.parseInt(split[2]));
            };
        }
    }
}
