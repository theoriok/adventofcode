package org.theoriok.adventofcode.y2021;

import org.theoriok.adventofcode.Day;

import java.util.List;

public class Day21 extends Day<Integer, Integer> {

    public static final int STARTING_POSITION_INDEX = 28;

    public Day21(List<String> input) {
        super(input);
    }

    @Override
    public Integer firstMethod() {
        var player1 = new Player(Integer.parseInt(input.get(0).substring(STARTING_POSITION_INDEX)));
        var player2 = new Player(Integer.parseInt(input.get(1).substring(STARTING_POSITION_INDEX)));
        return play(player1, player2, new DeterministicDie(), 1000);
    }

    private Integer play(Player player1, Player player2, Die die, int scoreToWin) {
        int counter = 0;
        while (true) {
            var roll = die.roll(3);
            counter += 3;
            player1.moveSpaces(roll);
            if (player1.score >= scoreToWin) {
                return player2.score * counter;
            }
            roll = die.roll(3);
            counter += 3;
            player2.moveSpaces(roll);
            if (player2.score >= 1000) {
                return player1.score * counter;
            }
        }
    }

    private static class Player {
        private int score = 0;
        private int position;

        private Player(int position) {
            this.position = position;
        }

        public void moveSpaces(int spaces) {
            position = ((position + spaces - 1) % 10) + 1;
            addScore(position);
        }

        public void addScore(int scoreToAdd) {
            score += scoreToAdd;
        }
    }

    private interface Die {
        int roll();

        default int roll(int times) {
            var amount = 0;
            for (int i = 0; i < times; i++) {
                amount += roll();
            }
            return amount;
        }
    }

    private static class DeterministicDie implements Die {
        private int roll = 0;

        @Override
        public int roll() {
            roll = roll % 100 + 1;
            return roll;
        }
    }
}
