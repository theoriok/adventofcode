package org.theoriok.adventofcode.y2023;

import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            return whereMirror(cols, rows);
        }

        private long whereMirror(List<String> forCols, List<String> forRows) {
            for (int i = 0; i < forCols.size() - 1; i++) {
                if (mirrorsAt(i, forCols)) {
                    return i + 1;
                }
            }
            for (int i = 0; i < forRows.size() - 1; i++) {
                if (mirrorsAt(i, forRows)) {
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

        public long whereSmudgedMirror() {
            var originalMirror = whereMirror();
            for (int i = 0; i < cols.size(); i++) {
                for (int j = 0; j < rows.size(); j++) {
                    List<String> colsCopy = copyList(cols);
                    colsCopy.set(i, flip(j, cols.get(i)));
                    List<String> rowsCopy = copyList(rows);
                    rowsCopy.set(j, flip(i, rows.get(j)));
                    long mirror = whereMirror(colsCopy, rowsCopy);
                    if (mirror != 0 && mirror != originalMirror) {
                        return originalMirror;
                    }
                }
            }
            return 0;
        }

        private List<String> copyList(List<String> list) {
            return list.stream()
                .map(line -> String.copyValueOf(line.toCharArray()))
                .collect(Collectors.toCollection(ArrayList::new));
        }

        private String flip(int characterIndex, String string) {
            char charAt = string.charAt(characterIndex);
            char newChar = charAt == '.' ? '#' : '.';
            char[] charArray = string.toCharArray();
            charArray[characterIndex] = newChar;
            return String.copyValueOf(charArray);
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
        return grids.stream()
            .mapToLong(Grid::whereSmudgedMirror)
            .sum();
    }
}
