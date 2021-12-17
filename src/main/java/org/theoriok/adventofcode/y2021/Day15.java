package org.theoriok.adventofcode.y2021;

import org.theoriok.adventofcode.Day;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Day15 extends Day {

    private final Node[][] grid;

    public Day15(List<String> input) {
        super(input);
        grid = new Node[input.size()][input.size()];
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.size(); j++) {
                var h = grid.length * grid.length - (i * j);
                var weight = Short.parseShort(input.get(i).substring(j, j + 1));
                grid[i][j] = new Node(h, weight);
            }
        }
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                var node = grid[i][j];
                if (i > 0) {
                    var neighbour = grid[i - 1][j];
                    node.addBranch(neighbour.weight, neighbour);
                }
                if (j > 0) {
                    var neighbour = grid[i][j - 1];
                    node.addBranch(neighbour.weight, neighbour);
                }
                if (i < grid.length - 1) {
                    var neighbour = grid[i + 1][j];
                    node.addBranch(neighbour.weight, neighbour);
                }
                if (j < grid.length - 1) {
                    var neighbour = grid[i][j + 1];
                    node.addBranch(neighbour.weight, neighbour);
                }
            }
        }
    }

    @Override
    public long firstMethod() {
        var node = aStar(grid[0][0], grid[grid.length - 1][grid.length - 1]);
        var count = node.weight;
        while (node.parent != null) {
            node = node.parent;
            count+=node.weight;
        }
        return count;
    }

    private Node aStar(Node start, Node target) {
        PriorityQueue<Node> closedList = new PriorityQueue<>();
        PriorityQueue<Node> openList = new PriorityQueue<>();

        start.f = start.g + start.calculateHeuristic(target);
        openList.add(start);

        while (!openList.isEmpty()) {
            Node n = openList.peek();
            if (n == target) {
                return n;
            }

            for (Node.Edge edge : n.neighbors) {
                Node m = edge.node;
                double totalWeight = n.g + edge.weight;

                if (!openList.contains(m) && !closedList.contains(m)) {
                    m.parent = n;
                    n.neighbors.remove(m);
                    m.g = totalWeight;
                    m.f = m.g + m.calculateHeuristic(target);
                    openList.add(m);
                } else {
                    if (totalWeight < m.g) {
                        m.parent = n;
                        n.neighbors.remove(m);
                        m.g = totalWeight;
                        m.f = m.g + m.calculateHeuristic(target);

                        if (closedList.contains(m)) {
                            closedList.remove(m);
                            openList.add(m);
                        }
                    }
                }
            }

            openList.remove(n);
            closedList.add(n);
        }
        return null;
    }

    private static class Node implements Comparable<Node> {
        // Id for readability of result purposes
        private static int idCounter = 0;
        public int id;

        // Parent in the path
        public Node parent = null;

        public List<Edge> neighbors;

        // Evaluation functions
        public double f = Double.MAX_VALUE;
        public double g = Double.MAX_VALUE;
        // Hardcoded heuristic
        public double h;
        public short weight;

        Node(double h, short weight) {
            this.h = h;
            this.weight = weight;
            this.id = idCounter++;
            this.neighbors = new ArrayList<>();
        }

        @Override
        public int compareTo(Node n) {
            return Double.compare(this.f, n.f);
        }

        public static class Edge {
            Edge(int weight, Node node) {
                this.weight = weight;
                this.node = node;
            }

            public int weight;
            public Node node;
        }

        public void addBranch(int weight, Node node) {
            Edge newEdge = new Edge(weight, node);
            neighbors.add(newEdge);
        }

        public double calculateHeuristic(Node target) {
            return this.h;
        }
    }
}