package org.theoriok.adventofcode.y2023;

import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.List;

public class Day13 implements Day<Long, Long> {

    private final List<Grid> grids = new ArrayList<>();

    public Day13(List<String> input) {
        int start = 0;
        while (start < input.size()) {
            int end = input.subList(start, input.size()).indexOf("") + start;
            grids.add(Grid.fromList(input.subList(start, end)));
            start = end + 1;
        }
    }

    private record Grid(List<String> rows, List<String> cols) {

        public static Grid fromList(List<String> lines) {
            List<String> cols = new ArrayList<>();
            for (int i = 0; i < lines.getFirst().length(); i++) {
                StringBuilder sb = new StringBuilder();
                for (String line : lines) {
                    sb.append(line.charAt(i));
                }
                cols.add(sb.toString());
            }
            return new Grid(lines, cols);
        }

        public long whereMirror() {
            for (int i = 0; i < cols.size() - 1; i++) {
                if (mirrorsAt(i, cols)) {
                    return i + 1;
                }
            }
            for (int i = 0; i < rows.size() - 1; i++) {
                if (mirrorsAt(i, rows)) {
                    return (i + 1) * 100L;
                }
            }
            return 0;
        }

        private boolean mirrorsAt(int i, List<String> list) {
            int lower = i;
            int higher = i + 1;
            while (lower >= 0 && higher < list.size()) {
                if (!list.get(lower).equals(list.get(higher))) {
                    return false;
                }
                lower--;
                higher++;
            }
            return true;
        }
    }

    @Override
    public Long firstMethod() {

        return grids.stream()
            .mapToLong(Grid::whereMirror)
            .sum();
    }

    @Override
    public Long secondMethod() {
        return 0L;
    }
}
