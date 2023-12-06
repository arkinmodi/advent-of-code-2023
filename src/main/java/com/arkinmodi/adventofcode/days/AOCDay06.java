package com.arkinmodi.adventofcode.days;

import com.arkinmodi.adventofcode.AOCDay;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class AOCDay06 implements AOCDay {

    @Override
    public int part1(final List<String> input) {
        final List<Integer> times =
                Arrays.stream(input.get(0).split(": ")[1].split(" "))
                        .filter(s -> !s.isEmpty())
                        .map(Integer::valueOf)
                        .toList();

        final List<Integer> records =
                Arrays.stream(input.get(1).split(": ")[1].split(" "))
                        .filter(s -> !s.isEmpty())
                        .map(Integer::valueOf)
                        .toList();

        return IntStream.range(0, times.size())
                .map(
                        i -> {
                            final int time = times.get(i);
                            final int record = records.get(i);

                            final long numWays =
                                    IntStream.range(0, time)
                                            .map(hold -> hold * (time - hold))
                                            .filter(dist -> dist > record)
                                            .count();
                            return Long.valueOf(numWays).intValue();
                        })
                .reduce(1, (acc, n) -> acc * n);
    }

    @Override
    public int part2(final List<String> input) {
        final int time =
                Integer.valueOf(
                        Arrays.stream(input.get(0).split(": ")[1].split(" "))
                                .filter(s -> !s.isEmpty())
                                .reduce("", (acc, s) -> acc + s));

        final long record =
                Long.valueOf(
                        Arrays.stream(input.get(1).split(": ")[1].split(" "))
                                .filter(s -> !s.isEmpty())
                                .reduce("", (acc, s) -> acc + s));

        final Long numWays =
                LongStream.range(0, time)
                        .map(hold -> hold * (time - hold))
                        .filter(dist -> dist > record)
                        .count();

        return numWays.intValue();
    }
}
