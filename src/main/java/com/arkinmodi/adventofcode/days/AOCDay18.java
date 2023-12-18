package com.arkinmodi.adventofcode.days;

import com.arkinmodi.adventofcode.AOCDayLong;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class AOCDay18 implements AOCDayLong {

    private enum DIRECTION {
        U(-1, 0),
        D(1, 0),
        L(0, -1),
        R(0, 1);
        private final int row, col;

        private DIRECTION(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public static List<DIRECTION> cw() {
            return List.of(R, D, L, U);
        }
    }

    private record Hole(int row, int col) {}
    ;

    @Override
    public long part1(final List<String> input) {
        final List<Hole> border = new ArrayList<>();
        border.add(new Hole(0, 0));

        int boundaryPoints = 0;
        for (final String line : input) {
            final String[] plan = line.split(" ");
            final DIRECTION direction = DIRECTION.valueOf(plan[0]);
            final int distance = Integer.valueOf(plan[1]);

            border.add(
                    new Hole(
                            border.getLast().row + direction.row * distance,
                            border.getLast().col + direction.col * distance));
            boundaryPoints += distance;
        }

        // shoelace formula
        // https://en.wikipedia.org/wiki/Shoelace_formula
        // spotless:off
        final int area = Math.abs(
                IntStream.range(0, border.size()).map(i -> {
                    final int x = border.get(i).row();
                    final int y = border.get(Math.floorMod(i - 1, border.size())).col();
                    final int y1 = border.get(Math.floorMod(i + 1, border.size())).col();
                    return x * (y - y1);
                })
                .sum()
            ) / 2;
        // spotless:on

        // pick's theorem
        // https://en.wikipedia.org/wiki/Pick%27s_theorem
        final int interior = area - boundaryPoints / 2 + 1;

        return interior + boundaryPoints;
    }

    @Override
    public long part2(final List<String> input) {
        final List<Hole> border = new ArrayList<>();
        border.add(new Hole(0, 0));

        int boundaryPoints = 0;
        for (final String line : input) {
            final String[] plan = line.split(" ");
            final DIRECTION direction = DIRECTION.cw().get(Character.digit(plan[2].charAt(7), 10));
            final int distance = Integer.valueOf(plan[2].substring(2, 7), 16);

            border.add(
                    new Hole(
                            border.getLast().row + direction.row * distance,
                            border.getLast().col + direction.col * distance));
            boundaryPoints += distance;
        }

        // shoelace formula
        // https://en.wikipedia.org/wiki/Shoelace_formula
        // spotless:off
        final long area = Math.abs(
                LongStream.range(0, border.size()).map(i -> {
                    final int idx = Long.valueOf(i).intValue();
                    final long x = border.get(idx).row();
                    final long y = border.get(Math.floorMod(idx - 1, border.size())).col();
                    final long y1 = border.get(Math.floorMod(idx + 1, border.size())).col();
                    return x * (y - y1);
                })
                .sum()
            ) / 2;
        // spotless:on

        // pick's theorem
        // https://en.wikipedia.org/wiki/Pick%27s_theorem
        final long interior = area - boundaryPoints / 2 + 1;

        return interior + boundaryPoints;
    }
}
