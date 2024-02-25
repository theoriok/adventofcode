package org.theoriok.adventofcode.y2022;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.math.NumberUtils;
import org.theoriok.adventofcode.Day;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;

public class Day5 implements Day<String, String> {

    public static final Pattern PATTERN = Pattern.compile("move (\\d+) from (\\d) to (\\d)", Pattern.MULTILINE);
    private final List<String> input;

    private List<Command> commands;
    private Map<Integer, Tower> towersByNumber;

    public Day5(List<String> input) {
        this.input = input;
    }

    private void init(List<String> input) {
        towersByNumber = getTowers(input);
        commands = getCommands(input);
    }

    private Map<Integer, Tower> getTowers(List<String> input) {
        var towerStrings = Lists.reverse(input.subList(0, input.indexOf("")));
        List<Tower> towers = Arrays.stream(towerStrings.getFirst().split(" {2}"))
            .map(String::trim)
            .filter(NumberUtils::isDigits)
            .map(Integer::parseInt)
            .map(number -> new Tower(number, new ArrayDeque<>()))
            .toList();
        for (int i = 1; i < towerStrings.size(); i++) {
            var towerString = towerStrings.get(i);
            for (int j = 0; j < towers.size(); j++) {
                var index = 1 + 4 * j;
                if (towerString.length() > index && towerString.charAt(index) != ' ') {
                    towers.get(j).crates.addFirst(towerString.substring(index, index + 1));
                }
            }
        }
        return towers.stream().collect(toMap(Tower::number, Function.identity()));
    }

    private List<Command> getCommands(List<String> input) {
        return input.subList(input.indexOf("") + 1, input.size()).stream()
            .map(this::toCommand)
            .filter(Objects::nonNull)
            .toList();
    }

    private Command toCommand(String line) {
        var matcher = PATTERN.matcher(line);
        if (matcher.matches()) {
            return new Command(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
        }
        return null;
    }

    @Override
    public String firstMethod() {
        init(input);
        commands.forEach(command -> command.execute9000(towersByNumber));
        return towersByNumber.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .map(Map.Entry::getValue)
            .map(Tower::crates)
            .map(Deque::getFirst)
            .collect(joining());
    }

    @Override
    public String secondMethod() {
        init(input);
        commands.forEach(command -> command.execute9001(towersByNumber));
        return towersByNumber.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .map(Map.Entry::getValue)
            .map(Tower::crates)
            .map(Deque::getFirst)
            .collect(joining());
    }

    private record Command(
        int numberToMove,
        int moveFrom,
        int moveTo
    ) {
        public void execute9000(Map<Integer, Tower> towersByNumber) {
            var towerFrom = towersByNumber.get(moveFrom);
            var towerTo = towersByNumber.get(moveTo);
            for (int i = 0; i < numberToMove; i++) {
                towerTo.crates.addFirst(towerFrom.crates.pop());
            }
        }

        public void execute9001(Map<Integer, Tower> towersByNumber) {
            var towerFrom = towersByNumber.get(moveFrom);
            var towerTo = towersByNumber.get(moveTo);
            var list = new ArrayDeque<String>();
            for (int i = 0; i < numberToMove; i++) {
                list.addFirst(towerFrom.crates.pop());
            }
            for (int i = 0; i < numberToMove; i++) {
                towerTo.crates.addFirst(list.pop());
            }
        }
    }

    private record Tower(
        int number,
        Deque<String> crates
    ) {
    }
}
