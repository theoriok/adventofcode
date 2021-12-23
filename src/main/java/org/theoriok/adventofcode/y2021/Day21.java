package org.theoriok.adventofcode.y2021;

import org.theoriok.adventofcode.Day;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Day21 extends Day<Integer, Long> {

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

    @Override
    public Long secondMethod() {
        return super.secondMethod();
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

        public Player clone() {
            var newPlayer = new Player(position, winningScore);
            newPlayer.score = score;
            return newPlayer;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Player player) {
                return score == player.score && position == player.position;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(score, position);
        }
    }

    private static class MultiversalPlayer {

        private final Map<Player, AtomicLong> players;

        private MultiversalPlayer(int winningScore, int position) {
            players = Map.of(new Player(winningScore, position), new AtomicLong(1));
        }

        public void addScores(Map<Short, AtomicLong> rolls) {

        }

        public long numberOfWinners() {
            return players.entrySet().stream()
                .filter(entry ->  entry.getKey().isWinner())
                .mapToLong(entry -> entry.getValue().get())
                .sum();
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

    private static class QuantumDie {
        private short[] roll() {
            return new short[] {1, 2, 3};
        }

        public Map<Short, AtomicLong> roll(int times) {
            Map<Short, AtomicLong> amounts = new HashMap<>();
            for (int i = 0; i < times; i++) {
                var roll = roll();
                if (amounts.isEmpty()) {
                    for (int j = 0; j < roll.length; j++) {
                        amounts.put(roll[j], new AtomicLong(1));
                    }
                } else {
                    Map<Short, AtomicLong> newAmounts = new HashMap<>();
                    for (int j = 0; j < roll.length; j++) {
                        for (Map.Entry<Short, AtomicLong> shortAtomicLongEntry : amounts.entrySet()) {
                            short newRoll = (short) (shortAtomicLongEntry.getKey() + roll[j]);
                            newAmounts.computeIfAbsent(newRoll, any -> new AtomicLong()).addAndGet(shortAtomicLongEntry.getValue().get());
                        }
                    }
                    amounts = newAmounts;
                }
            }
            return amounts;
        }
    }
}
