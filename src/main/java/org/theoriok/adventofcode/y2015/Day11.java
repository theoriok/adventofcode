package org.theoriok.adventofcode.y2015;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theoriok.adventofcode.Day;
import org.theoriok.adventofcode.GenericDay;

import java.util.List;

public class Day11 implements Day<String, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(Day11.class);

    private final String line;

    public Day11(List<String> input) {
        line = input.getFirst();
    }

    @Override
    public String firstMethod() {
        String newPassword = line;
        do {
            newPassword = increment(newPassword);
            logger.info("New password: {}", newPassword);
        } while (!isValid(newPassword));

        return newPassword;
    }

    private String increment(String newPassword) {
        return incrementCharAt(newPassword, newPassword.length()-1);
    }

    private String incrementCharAt(String newPassword, int position) {
        StringBuilder stringBuilder = new StringBuilder(newPassword);
        char oldChar = newPassword.charAt(position);
        char newChar = (char) (oldChar + 1);
        if (newChar >'z') {
            stringBuilder.setCharAt(position, 'a');
            return incrementCharAt(stringBuilder.toString(), position - 1);
        }
        stringBuilder.setCharAt(position, newChar);
        return stringBuilder.toString().toLowerCase();
    }

    private boolean isValid(String newPassword) {
        return containsStraightOf(3, newPassword)
                && containsAtLeastUniqueDoubles(2, newPassword)
                && doesNotContainForbiddenCharacters(newPassword);
    }

    private boolean containsStraightOf(int lengthOfStraight, String newPassword) {
        for (int i = 0; i < 26 - lengthOfStraight; i++) {
            StringBuilder straight = new StringBuilder();
            for (int j = 0; j < lengthOfStraight; j++) {
                straight.append((char) ('a' + i + j));
            }
            if (newPassword.contains(straight.toString())) {
                return true;
            }
        }
        logger.warn("Fails containsStraightOf");
        return false;
    }

    private boolean containsAtLeastUniqueDoubles(int numberOfDoublesWanted, String newPassword) {
        int numberOfDoublesFound = 0;
        for (int i = 0; i < 26; i++) {
            String doubleChar = "%1$s%1$s".formatted((char) ('a' + i));
            if (newPassword.contains(doubleChar)) {
                numberOfDoublesFound++;
                if (numberOfDoublesFound == numberOfDoublesWanted) {
                    return true;
                }
            }
        }
        logger.warn("Fails containsAtLeastUniqueDoubles");
        return false;
    }

    private boolean doesNotContainForbiddenCharacters(String newPassword) {
        boolean containsNone = StringUtils.containsNone(newPassword, 'i', 'o', 'l');
        if(!containsNone) {
            logger.warn("Fails doesNotContainForbiddenCharacters");
        }
        return containsNone;
    }

    @Override
    public Integer secondMethod() {
        return 0;
    }
}
