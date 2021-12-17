package org.theoriok.adventofcode.y2021;

import static java.util.stream.Collectors.joining;
import static org.springframework.util.CollectionUtils.newHashMap;

import org.apache.commons.lang3.math.NumberUtils;
import org.theoriok.adventofcode.Day;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day16 extends Day {
    public Day16(List<String> input) {
        super(input);
    }

    @Override
    public long firstMethod() {
        var packet = new Packet(input.get(0));
        return packet.version;
    }

    private static class Packet {
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
        private final int version;
        private final int typeId;

        public Packet(String hexadecimalString) {
            var binaryString = hexToBinary(hexadecimalString);
            version = Integer.parseInt(binaryString.substring(0, 3), 2);
            typeId = Integer.parseInt(binaryString.substring(3, 6), 2);
        }

        private String hexToBinary(String hex) {
            return Arrays.stream(hex.split(""))
                .map(HEX_2_BIN::get)
                .collect(joining());
        }
    }
}
