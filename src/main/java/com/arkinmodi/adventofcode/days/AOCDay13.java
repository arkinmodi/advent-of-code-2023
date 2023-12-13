package com.arkinmodi.adventofcode.days;

import com.arkinmodi.adventofcode.AOCDay;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class AOCDay13 implements AOCDay {

    @Override
    public int part1(final List<String> input) {
        int total = 0;

        final List<List<String>> patterns = new ArrayList<>();
        patterns.add(new ArrayList<>());
        for (final String line : input) {
            if (line.isEmpty()) {
                patterns.add(new ArrayList<>());
            } else {
                patterns.getLast().add(line);
            }
        }

        for (final List<String> pattern : patterns) {
            final List<Integer> horizontalReflections =
                    IntStream.range(0, pattern.size() - 1)
                            .filter(r -> pattern.get(r).equals(pattern.get(r + 1)))
                            .boxed()
                            .toList();

            // spotless:off
            final int validHorizontal = horizontalReflections.stream()
                .filter(
                    hr -> IntStream.rangeClosed(0, hr).allMatch(
                        r -> hr + 1 + r >= pattern.size() ||
                                pattern.get(hr - r).equals(pattern.get(hr + 1 + r))
                    )
                )
                .findFirst()
                .orElse(-1);

            final List<Integer> verticalReflections = IntStream
                .range(0, pattern.getFirst().length() - 1)
                .filter(
                    c -> IntStream.range(0, pattern.size()).allMatch(
                        r -> pattern.get(r).charAt(c) == pattern.get(r).charAt(c + 1)
                    )
                )
                .boxed()
                .toList();

            final int validVertical = verticalReflections.stream()
                .filter(
                    vr -> IntStream.rangeClosed(0, vr).allMatch(
                        c -> IntStream.range(0, pattern.size()).allMatch(
                            r -> vr + 1 + c >= pattern.get(r).length() ||
                                pattern.get(r).charAt(vr - c) == pattern.get(r).charAt(vr + 1 + c)
                            )
                        )
                )
                .findFirst()
                .orElse(-1);
            // spotless:on

            total += validVertical != -1 ? validVertical + 1 : 0;
            total += validHorizontal != -1 ? (validHorizontal + 1) * 100 : 0;
        }

        return total;
    }

    @Override
    public int part2(final List<String> input) {
        final int MAX_SMUDGE = 1;
        int total = 0;

        final List<List<String>> patterns = new ArrayList<>();
        patterns.add(new ArrayList<>());
        for (final String line : input) {
            if (line.isEmpty()) {
                patterns.add(new ArrayList<>());
            } else {
                patterns.getLast().add(line);
            }
        }

        for (final List<String> pattern : patterns) {
            final List<Integer> horizontalReflections =
                    IntStream.range(0, pattern.size() - 1)
                            .filter(r -> getRowSmudgeFactor(pattern, r, r + 1) <= MAX_SMUDGE)
                            .boxed()
                            .toList();

            final List<Integer> verticalReflections =
                    IntStream.range(0, pattern.getFirst().length() - 1)
                            .filter(c -> getColSmudgeFactor(pattern, c, c + 1) <= MAX_SMUDGE)
                            .boxed()
                            .toList();

            // spotless:off
            final int validHorizontal = horizontalReflections.stream()
                .filter(
                    hr -> IntStream.rangeClosed(0, hr).map(
                        r -> hr + 1 + r >= pattern.size() ?
                                0 : getRowSmudgeFactor(pattern, hr - r, hr + 1 + r)
                        ).sum() == MAX_SMUDGE
                )
                .findFirst()
                .orElse(-1);

            final int validVertical = verticalReflections.stream()
                .filter(
                    vr -> IntStream.rangeClosed(0, vr).map(
                        c -> vr + 1 + c >= pattern.getFirst().length() ?
                            0 : getColSmudgeFactor(pattern, vr - c, vr + 1 + c)
                    ).sum() == MAX_SMUDGE
                )
                .findFirst()
                .orElse(-1);
            // spotless:on

            total += validVertical != -1 ? validVertical + 1 : 0;
            total += validHorizontal != -1 ? (validHorizontal + 1) * 100 : 0;
        }

        return total;
    }

    private int getRowSmudgeFactor(final List<String> pattern, final int r1, final int r2) {
        return IntStream.range(0, pattern.getFirst().length())
                .map(c -> pattern.get(r1).charAt(c) == pattern.get(r2).charAt(c) ? 0 : 1)
                .sum();
    }

    private int getColSmudgeFactor(final List<String> pattern, final int c1, final int c2) {
        return IntStream.range(0, pattern.size())
                .map(r -> pattern.get(r).charAt(c1) == pattern.get(r).charAt(c2) ? 0 : 1)
                .sum();
    }
}
