package com.arkinmodi.adventofcode.days;

import com.arkinmodi.adventofcode.AOCDay;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AOCDay09 implements AOCDay {

    @Override
    public int part1(final List<String> input) {
        int sum = 0;
        for (final String history : input) {
            final List<List<Integer>> differences = new ArrayList<>();
            differences.addLast(
                    Arrays.stream(history.split(" "))
                            .map(Integer::valueOf)
                            .collect(Collectors.toList()));

            while (!differences.getLast().stream().allMatch(n -> n.equals(0))) {
                final List<Integer> current = differences.getLast();
                final List<Integer> d =
                        IntStream.range(0, current.size() - 1)
                                .map(i -> current.get(i + 1) - current.get(i))
                                .boxed()
                                .collect(Collectors.toList());
                differences.addLast(d);
            }

            differences.getLast().addLast(0);
            IntStream.range(0, differences.size() - 1)
                    .boxed()
                    .sorted(Collections.reverseOrder())
                    .forEach(
                            i -> {
                                final int extrapolated =
                                        differences.get(i).getLast()
                                                + differences.get(i + 1).getLast();
                                differences.get(i).add(extrapolated);
                            });

            sum += differences.getFirst().getLast();
        }
        return sum;
    }

    @Override
    public int part2(final List<String> input) {
        int sum = 0;
        for (final String history : input) {
            final List<List<Integer>> differences = new ArrayList<>();
            differences.addLast(
                    Arrays.stream(history.split(" "))
                            .map(Integer::valueOf)
                            .collect(Collectors.toList()));

            while (!differences.getLast().stream().allMatch(n -> n.equals(0))) {
                final List<Integer> current = differences.getLast();
                final List<Integer> d =
                        IntStream.range(0, current.size() - 1)
                                .map(i -> current.get(i + 1) - current.get(i))
                                .boxed()
                                .collect(Collectors.toList());
                differences.addLast(d);
            }

            differences.getLast().addFirst(0);
            IntStream.range(0, differences.size() - 1)
                    .boxed()
                    .sorted(Collections.reverseOrder())
                    .forEach(
                            i -> {
                                final int extrapolated =
                                        differences.get(i).getFirst()
                                                - differences.get(i + 1).getFirst();
                                differences.get(i).addFirst(extrapolated);
                            });

            sum += differences.getFirst().getFirst();
        }
        return sum;
    }
}
