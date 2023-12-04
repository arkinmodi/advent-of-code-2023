package com.arkinmodi.adventofcode.days;

import com.arkinmodi.adventofcode.AOCDay;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class AOCDay04 implements AOCDay {

    @Override
    public int part1(final List<String> input) {
        int totalPoints = 0;
        for (final String card : input) {
            final String[] numbers = card.split(": ")[1].split(" \\| ");

            final List<Integer> winningNumbers =
                    Arrays.stream(numbers[0].split(" "))
                            .filter(s -> !s.isEmpty())
                            .map(Integer::valueOf)
                            .toList();

            final List<Integer> gameNumbers =
                    Arrays.stream(numbers[1].split(" "))
                            .filter(s -> !s.isEmpty())
                            .map(Integer::valueOf)
                            .toList();

            totalPoints +=
                    gameNumbers.stream()
                            .filter(winningNumbers::contains)
                            .reduce(0, (acc, _n) -> acc == 0 ? 1 : acc * 2);
        }
        return totalPoints;
    }

    @Override
    public int part2(final List<String> input) {
        final Map<Integer, Integer> dp = new HashMap<>();
        return IntStream.rangeClosed(1, input.size()).map(i -> dfs(i, input, dp)).sum();
    }

    private int dfs(final int gameId, final List<String> input, final Map<Integer, Integer> dp) {
        if (dp.containsKey(gameId)) {
            return dp.get(gameId);
        }

        final String[] numbers = input.get(gameId - 1).split(": ")[1].split(" \\| ");

        final List<Integer> winningNumbers =
                Arrays.stream(numbers[0].split(" "))
                        .filter(s -> !s.isEmpty())
                        .map(Integer::valueOf)
                        .toList();

        final List<Integer> gameNumbers =
                Arrays.stream(numbers[1].split(" "))
                        .filter(s -> !s.isEmpty())
                        .map(Integer::valueOf)
                        .toList();

        final int gamePoints =
                gameNumbers.stream()
                        .filter(winningNumbers::contains)
                        .reduce(0, (acc, _n) -> acc + 1);

        final int numCards =
                1
                        + IntStream.range(gameId + 1, gameId + 1 + gamePoints)
                                .map(i -> dfs(i, input, dp))
                                .sum();

        dp.put(gameId, numCards);
        return numCards;
    }
}
