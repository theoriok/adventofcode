package org.theoriok.adventofcode.y2021;

import org.theoriok.adventofcode.Day;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21 implements Day<Integer, Long> {

    public static final int STARTING_POSITION_INDEX = 28;
    public static final int TIMES_TO_ROLL = 3;
    public static final int NUMBER_OF_SPACES_ON_BOARD = 10;
    public static final int WINNING_SCORE_1 = 1000;
    public static final int WINNING_SCORE_2 = 21;

    private final List<String> input;

    public Day21(List<String> input) {
        this.input = input;
    }

    @Override
    public Integer firstMethod() {
        var player1 = new Player(Integer.parseInt(input.getFirst().substring(STARTING_POSITION_INDEX)), 0);
        var player2 = new Player(Integer.parseInt(input.get(1).substring(STARTING_POSITION_INDEX)), 0);
        return play(player1, player2);
    }

    private Integer play(Player player1, Player player2) {
        var die = new DeterministicDie(100);
        var gameState = new GameState(player1, player2, true);
        while (!gameState.hasWinner(WINNING_SCORE_1)) {
            gameState = gameState.playTurn(die.rollTimes(TIMES_TO_ROLL));
        }
        return gameState.getLosingScore() * die.numberOfRolls;
    }

    @Override
    public Long secondMethod() {
        var die = new QuantumDie();
        var rolls = die.rollTimes(3);
        var player1 = new Player(Integer.parseInt(input.getFirst().substring(STARTING_POSITION_INDEX)), 0);
        var player2 = new Player(Integer.parseInt(input.get(1).substring(STARTING_POSITION_INDEX)), 0);
        Map<GameState, WinCounts> stateMemory = new HashMap<>();
        return playQuantum(rolls, new GameState(player1, player2, true), stateMemory).max();
    }

    private WinCounts playQuantum(Map<Short, Long> rolls, GameState gameState, Map<GameState, WinCounts> stateMemory) {
        if (gameState.hasWinner(WINNING_SCORE_2)) {
            return gameState.player1.score > gameState.player2.score ? new WinCounts(1L, 0L) : new WinCounts(0L, 1L);
        }
        if (stateMemory.containsKey(gameState)) {
            return stateMemory.get(gameState);
        }
        var winCounts = rolls.entrySet().stream()
            .map(entry -> playQuantum(rolls, gameState.playTurn(entry.getKey()), stateMemory).times(entry.getValue()))
            .reduce(WinCounts::plus)
            .orElseThrow();
        stateMemory.put(gameState, winCounts);
        return winCounts;
    }

    private record Player(
        int position,
        int score
    ) {

        public Player moveSpaces(short spaces) {
            var newPosition = ((this.position + spaces - 1) % NUMBER_OF_SPACES_ON_BOARD) + 1;
            return new Player(newPosition, score + newPosition);
        }

        public boolean isWinner(int winningScore) {
            return score >= winningScore;
        }
    }

    private record GameState(Player player1, Player player2, boolean isPlayer1Turn) {
        public GameState playTurn(short spaces) {
            return new GameState(
                isPlayer1Turn ? player1.moveSpaces(spaces) : player1,
                !isPlayer1Turn ? player2.moveSpaces(spaces) : player2,
                !isPlayer1Turn
            );
        }

        public boolean hasWinner(int winningScore) {
            return player1.isWinner(winningScore) || player2.isWinner(winningScore);
        }

        public int getLosingScore() {
            return Math.min(player1.score, player2.score);
        }
    }

    private record WinCounts(long player1Count, long player2Count) {
        public WinCounts plus(WinCounts other) {
            return new WinCounts(player1Count + other.player1Count, player2Count + other.player2Count);
        }

        public WinCounts times(long other) {
            return
                new WinCounts(player1Count * other, player2Count * other);
        }

        public Long max() {
            return Math.max(player1Count, player2Count);
        }
    }

    private static class DeterministicDie {
        private short roll = 0;
        private int numberOfRolls = 0;
        private final int maxValue;

        private DeterministicDie(int maxValue) {
            this.maxValue = maxValue;
        }

        private short roll() {
            roll = (short) (roll % maxValue + 1);
            numberOfRolls++;
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

        static final short[] ROLL = {1, 2, 3};

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
