package org.theoriok.adventofcode.y2015;

import org.theoriok.adventofcode.Day;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day25 implements Day<Long, Integer> {

    private final String line;
    private static final String PATTERN = "To continue, please consult the code grid in the manual.  Enter the code at row (\\d+), column (\\d+).";

    public Day25(List<String> input) {
        line = input.getFirst();
    }

    @Override
    public Long firstMethod() {
        long value = 20151125;
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            int rowToFind = Integer.parseInt(matcher.group(1));
            int columnToFind = Integer.parseInt(matcher.group(2));
            int row = 1;
            int column = 1;
            while (!(row == rowToFind && column == columnToFind)) {
                if (row == 1) {
                    row = column + 1;
                    column = 1;
                } else {
                    row--;
                    column++;
                }
                value = (value * 252533) % 33554393;
            }
        }

        return value;
    }

    @Override
    public Integer secondMethod() {
        return 0;
    }
}
