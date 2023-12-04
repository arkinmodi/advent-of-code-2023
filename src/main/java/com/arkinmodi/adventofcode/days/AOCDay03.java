package com.arkinmodi.adventofcode.days;

import com.arkinmodi.adventofcode.AOCDay;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AOCDay03 implements AOCDay {

    @Override
    public int part1(final List<String> input) {
        int partNumberSum = 0;

        // spotless:off
        final List<List<Integer>> directions =
            Stream.of(-1, 0, 1)
            .map(x ->
                    Stream.of(-1, 0, 1)
                    .map(y -> List.of(x, y))
                    .filter(xy -> xy != List.of(0, 0))
                    .toList())
            .flatMap(List::stream)
            .toList();

        record Coordinate(int row, int col) {};
        // spotless:on

        for (final int row : IntStream.range(0, input.size()).toArray()) {
            for (int end = 0; end < input.get(row).length(); end++) {
                if (isNumber(input.get(row).charAt(end))) {
                    final int start = end;
                    end =
                            IntStream.range(end + 1, input.get(row).length())
                                    .filter(col -> !isNumber(input.get(row).charAt(col)))
                                    .findFirst()
                                    .orElse(input.get(row).length());

                    // spotless:off
                    final boolean isValid = IntStream.range(start, end).anyMatch(
                            col -> {
                                return directions.stream()
                                    .map(d -> new Coordinate(row + d.get(0), col + d.get(1)))
                                    .anyMatch(coord -> {
                                        return 0 <= coord.row()
                                            && coord.row() < input.size()
                                            && 0 <= coord.col()
                                            && coord.col() < input.get(row).length()
                                            && isSymbol(input.get(coord.row()).charAt(coord.col()));
                                    });
                            });
                    // spotless:on

                    final int partNumber = Integer.valueOf(input.get(row).substring(start, end));
                    partNumberSum += isValid ? partNumber : 0;
                }
            }
        }
        return partNumberSum;
    }

    @Override
    public int part2(final List<String> input) {
        int partNumberSum = 0;

        // spotless:off
        final List<List<Integer>> directions =
            Stream.of(-1, 0, 1)
            .map(x ->
                    Stream.of(-1, 0, 1)
                    .map(y -> List.of(x, y))
                    .filter(xy -> xy != List.of(0, 0))
                    .toList())
            .flatMap(List::stream)
            .toList();

        record Coordinate(int row, int col) {};
        // spotless:on

        for (final int row : IntStream.range(0, input.size()).toArray()) {
            for (final int col : IntStream.range(0, input.get(row).length()).toArray()) {
                if (input.get(row).charAt(col) == '*') {
                    final List<Coordinate> visited = new ArrayList<>();

                    // spotless:off
                    final List<Integer> gears = directions.stream()
                        .map(d -> new Coordinate(row + d.get(0), col + d.get(1)))
                        .filter(coord -> {
                            return 0 <= coord.row()
                                && coord.row() < input.size()
                                && 0 <= coord.col()
                                && coord.col() < input.get(row).length()
                                && isNumber(input.get(coord.row()).charAt(coord.col()))
                                && !visited.contains(coord);
                        })
                        .map(coord -> {
                            final int start = IntStream
                                .range(0, coord.col())
                                .boxed()
                                .sorted(Collections.reverseOrder())
                                .filter(c -> !isNumber(input.get(coord.row()).charAt(c)))
                                .findFirst()
                                .orElse(-1) + 1;

                            final int end = IntStream
                                .range(coord.col(), input.get(coord.row()).length())
                                .filter(c -> !isNumber(input.get(coord.row()).charAt(c)))
                                .findFirst()
                                .orElse(input.get(row).length());

                            IntStream.range(start, end).forEach((i) -> {
                                visited.add(new Coordinate(coord.row(), i));
                            });
                            return Integer.valueOf(input.get(coord.row()).substring(start, end));
                        })
                        .toList();
                    // spotless:on

                    if (gears.size() == 2) {
                        partNumberSum += gears.stream().reduce(1, (a, b) -> a * b);
                    }
                }
            }
        }
        return partNumberSum;
    }

    private boolean isSymbol(final char c) {
        return !isNumber(c) && c != '.';
    }

    private boolean isNumber(final char c) {
        return 48 <= c && c <= 57;
    }
}
