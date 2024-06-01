package org.theoriok.adventofcode.y2015;

import org.apache.commons.lang3.StringUtils;
import org.theoriok.adventofcode.Day;

import java.util.List;

public class Day11 implements Day<String, String> {

    public static final int LENGTH_OF_STRAIGHT = 3;
    public static final int NUMBER_OF_DOUBLES_WANTED = 2;
    private final String line;

    public Day11(List<String> input) {
        line = input.getFirst();
    }

    @Override
    public String firstMethod() {
        return getNextPasswordAfter(line);
    }

    private String getNextPasswordAfter(String oldPassword) {
        String newPassword = oldPassword;
        do {
            newPassword = increment(newPassword);
        } while (!isValid(newPassword));

        return newPassword;
    }

    private String increment(String newPassword) {
        return incrementCharAt(newPassword, newPassword.length() - 1);
    }

    private String incrementCharAt(String newPassword, int position) {
        StringBuilder stringBuilder = new StringBuilder(newPassword);
        char oldChar = newPassword.charAt(position);
        char newChar = (char) (oldChar + 1);
        if (newChar > 'z') {
            stringBuilder.setCharAt(position, 'a');
            return incrementCharAt(stringBuilder.toString(), position - 1);
        }
        stringBuilder.setCharAt(position, newChar);
        return stringBuilder.toString().toLowerCase();
    }

    private boolean isValid(String newPassword) {
        return doesNotContainForbiddenCharacters(newPassword)
                && containsStraightOf(newPassword)
                && containsAtLeastUniqueDoubles(newPassword);
    }

    private boolean containsStraightOf(String newPassword) {
        for (int i = 0; i <= 26 - LENGTH_OF_STRAIGHT; i++) {
            StringBuilder straight = new StringBuilder();
            for (int j = 0; j < LENGTH_OF_STRAIGHT; j++) {
                straight.append((char) ('a' + i + j));
            }
            if (newPassword.contains(straight.toString())) {
                return true;
            }
        }
        return false;
    }

    private boolean containsAtLeastUniqueDoubles(String newPassword) {
        int numberOfDoublesFound = 0;
        for (int i = 0; i < 26; i++) {
            String doubleChar = "%1$s%1$s".formatted((char) ('a' + i));
            if (newPassword.contains(doubleChar)) {
                numberOfDoublesFound++;
                if (numberOfDoublesFound == NUMBER_OF_DOUBLES_WANTED) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean doesNotContainForbiddenCharacters(String newPassword) {
        return StringUtils.containsNone(newPassword, 'i', 'o', 'l');
    }

    @Override
    public String secondMethod() {
        return getNextPasswordAfter(getNextPasswordAfter(line));
    }
}
