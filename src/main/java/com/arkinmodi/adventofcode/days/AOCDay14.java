package com.arkinmodi.adventofcode.days;

import com.arkinmodi.adventofcode.AOCDay;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AOCDay14 implements AOCDay {

    @Override
    public int part1(final List<String> input) {
        final List<List<Character>> dish =
                input.stream()
                        .map(
                                line ->
                                        line.chars()
                                                .mapToObj(c -> (char) c)
                                                .collect(Collectors.toList()))
                        .toList();

        int totalLoad = 0;
        for (int r = 0; r < dish.size(); r++) {
            for (int c = 0; c < dish.get(r).size(); c++) {
                if (dish.get(r).get(c).equals('O')) {
                    int newRow = r;
                    do {
                        newRow--;
                    } while (newRow >= 0 && dish.get(newRow).get(c).equals('.'));

                    dish.get(r).set(c, '.');
                    dish.get(newRow + 1).set(c, 'O');
                    totalLoad += dish.size() - (newRow + 1);
                }
            }
        }
        return totalLoad;
    }

    @Override
    public int part2(final List<String> input) {
        final int CYCLES = 1_000_000_000;

        List<List<Character>> dish =
                input.stream()
                        .map(
                                line ->
                                        line.chars()
                                                .mapToObj(c -> (char) c)
                                                .collect(Collectors.toList()))
                        .toList();

        final int ROWS = dish.size();
        final int COLS = dish.getFirst().size();

        final Set<List<List<Character>>> seen = new HashSet<>();
        final List<List<List<Character>>> cache = new ArrayList<>();

        for (int i = 0; i < CYCLES; i++) {
            if (seen.contains(dish)) {
                break;
            }
            final List<List<Character>> copy =
                    dish.stream().map(row -> row.stream().toList()).toList();
            seen.add(copy);
            cache.add(copy);

            // north
            for (int r = 0; r < ROWS; r++) {
                for (int c = 0; c < COLS; c++) {
                    if (dish.get(r).get(c).equals('O')) {
                        int newRow = r;
                        do {
                            newRow--;
                        } while (0 <= newRow && dish.get(newRow).get(c).equals('.'));
                        dish.get(r).set(c, '.');
                        dish.get(newRow + 1).set(c, 'O');
                    }
                }
            }

            // west
            for (int r = 0; r < ROWS; r++) {
                for (int c = 0; c < COLS; c++) {
                    if (dish.get(r).get(c).equals('O')) {
                        int newCol = c;
                        do {
                            newCol--;
                        } while (0 <= newCol && dish.get(r).get(newCol).equals('.'));
                        dish.get(r).set(c, '.');
                        dish.get(r).set(newCol + 1, 'O');
                    }
                }
            }

            // south
            for (int r = ROWS - 1; r >= 0; r--) {
                for (int c = 0; c < COLS; c++) {
                    if (dish.get(r).get(c).equals('O')) {
                        int newRow = r;
                        do {
                            newRow++;
                        } while (newRow < ROWS && dish.get(newRow).get(c).equals('.'));
                        dish.get(r).set(c, '.');
                        dish.get(newRow - 1).set(c, 'O');
                    }
                }
            }

            // east
            for (int r = 0; r < ROWS; r++) {
                for (int c = COLS - 1; c >= 0; c--) {
                    if (dish.get(r).get(c).equals('O')) {
                        int newCol = c;
                        do {
                            newCol++;
                        } while (newCol < COLS && dish.get(r).get(newCol).equals('.'));
                        dish.get(r).set(c, '.');
                        dish.get(r).set(newCol - 1, 'O');
                    }
                }
            }
        }

        final int cycleStart = cache.indexOf(dish);
        final int cycleEnd = cache.size();
        final List<List<Character>> finalDish =
                cache.get(cycleStart + (CYCLES - cycleStart) % (cycleEnd - cycleStart));

        return IntStream.range(0, ROWS)
                .map(
                        r ->
                                IntStream.range(0, COLS)
                                        .filter(c -> finalDish.get(r).get(c).equals('O'))
                                        .map(c -> ROWS - r)
                                        .sum())
                .sum();
    }
}
