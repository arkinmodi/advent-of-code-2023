package com.arkinmodi.adventofcode.days;

import com.arkinmodi.adventofcode.AOCDayLong;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class AOCDay11 implements AOCDayLong {

    @Override
    public long part1(final List<String> input) {
        record Coordinate(int row, int col) {}
        ;

        final List<Integer> expandedCols = new ArrayList<>();
        for (int c = 0; c < input.get(0).length(); c++) {
            boolean isColExapanded = true;
            for (int r = 0; r < input.size(); r++) {
                if (input.get(r).charAt(c) != '.') {
                    isColExapanded = false;
                    break;
                }
            }

            if (isColExapanded) {
                expandedCols.add(c);
            }
        }

        final List<Integer> expandedRows = new ArrayList<>();
        for (int r = 0; r < input.size(); r++) {
            boolean isRowExapanded = true;
            for (int c = 0; c < input.get(r).length(); c++) {
                if (input.get(r).charAt(c) != '.') {
                    isRowExapanded = false;
                    break;
                }
            }

            if (isRowExapanded) {
                expandedRows.add(r);
            }
        }

        final List<Coordinate> galaxies = new ArrayList<>();
        for (int r = 0; r < input.size(); r++) {
            for (int c = 0; c < input.get(r).length(); c++) {
                if (input.get(r).charAt(c) == '#') {
                    int row = r;
                    for (int er : expandedRows) {
                        if (er <= r) {
                            row++;
                        }
                    }

                    int col = c;
                    for (int ec : expandedCols) {
                        if (ec <= c) {
                            col++;
                        }
                    }

                    galaxies.add(new Coordinate(row, col));
                }
            }
        }

        final List<List<Coordinate>> galaxyPairs = new ArrayList<>();
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                galaxyPairs.add(List.of(galaxies.get(i), galaxies.get(j)));
            }
        }

        final BiFunction<Coordinate, Coordinate, Integer> getManhattanDistance =
                (a, b) -> {
                    return Math.abs(a.row() - b.row()) + Math.abs(a.col() - b.col());
                };

        return galaxyPairs.stream()
                .mapToInt(pair -> getManhattanDistance.apply(pair.getFirst(), pair.getLast()))
                .sum();
    }

    @Override
    public long part2(final List<String> input) {
        final int EXPANSION_FACTOR = 1_000_000 - 1;
        record Coordinate(long row, long col) {}
        ;

        final List<Integer> expandedCols = new ArrayList<>();
        for (int c = 0; c < input.get(0).length(); c++) {
            boolean isColExapanded = true;
            for (int r = 0; r < input.size(); r++) {
                if (input.get(r).charAt(c) != '.') {
                    isColExapanded = false;
                    break;
                }
            }

            if (isColExapanded) {
                expandedCols.add(c);
            }
        }

        final List<Integer> expandedRows = new ArrayList<>();
        for (int r = 0; r < input.size(); r++) {
            boolean isRowExapanded = true;
            for (int c = 0; c < input.get(r).length(); c++) {
                if (input.get(r).charAt(c) != '.') {
                    isRowExapanded = false;
                    break;
                }
            }

            if (isRowExapanded) {
                expandedRows.add(r);
            }
        }

        final List<Coordinate> galaxies = new ArrayList<>();
        for (int r = 0; r < input.size(); r++) {
            for (int c = 0; c < input.get(r).length(); c++) {
                if (input.get(r).charAt(c) == '#') {
                    long row = r;
                    for (int er : expandedRows) {
                        if (er <= r) {
                            row += EXPANSION_FACTOR;
                        }
                    }

                    long col = c;
                    for (int ec : expandedCols) {
                        if (ec <= c) {
                            col += EXPANSION_FACTOR;
                        }
                    }

                    galaxies.add(new Coordinate(row, col));
                }
            }
        }

        final List<List<Coordinate>> galaxyPairs = new ArrayList<>();
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                galaxyPairs.add(List.of(galaxies.get(i), galaxies.get(j)));
            }
        }

        final BiFunction<Coordinate, Coordinate, Long> getManhattanDistance =
                (a, b) -> {
                    return Math.abs(a.row() - b.row()) + Math.abs(a.col() - b.col());
                };

        return galaxyPairs.stream()
                .mapToLong(pair -> getManhattanDistance.apply(pair.getFirst(), pair.getLast()))
                .sum();
    }
}
