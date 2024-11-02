package org.theoriok.adventofcode.y2021;

import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;

public class Day16 implements Day<Integer, Long> {
    private static final Map<String, String> HEX_2_BIN = Map.ofEntries(
        Map.entry("0", "0000"),
        Map.entry("1", "0001"),
        Map.entry("2", "0010"),
        Map.entry("3", "0011"),
        Map.entry("4", "0100"),
        Map.entry("5", "0101"),
        Map.entry("6", "0110"),
        Map.entry("7", "0111"),
        Map.entry("8", "1000"),
        Map.entry("9", "1001"),
        Map.entry("A", "1010"),
        Map.entry("B", "1011"),
        Map.entry("C", "1100"),
        Map.entry("D", "1101"),
        Map.entry("E", "1110"),
        Map.entry("F", "1111")
    );
    private final Packet packet;

    public Day16(List<String> input) {
        packet = new Packet(hexToBinary(input.getFirst()));
    }

    @Override
    public Integer firstMethod() {
        return packet.versionSum();
    }

    @Override
    public Long secondMethod() {
        return packet.getValue();
    }

    private static class Packet {
        private final int version;
        private final long value;
        private final String remainder;
        private final List<Packet> subPackets = new ArrayList<>();

        public Packet(String binaryString) {
            version = Integer.parseInt(binaryString.substring(0, 3), 2);
            Operation operation = Operation.forTypeId(Integer.parseInt(binaryString.substring(3, 6), 2));
            if (operation == Operation.LITERAL) {
                var sb = new StringBuilder();
                var counter = 0;
                var substring = "";
                do {
                    counter++;
                    substring = binaryString.substring(1 + (counter * 5), 6 + (counter * 5));
                    sb.append(substring, 1, 5);
                } while (substring.startsWith("1"));
                this.value = Long.parseLong(sb.toString(), 2);
                remainder = binaryString.substring(6 + (counter * 5));
            } else {
                var lengthTypeId = binaryString.substring(6, 7);
                if ("0".equals(lengthTypeId)) {
                    var length = Integer.parseInt(binaryString.substring(7, 22), 2);
                    var data = binaryString.substring(22, 22 + length);
                    Packet subPacket;
                    do {
                        subPacket = new Packet(data);
                        data = subPacket.remainder;
                        subPackets.add(subPacket);
                    } while (!data.isEmpty());
                    remainder = binaryString.substring(22 + length);
                } else {
                    var nrSubPackets = Integer.parseInt(binaryString.substring(7, 18), 2);
                    var data = binaryString.substring(18);
                    do {
                        Packet subPacket;
                        subPacket = new Packet(data);
                        data = subPacket.remainder;
                        subPackets.add(subPacket);
                    } while (subPackets.size() < nrSubPackets);
                    remainder = subPackets.get(nrSubPackets - 1).remainder;
                }
                value = operation.doOperation(subPackets);
            }
        }

        public int versionSum() {
            return version + subPackets.stream().mapToInt(Packet::versionSum).sum();
        }

        public long getValue() {
            return value;
        }
    }

    private String hexToBinary(String hex) {
        return Arrays.stream(hex.split(""))
            .map(HEX_2_BIN::get)
            .collect(joining());
    }

    private enum Operation {
        SUM(0) {
            @Override
            public long doOperation(List<Packet> packets) {
                return packets.stream().mapToLong(Packet::getValue).sum();
            }
        },
        PRODUCT(1) {
            @Override
            public long doOperation(List<Packet> packets) {
                return packets.stream().mapToLong(Packet::getValue).reduce((value1, value2) -> value1 * value2).orElse(-1);
            }
        },
        MIN(2) {
            @Override
            public long doOperation(List<Packet> packets) {
                return packets.stream().mapToLong(Packet::getValue).min().orElse(-1);
            }
        },
        MAX(3) {
            @Override
            public long doOperation(List<Packet> packets) {
                return packets.stream().mapToLong(Packet::getValue).max().orElse(-1);
            }
        },
        LITERAL(4) {
            @Override
            public long doOperation(List<Packet> packets) {
                throw new UnsupportedOperationException();
            }
        },
        GREATER_THAN(5) {
            @Override
            public long doOperation(List<Packet> packets) {
                return packets.getFirst().value > packets.get(1).getValue() ? 1 : 0;
            }
        },
        LESS_THAN(6) {
            @Override
            public long doOperation(List<Packet> packets) {
                return packets.getFirst().value < packets.get(1).getValue() ? 1 : 0;
            }
        },
        EQUAL_TO(7) {
            @Override
            public long doOperation(List<Packet> packets) {
                return packets.getFirst().value == packets.get(1).getValue() ? 1 : 0;
            }
        };

        private final int typeId;

        Operation(int typeId) {
            this.typeId = typeId;
        }

        public static Operation forTypeId(int typeId) {
            return Arrays.stream(Operation.values())
                .filter(operation -> operation.typeId == typeId)
                .findFirst().orElseThrow();
        }

        public abstract long doOperation(List<Packet> packets);
    }
}
