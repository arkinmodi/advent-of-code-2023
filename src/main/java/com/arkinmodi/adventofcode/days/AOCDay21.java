package com.arkinmodi.adventofcode.days;

import com.arkinmodi.adventofcode.AOCDayLong;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.IntStream;

public class AOCDay21 implements AOCDayLong {

    @Override
    public long part1(final List<String> input) {
        return part1(input, 64);
    }

    @Override
    public long part2(final List<String> input) {
        return part2(input, 26501365);
    }

    private record Coordinate(int row, int col) {}
    ;

    private record Elf(Coordinate coordinate, int remainingSteps) {}
    ;

    private enum DIRECTION {
        NORTH(-1, 0),
        SOUTH(1, 0),
        EAST(0, 1),
        WEST(0, -1);
        public int row, col;

        private DIRECTION(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public int part1(final List<String> input, final int remainingSteps) {
        Coordinate start = null;

        outer:
        for (int r = 0; r < input.size(); r++) {
            for (int c = 0; c < input.get(r).length(); c++) {
                if (input.get(r).charAt(c) == 'S') {
                    start = new Coordinate(r, c);
                    break outer;
                }
            }
        }
        assert start != null : "failed to find starting position";
        return run(input, new Elf(start, remainingSteps));
    }

    public long part2(final List<String> input, final int remainingSteps) {
        Coordinate start = null;

        outer:
        for (int r = 0; r < input.size(); r++) {
            for (int c = 0; c < input.get(r).length(); c++) {
                if (input.get(r).charAt(c) == 'S') {
                    start = new Coordinate(r, c);
                    break outer;
                }
            }
        }
        assert start != null : "failed to find starting position";

        final int size = input.size();
        final int sr = start.row();
        final int sc = start.col();

        assert size == input.getFirst().length() : "(assumption) expect input to be a square";

        assert sr == sc && sc == size / 2 : "(assumption) expect start to be in the middle";

        assert remainingSteps % size == size / 2
                : "(assumption) expect that starting from the middle, walking all the way in a"
                        + " straight line will end at the edge of the input";

        assert IntStream.range(0, size)
                        .allMatch(
                                r ->
                                        input.get(r).charAt(sc) == '.'
                                                || input.get(r).charAt(sc) == 'S')
                : "(assumption) expect starting column to contain no rocks";

        assert IntStream.range(0, size)
                        .allMatch(
                                c ->
                                        input.get(sr).charAt(c) == '.'
                                                || input.get(sr).charAt(c) == 'S')
                : "(assumption) expect starting row to contain no rocks";

        final long gridWidth = remainingSteps / size - 1;

        final long numOddGrids = Double.valueOf(Math.pow((gridWidth / 2 * 2) + 1, 2)).longValue();
        final long numEvenGrids = Double.valueOf(Math.pow((gridWidth + 1) / 2 * 2, 2)).longValue();

        final long numOddPoints = run(input, new Elf(start, size * 2 + 1));
        final long numEvenPoints = run(input, new Elf(start, size * 2));

        final long cornerTop = run(input, new Elf(new Coordinate(size - 1, sc), size - 1));
        final long cornerRight = run(input, new Elf(new Coordinate(sr, 0), size - 1));
        final long cornerBottom = run(input, new Elf(new Coordinate(0, sc), size - 1));
        final long cornerLeft = run(input, new Elf(new Coordinate(sr, size - 1), size - 1));

        final long smallTopRight = run(input, new Elf(new Coordinate(size - 1, 0), size / 2 - 1));
        final long smallTopLeft =
                run(input, new Elf(new Coordinate(size - 1, size - 1), size / 2 - 1));
        final long smallBottomRight = run(input, new Elf(new Coordinate(0, 0), size / 2 - 1));
        final long smallBottomLeft = run(input, new Elf(new Coordinate(0, size - 1), size / 2 - 1));

        final long largeTopRight =
                run(input, new Elf(new Coordinate(size - 1, 0), size * 3 / 2 - 1));
        final long largeTopLeft =
                run(input, new Elf(new Coordinate(size - 1, size - 1), size * 3 / 2 - 1));
        final long largeBottomRight = run(input, new Elf(new Coordinate(0, 0), size * 3 / 2 - 1));
        final long largeBottomLeft =
                run(input, new Elf(new Coordinate(0, size - 1), size * 3 / 2 - 1));

        return numOddGrids * numOddPoints
                + numEvenGrids * numEvenPoints
                + cornerTop
                + cornerRight
                + cornerBottom
                + cornerLeft
                + (gridWidth + 1)
                        * (smallTopRight + smallTopLeft + smallBottomRight + smallBottomLeft)
                + gridWidth * (largeTopRight + largeTopLeft + largeBottomRight + largeBottomLeft);
    }

    private int run(final List<String> input, final Elf start) {
        final Queue<Elf> queue = new LinkedList<>();
        final Set<Coordinate> visited = new HashSet<>();
        final Set<Coordinate> reachable = new HashSet<>();

        queue.add(start);
        visited.add(start.coordinate());

        while (!queue.isEmpty()) {
            final Elf current = queue.remove();

            if (current.remainingSteps() % 2 == 0) {
                reachable.add(current.coordinate());
            }

            if (current.remainingSteps() == 0) {
                continue;
            }

            for (DIRECTION d : DIRECTION.values()) {
                final int r = current.coordinate().row() + d.row;
                final int c = current.coordinate().col() + d.col;
                final Coordinate coord = new Coordinate(r, c);

                if (0 <= r
                        && r < input.size()
                        && 0 <= c
                        && c < input.get(r).length()
                        && input.get(r).charAt(c) != '#'
                        && !visited.contains(coord)) {
                    visited.add(coord);
                    queue.add(new Elf(coord, current.remainingSteps() - 1));
                }
            }
        }
        return reachable.size();
    }
}
