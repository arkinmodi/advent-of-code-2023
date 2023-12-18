package com.arkinmodi.adventofcode.days;

import com.arkinmodi.adventofcode.AOCDay;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiFunction;

public class AOCDay17 implements AOCDay {

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

    private record Position(int row, int col, DIRECTION direction, int travelled) {}
    ;

    private record Crucible(Position position, int heatLoss) implements Comparable<Crucible> {
        @Override
        public int compareTo(Crucible o) {
            return Integer.compare(this.heatLoss, o.heatLoss);
        }
    }
    ;

    @Override
    public int part1(final List<String> input) {
        final List<List<Integer>> cityMap =
                input.stream()
                        .map(line -> line.chars().mapToObj(c -> Character.digit(c, 10)).toList())
                        .toList();

        final int ROW = cityMap.size();
        final int COL = cityMap.getFirst().size();

        final BiFunction<Integer, Integer, Boolean> isValidPosition =
                (r, c) -> {
                    return 0 <= r && r < ROW && 0 <= c && c < COL;
                };

        final Set<Position> visited = new HashSet<>();
        final Queue<Crucible> queue = new PriorityQueue<>();

        queue.add(new Crucible(new Position(0, 0, DIRECTION.RIGHT, 0), 0));
        while (!queue.isEmpty()) {
            final Crucible current = queue.remove();

            if (current.position().row == ROW - 1 && current.position().col == COL - 1) {
                return current.heatLoss();
            } else if (visited.contains(current.position())) {
                continue;
            }
            visited.add(current.position());

            for (final DIRECTION d : DIRECTION.values()) {
                // cannot travel backwards
                if (current.position().direction().row + d.row == 0
                        && current.position().direction().col + d.col == 0) {
                    continue;
                }

                final int newRow = current.position().row + d.row;
                final int newCol = current.position().col + d.col;
                if (isValidPosition.apply(newRow, newCol)) {
                    final int newHeatLoss = current.heatLoss + cityMap.get(newRow).get(newCol);

                    if (current.position().direction().equals(d)
                            && current.position().travelled() < 3) {
                        queue.add(
                                new Crucible(
                                        new Position(
                                                newRow,
                                                newCol,
                                                d,
                                                current.position().travelled() + 1),
                                        newHeatLoss));
                    } else if (!current.position().direction().equals(d)) {
                        queue.add(new Crucible(new Position(newRow, newCol, d, 1), newHeatLoss));
                    }
                }
            }
        }
        throw new RuntimeException("unreachable");
    }

    @Override
    public int part2(final List<String> input) {
        final List<List<Integer>> cityMap =
                input.stream()
                        .map(line -> line.chars().mapToObj(c -> Character.digit(c, 10)).toList())
                        .toList();

        final int ROW = cityMap.size();
        final int COL = cityMap.getFirst().size();

        final BiFunction<Integer, Integer, Boolean> isValidPosition =
                (r, c) -> {
                    return 0 <= r && r < ROW && 0 <= c && c < COL;
                };

        final Set<Position> visited = new HashSet<>();
        final Queue<Crucible> queue = new PriorityQueue<>();

        queue.add(new Crucible(new Position(0, 0, DIRECTION.RIGHT, 0), 0));
        while (!queue.isEmpty()) {
            final Crucible current = queue.remove();

            if (current.position().row == ROW - 1
                    && current.position().col == COL - 1
                    && current.position().travelled() >= 4) {
                return current.heatLoss();
            } else if (visited.contains(current.position())) {
                continue;
            }
            visited.add(current.position());

            for (final DIRECTION d : DIRECTION.values()) {
                // cannot travel backwards
                if (current.position().direction().row + d.row == 0
                        && current.position().direction().col + d.col == 0) {
                    continue;
                }

                final int newRow = current.position().row + d.row;
                final int newCol = current.position().col + d.col;
                if (isValidPosition.apply(newRow, newCol)) {
                    final int newHeatLoss = current.heatLoss + cityMap.get(newRow).get(newCol);

                    if (current.position().direction().equals(d)
                            && current.position().travelled() < 10) {
                        queue.add(
                                new Crucible(
                                        new Position(
                                                newRow,
                                                newCol,
                                                d,
                                                current.position().travelled() + 1),
                                        newHeatLoss));
                    } else if (!current.position().direction().equals(d)
                            && current.position().travelled() >= 4) {
                        queue.add(new Crucible(new Position(newRow, newCol, d, 1), newHeatLoss));
                    }
                }
            }
        }
        throw new RuntimeException("unreachable");
    }
}
