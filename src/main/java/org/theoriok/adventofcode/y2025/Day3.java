package org.theoriok.adventofcode.y2025;

import static org.theoriok.adventofcode.util.Utils.splitToList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.List;

public class Day3 implements Day<Integer, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day3.class);
    private final List<BatteryBank> batteryBanks;

    public Day3(List<String> input) {
        batteryBanks = input.stream()
            .map(BatteryBank::from)
            .toList();
    }

    @Override
    public Integer firstMethod() {
        return batteryBanks.stream()
            .mapToInt(BatteryBank::joltage)
            .sum();
    }

    @Override
    public Long secondMethod() {
        return batteryBanks.stream()
            .mapToLong(BatteryBank::dangerousJoltage)
            .sum();
    }

    private record BatteryBank(List<Battery> batteries){
        static BatteryBank from(String input) {
            return new BatteryBank(splitToList(input, "", Battery::from));
        }

        public int joltage() {
            var first = batteries.subList(0, batteries.size() -1).stream().max(Battery::compare).orElseThrow();
            var second = batteries.subList(batteries.indexOf(first) + 1, batteries.size()).stream().max(Battery::compare).orElseThrow();
            return first.joltage * 10 + second.joltage;
        }

        public long dangerousJoltage() {
            var result = new ArrayList<Battery>();
            int remaining = 12;
            int start = 0;

            while (remaining > 0) {
                int maxIdx = start;
                for (int i = start; i <= batteries.size() - remaining; i++) {
                    if (batteries.get(i).compare(batteries.get(maxIdx)) > 0) maxIdx = i;
                }
                result.add(batteries.get(maxIdx));
                start = maxIdx + 1;
                remaining--;
            }
            var joltage = 0L;
            for (var i = 0; i < result.size(); i++) {
                joltage += result.get(i).joltage * Math.powExact(10L, 11 - i);
            }
            return joltage;
        }
    }

    private record Battery(Short joltage) {
        static Battery from(String input) {
            return new Battery(Short.parseShort(input));
        }

        public int compare(Battery battery) {
            return joltage.compareTo(battery.joltage);
        }
    }
}
