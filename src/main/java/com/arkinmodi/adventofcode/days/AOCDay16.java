package com.arkinmodi.adventofcode.days;

import com.arkinmodi.adventofcode.AOCDay;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.IntStream;

public class AOCDay16 implements AOCDay {

    private enum DIRECTION {
        UP(-1, 0),
        DOWN(1, 0),
        LEFT(0, -1),
        RIGHT(0, 1);
        private final int row, col;

        private DIRECTION(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private record Tile(int row, int col) {}
    ;

    private record Beam(Tile tile, DIRECTION direction) {}
    ;

    @Override
    public int part1(final List<String> input) {
        final Set<Tile> energized = new HashSet<>();
        final Set<Beam> visited = new HashSet<>();
        final Queue<Beam> queue = new LinkedList<>();

        queue.add(new Beam(new Tile(0, 0), DIRECTION.RIGHT));
        while (!queue.isEmpty()) {
            final Beam curr = queue.remove();
            visited.add(curr);
            energized.add(curr.tile());

            final List<Beam> next = getNextBeams(input, curr);
            next.stream().filter(b -> !visited.contains(b)).forEach(b -> queue.add(b));
        }
        return energized.size();
    }

    @Override
    public int part2(final List<String> input) {
        Function<Beam, Integer> run =
                start -> {
                    final Set<Tile> energized = new HashSet<>();
                    final Set<Beam> visited = new HashSet<>();
                    final Queue<Beam> queue = new LinkedList<>();

                    queue.add(start);
                    while (!queue.isEmpty()) {
                        final Beam curr = queue.remove();
                        visited.add(curr);
                        energized.add(curr.tile());

                        final List<Beam> next = getNextBeams(input, curr);
                        next.stream().filter(b -> !visited.contains(b)).forEach(b -> queue.add(b));
                    }
                    return energized.size();
                };

        final List<Beam> startingPoints = new ArrayList<>();

        IntStream.range(0, input.getFirst().length())
                .forEach(
                        c -> {
                            startingPoints.add(new Beam(new Tile(0, c), DIRECTION.DOWN));
                            startingPoints.add(
                                    new Beam(new Tile(input.size() - 1, c), DIRECTION.UP));
                        });

        IntStream.range(0, input.size())
                .forEach(
                        r -> {
                            startingPoints.add(new Beam(new Tile(r, 0), DIRECTION.RIGHT));
                            startingPoints.add(
                                    new Beam(
                                            new Tile(r, input.getFirst().length() - 1),
                                            DIRECTION.LEFT));
                        });
        return startingPoints.parallelStream().mapToInt(run::apply).max().getAsInt();
    }

    private List<Beam> getNextBeams(List<String> input, Beam start) {
        final int r = start.tile().row();
        final int c = start.tile().col();
        final char currTile = input.get(r).charAt(c);

        if (currTile == '.') {
            if (isValidRow(input, r + start.direction().row)
                    && isValidCol(input, c + start.direction().col)) {
                return List.of(
                        new Beam(
                                new Tile(r + start.direction().row, c + start.direction().col),
                                start.direction()));
            }
            return Collections.emptyList();

        } else if (currTile == '|') {
            return switch (start.direction()) {
                case UP, DOWN -> {
                    if (isValidRow(input, r + start.direction().row)) {
                        yield List.of(
                                new Beam(
                                        new Tile(r + start.direction().row, c), start.direction()));
                    }
                    yield Collections.emptyList();
                }

                case LEFT, RIGHT -> {
                    final List<Beam> splitBeams = new ArrayList<>();

                    if (isValidRow(input, r + DIRECTION.UP.row)) {
                        splitBeams.add(new Beam(new Tile(r + DIRECTION.UP.row, c), DIRECTION.UP));
                    }

                    if (isValidRow(input, r + DIRECTION.DOWN.row)) {
                        splitBeams.add(
                                new Beam(new Tile(r + DIRECTION.DOWN.row, c), DIRECTION.DOWN));
                    }

                    yield splitBeams;
                }
            };

        } else if (currTile == '-') {
            return switch (start.direction()) {
                case UP, DOWN -> {
                    final List<Beam> splitBeams = new ArrayList<>();

                    if (isValidCol(input, c + DIRECTION.RIGHT.col)) {
                        splitBeams.add(
                                new Beam(new Tile(r, c + DIRECTION.RIGHT.col), DIRECTION.RIGHT));
                    }

                    if (isValidCol(input, c + DIRECTION.LEFT.col)) {
                        splitBeams.add(
                                new Beam(new Tile(r, c + DIRECTION.LEFT.col), DIRECTION.LEFT));
                    }

                    yield splitBeams;
                }

                case LEFT, RIGHT -> {
                    if (isValidCol(input, c + start.direction().col)) {
                        yield List.of(
                                new Beam(
                                        new Tile(r, c + start.direction().col), start.direction()));
                    }
                    yield Collections.emptyList();
                }
            };

        } else if (currTile == '/') {
            return switch (start.direction()) {
                case UP -> {
                    if (isValidCol(input, c + DIRECTION.RIGHT.col)) {
                        yield List.of(
                                new Beam(new Tile(r, c + DIRECTION.RIGHT.col), DIRECTION.RIGHT));
                    }
                    yield Collections.emptyList();
                }
                case DOWN -> {
                    if (isValidCol(input, c + DIRECTION.LEFT.col)) {
                        yield List.of(
                                new Beam(new Tile(r, c + DIRECTION.LEFT.col), DIRECTION.LEFT));
                    }
                    yield Collections.emptyList();
                }
                case LEFT -> {
                    if (isValidRow(input, r + DIRECTION.DOWN.row)) {
                        yield List.of(
                                new Beam(new Tile(r + DIRECTION.DOWN.row, c), DIRECTION.DOWN));
                    }
                    yield Collections.emptyList();
                }
                case RIGHT -> {
                    if (isValidRow(input, r + DIRECTION.UP.row)) {
                        yield List.of(new Beam(new Tile(r + DIRECTION.UP.row, c), DIRECTION.UP));
                    }
                    yield Collections.emptyList();
                }
            };

        } else if (currTile == '\\') {
            return switch (start.direction()) {
                case UP -> {
                    if (isValidCol(input, c + DIRECTION.LEFT.col)) {
                        yield List.of(
                                new Beam(new Tile(r, c + DIRECTION.LEFT.col), DIRECTION.LEFT));
                    }
                    yield Collections.emptyList();
                }
                case DOWN -> {
                    if (isValidCol(input, c + DIRECTION.RIGHT.col)) {
                        yield List.of(
                                new Beam(new Tile(r, c + DIRECTION.RIGHT.col), DIRECTION.RIGHT));
                    }
                    yield Collections.emptyList();
                }
                case LEFT -> {
                    if (isValidRow(input, r + DIRECTION.UP.row)) {
                        yield List.of(new Beam(new Tile(r + DIRECTION.UP.row, c), DIRECTION.UP));
                    }
                    yield Collections.emptyList();
                }
                case RIGHT -> {
                    if (isValidRow(input, r + DIRECTION.DOWN.row)) {
                        yield List.of(
                                new Beam(new Tile(r + DIRECTION.DOWN.row, c), DIRECTION.DOWN));
                    }
                    yield Collections.emptyList();
                }
            };
        }
        throw new RuntimeException("unexpected tile at (%d, %d): %s".formatted(r, c, currTile));
    }

    private boolean isValidRow(List<String> input, int r) {
        return 0 <= r && r < input.size();
    }

    private boolean isValidCol(List<String> input, int c) {
        return 0 <= c && c < input.getFirst().length();
    }
}
