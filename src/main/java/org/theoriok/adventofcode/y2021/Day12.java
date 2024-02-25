package org.theoriok.adventofcode.y2021;

import static org.apache.commons.lang3.StringUtils.isAllUpperCase;

import org.theoriok.adventofcode.Day;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Function;

public class Day12 implements Day<Integer, Integer> {

    public static final String START = "start";
    public static final String END = "end";

    private final List<String> input;

    public Day12(List<String> input) {
        this.input = input;
    }

    @Override
    public Integer firstMethod() {
        var caves = initializeCaves1();
        return caves.solve();
    }

    @Override
    public Integer secondMethod() {
        var caves = initializeCaves2();
        return caves.solve();
    }

    private Caves initializeCaves1() {
        var caves = new Caves(0);
        input.stream()
            .map(line -> line.split("-"))
            .forEach(caveNames -> caves.addCaves(caveNames[0], caveNames[1], name -> new Cave(name, isAllUpperCase(name))));
        return caves;
    }

    private Caves initializeCaves2() {
        var caves = new Caves(1);
        input.stream()
            .map(line -> line.split("-"))
            .forEach(caveNames -> caves.addCaves(caveNames[0], caveNames[1], name -> new Cave(name, isAllUpperCase(name))));
        return caves;
    }

    private static class Caves {
        private final Map<String, Cave> cavesByName = new HashMap<>();
        private int revisits;

        private Caves(int revisits) {
            this.revisits = revisits;
        }

        public void addCaves(String from, String to, Function<String, Cave> caveConstructor) {
            var fromCave = cavesByName.computeIfAbsent(from, caveConstructor);
            var toCave = cavesByName.computeIfAbsent(to, caveConstructor);
            fromCave.adjacentCaves.add(toCave);
            toCave.adjacentCaves.add(fromCave);
        }

        private int solve() {
            var start = cavesByName.get(START);
            var end = cavesByName.get(END);
            List<List<Cave>> paths = new ArrayList<>();
            Deque<Cave> path = new ArrayDeque<>();
            visit(start, path, end, paths);
            return paths.size();
        }

        private void visit(Cave cave, Deque<Cave> path, Cave end, List<List<Cave>> paths) {
            if ((cave.canVisit(revisits)) && !cave.isDeadEnd(revisits)) {
                path.addLast(cave);
                cave.visitsRemaining--;
                if (cave.visitsRemaining < 0 && !cave.isBigCave) {
                    revisits--;
                }
                for (Cave adjacentCave : cave.adjacentCaves) {
                    if (adjacentCave == end) {
                        addPath(path, end, paths);
                        path.removeLast(); //remove end
                    } else {
                        visit(adjacentCave, path, end, paths);
                    }
                }
                cave.visitsRemaining++;
                if (cave.visitsRemaining == 0 && !cave.isBigCave) {
                    revisits++;
                }
                path.removeLast(); //remove cave
            }
        }

        private void addPath(Deque<Cave> path, Cave end, List<List<Cave>> paths) {
            path.addLast(end);
            paths.add(path.stream().toList());
        }
    }

    private static final class Cave {
        private final String name;
        private final boolean isBigCave;
        private int visitsRemaining;
        private final List<Cave> adjacentCaves = new ArrayList<>();

        private Cave(String name, boolean isBigCave) {
            this.name = name;
            this.isBigCave = isBigCave;
            this.visitsRemaining = isBigCave ? 10000 : 1;
        }

        public boolean canVisit(int revisits) {
            return (isBigCave || visitsRemaining > 0 || (revisits > 0 && !Set.of(START, END).contains(name)));
        }

        public boolean isDeadEnd(int revisits) {
            return adjacentCaves.stream().noneMatch(cave -> cave.canVisit(revisits));
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Cave.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .toString();
        }
    }
}
