package org.theoriok.adventofcode.y2021;

import org.apache.commons.lang3.ArrayUtils;
import org.theoriok.adventofcode.Day;

import java.util.Arrays;
import java.util.List;

public class Day21 extends Day<Integer, Integer> {

    public static final int STARTING_POSITION_INDEX = 28;

    public Day21(List<String> input) {
        super(input);
    }

    @Override
    public Integer firstMethod() {
        var player1 = new Player(1000, Integer.parseInt(input.get(0).substring(STARTING_POSITION_INDEX)));
        var player2 = new Player(1000, Integer.parseInt(input.get(1).substring(STARTING_POSITION_INDEX)));
        return play(player1, player2, new DeterministicDie());
    }

    private Integer play(Player player1, Player player2, DeterministicDie die) {
        int counter = 0;
        while (true) {
            var roll = die.roll(3);
            counter += 3;
            player1.moveSpaces(roll);
            if (player1.isWinner()) {
                return player2.score * counter;
            }
            roll = die.roll(3);
            counter += 3;
            player2.moveSpaces(roll);
            if (player2.isWinner()) {
                return player1.score * counter;
            }
        }
    }

    private static class Player {
        private int score = 0;
        private int position;
        private final int winningScore;

        private Player(int winningScore, int position) {
            this.position = position;
            this.winningScore = winningScore;
        }

        public void moveSpaces(short spaces) {
            position = ((position + spaces - 1) % 10) + 1;
            addScore(position);
        }

        public void addScore(int scoreToAdd) {
            score += scoreToAdd;
        }

        public boolean isWinner() {
            return score >= winningScore;
        }
    }

    private static class MultiversalPlayer extends Player {

        private MultiversalPlayer(int winningScore, int position) {
            super(winningScore, position);
        }
    }

    private static class DeterministicDie {
        private short roll = 0;

        private short roll() {
            roll = (short) (roll % 100 + 1);
            return roll;
        }

        public short roll(int times) {
            short amount = 0;
            for (int i = 0; i < times; i++) {
                amount += roll();
            }
            return amount;
        }
    }

     static class QuantumDie {
        private short[] roll() {
            return new short[] {1, 2, 3};
        }

        public short[] roll(int times) {
            short[] amounts = {0};
            for (int i = 0; i < times; i++) {
                var roll = roll();
                var newAmounts = new short[amounts.length * roll.length];
                for (int j = 0; j < roll.length; j++) {
                    for (int k = 0; k < amounts.length; k++) {
                        newAmounts[j*amounts.length + k] = (short) (amounts[k] + roll[j]);
                    }
                }
                amounts = newAmounts;
            }
            return amounts;
        }
    }
}
