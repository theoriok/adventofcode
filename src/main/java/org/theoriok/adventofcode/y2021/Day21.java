package org.theoriok.adventofcode.y2021;

import org.theoriok.adventofcode.Day;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Day21 extends Day<Integer, Long> {

    public static final int STARTING_POSITION_INDEX = 28;
    public static final int TIMES_TO_ROLL = 3;

    public Day21(List<String> input) {
        super(input);
    }

    @Override
    public Integer firstMethod() {
        var player1 = new Player(1000, Integer.parseInt(input.get(0).substring(STARTING_POSITION_INDEX)));
        var player2 = new Player(1000, Integer.parseInt(input.get(1).substring(STARTING_POSITION_INDEX)));
        return play(player1, player2);
    }

    private Integer play(Player player1, Player player2) {
        DeterministicDie die = new DeterministicDie();
        int counter = 0;
        while (true) {
            var roll = die.rollTimes(TIMES_TO_ROLL);
            counter += TIMES_TO_ROLL;
            player1.moveSpaces(roll);
            if (player1.isWinner()) {
                return player2.score * counter;
            }
            roll = die.rollTimes(TIMES_TO_ROLL);
            counter += TIMES_TO_ROLL;
            player2.moveSpaces(roll);
            if (player2.isWinner()) {
                return player1.score * counter;
            }
        }
    }

    @Override
    public Long secondMethod() {
        var player1 = new MultiversalPlayer(21, Integer.parseInt(input.get(0).substring(STARTING_POSITION_INDEX)));
        var player2 = new MultiversalPlayer(21, Integer.parseInt(input.get(1).substring(STARTING_POSITION_INDEX)));
        return playMultiversal(player1, player2, new QuantumDie());
    }

    private Long playMultiversal(MultiversalPlayer player1, MultiversalPlayer player2, QuantumDie die) {
        while (player1.numberOfWinners() == 0 && player2.numberOfWinners() == 0) {
            var roll = die.rollTimes(TIMES_TO_ROLL);
            player1.addScores(roll);
            player2.multiplyUniverses((short)Math.pow(TIMES_TO_ROLL, QuantumDie.ROLL.length));
            roll = die.rollTimes(TIMES_TO_ROLL);
            player2.addScores(roll);
            player1.multiplyUniverses((short)Math.pow(TIMES_TO_ROLL, QuantumDie.ROLL.length));
        }
        return Math.max(player1.numberOfWinners(), player2.numberOfWinners());
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

        public Player copy() {
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

        private Map<Player, Long> players;

        private MultiversalPlayer(int winningScore, int position) {
            players = new HashMap<>(Map.of(new Player(winningScore, position), 1L));
        }

        public void addScores(Map<Short, Long> rolls) {
            Map<Player, Long> newPlayers = new HashMap<>();
            for (Map.Entry<Player, Long> playerAndCount : players.entrySet()) {
                for (Map.Entry<Short, Long> rollsAndCount : rolls.entrySet()) {
                    var newPlayer = playerAndCount.getKey().copy();
                    newPlayer.addScore(rollsAndCount.getKey());
                    var atomicLong = newPlayers.computeIfAbsent(newPlayer, any -> 1L);
                    newPlayers.put(newPlayer, atomicLong * playerAndCount.getValue() * rollsAndCount.getValue());
                }
            }
            players = newPlayers;
        }

        public long numberOfWinners() {
            return players.entrySet().stream()
                .filter(entry -> entry.getKey().isWinner())
                .mapToLong(Map.Entry::getValue)
                .sum();
        }

        public void multiplyUniverses(short universes) {
            players.replaceAll((player, count) -> count * universes);
        }
    }

    private static class DeterministicDie {
        private short roll = 0;

        private short roll() {
            roll = (short) (roll % 100 + 1);
            return roll;
        }

        public short rollTimes(int times) {
            short amount = 0;
            for (int i = 0; i < times; i++) {
                amount += roll();
            }
            return amount;
        }
    }

    private static class QuantumDie {

        public static final short[] ROLL = {1, 2, 3};

        private short[] roll() {
            return ROLL;
        }

        public Map<Short, Long> rollTimes(int times) {
            Map<Short, Long> amounts = new HashMap<>();
            for (int i = 0; i < times; i++) {
                var roll = roll();
                if (amounts.isEmpty()) {
                    for (short value : roll) {
                        amounts.put(value, 1L);
                    }
                } else {
                    Map<Short, Long> newAmounts = new HashMap<>();
                    for (short value : roll) {
                        for (Map.Entry<Short, Long> shortAtomicLongEntry : amounts.entrySet()) {
                            short newRoll = (short) (shortAtomicLongEntry.getKey() + value);
                            var amount = newAmounts.computeIfAbsent(newRoll, any -> 0L);
                            newAmounts.put(newRoll, amount + shortAtomicLongEntry.getValue());
                        }
                    }
                    amounts = newAmounts;
                }
            }
            return amounts;
        }
    }
}
