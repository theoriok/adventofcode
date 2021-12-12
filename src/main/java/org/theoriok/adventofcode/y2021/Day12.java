package org.theoriok.adventofcode.y2021;

import static org.apache.commons.lang3.StringUtils.isAllUpperCase;

import org.theoriok.adventofcode.Day;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Function;

public class Day12 extends Day {

    public Day12(List<String> input) {
        super(input);
    }

    @Override
    public long firstMethod() {
        var caves = initializeCaves();
        var start = caves.cavesByName.get("start");
        var end = caves.cavesByName.get("end");
        List<List<Cave>> paths = new ArrayList<>();
        Deque<Cave> path = new ArrayDeque<>();
        visit(start, path, end, paths);
        return paths.size();
    }

    private void visit(Cave cave, Deque<Cave> path, Cave end, List<List<Cave>> paths) {
        if (cave.canVisit() && ! cave.isDeadEnd()) {
            path.addLast(cave);
            cave.isVisited = true;
            for (Cave adjacentCave : cave.adjacentCaves) {
                if (adjacentCave == end) {
                    addPath(path, end, paths);
                    path.removeLast(); //remove end
                } else {
                    visit(adjacentCave, path, end, paths);
                }
            }
            cave.isVisited = false;
            path.removeLast(); //remove cave
        }
    }

    private void addPath(Deque<Cave> path, Cave end, List<List<Cave>> paths) {
        path.addLast(end);
        paths.add(path.stream().toList());
    }

    private Caves initializeCaves() {
        var caves = new Caves();
        input.stream()
            .map(line -> line.split("-"))
            .forEach(caveNames -> caves.addCaves(caveNames[0], caveNames[1]));
        return caves;
    }

    private static class Caves {
        private Map<String, Cave> cavesByName = new HashMap<>();

        public void addCaves(String from, String to) {
            Function<String, Cave> caveConstructor = name -> new Cave(name, isAllUpperCase(name));
            var fromCave = cavesByName.computeIfAbsent(from, caveConstructor);
            var toCave = cavesByName.computeIfAbsent(to, caveConstructor);
            fromCave.adjacentCaves.add(toCave);
            toCave.adjacentCaves.add(fromCave);
        }
    }

    private static final class Cave {
        private final String name;
        private final boolean isBigCave;
        private boolean isVisited;
        private List<Cave> adjacentCaves = new ArrayList<>();

        private Cave(String name, boolean isBigCave) {
            this.name = name;
            this.isBigCave = isBigCave;
        }

        public boolean canVisit() {
            return (isBigCave || !isVisited);
        }

        public boolean isDeadEnd() {
            return adjacentCaves.stream().noneMatch(Cave::canVisit);
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Cave.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .toString();
        }
    }
}
