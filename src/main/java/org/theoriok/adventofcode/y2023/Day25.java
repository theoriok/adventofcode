package org.theoriok.adventofcode.y2023;

import org.jgrapht.alg.StoerWagnerMinimumCut;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.theoriok.adventofcode.Day;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static org.theoriok.adventofcode.util.Utils.splitToList;

public class Day25 implements Day<Integer, Long> {

    private final List<String> input;

    public Day25(List<String> input) {
        this.input = input;
    }

    @Override
    public Integer firstMethod() {
        var graph = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        input.forEach(line -> {
            String[] split = line.split(": ");
            String name = split[0];
            graph.addVertex(name);

            List<String> others = getOthers(split[1]);
            others.forEach(other -> {
                graph.addVertex(other);
                graph.addEdge(name, other);
            });
        });
        Set<String> minCut = new StoerWagnerMinimumCut<>(graph).minCut();
        return minCut.size() * (graph.vertexSet().size() - minCut.size());
    }

    private List<String> getOthers(String inputs) {
        return splitToList(inputs, " ", Function.identity());
    }
}
