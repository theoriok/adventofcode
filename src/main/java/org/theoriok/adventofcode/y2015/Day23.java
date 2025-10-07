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
        return beTheComputer(0);
    }

    private int beTheComputer(int startingA) {
        Registers registers = new Registers(startingA, 0);
        int counter = 0;

        while (counter < commands.size()) {
            Command command = commands.get(counter);
            counter += command.operation.execute(registers, command);
        }

        LOGGER.debug("A: {}\nB: {}", registers.a, registers.b);
        return registers.b;
    }

    @Override
    public Integer secondMethod() {
        return beTheComputer(1);
    }

    static class Registers {
        int a, b;

        Registers(int a, int b) {
            this.a = a;
            this.b = b;
        }

        int get(String register) {
            return switch (register) {
                case "a" -> a;
                case "b" -> b;
                default -> throw new IllegalArgumentException("Unknown register: " + register);
            };
        }

        void set(String register, int value) {
            switch (register) {
                case "a" -> a = value;
                case "b" -> b = value;
                default -> throw new IllegalArgumentException("Unknown register: " + register);
            }
        }
    }

    enum Operation {
        HALF("hlf") {
            @Override
            int execute(Registers registers, Command command) {
                registers.set(command.register, registers.get(command.register) / 2);
                return 1;
            }
        },
        TRIPLE("tpl") {
            @Override
            int execute(Registers registers, Command command) {
                registers.set(command.register, registers.get(command.register) * 3);
                return 1;
            }
        },
        INCREMENT("inc") {
            @Override
            int execute(Registers registers, Command command) {
                registers.set(command.register, registers.get(command.register) + 1);
                return 1;
            }
        },
        JUMP("jmp") {
            @Override
            int execute(Registers registers, Command command) {
                return command.amount;
            }
        },
        JUMP_IF_EVEN("jie") {
            @Override
            int execute(Registers registers, Command command) {
                return registers.get(command.register) % 2 == 0 ? command.amount : 1;
            }
        },
        JUMP_IF_ONE("jio") {
            @Override
            int execute(Registers registers, Command command) {
                return registers.get(command.register) == 1 ? command.amount : 1;
            }
        };

        private final String command;

        Operation(String command) {
            this.command = command;
        }

        abstract int execute(Registers registers, Command command);

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
                case JUMP_IF_EVEN -> new Command(Operation.JUMP_IF_EVEN, split[1].substring(0, 1), Integer.parseInt(split[2]));
                case JUMP_IF_ONE -> new Command(Operation.JUMP_IF_ONE, split[1].substring(0, 1), Integer.parseInt(split[2]));
            };
        }
    }
}
