package com.arkinmodi.adventofcode.days;

import com.arkinmodi.adventofcode.AOCDay;
import java.util.ArrayList;
import java.util.List;

public class AOCDay03 implements AOCDay {

    @Override
    public int part1(final List<String> input) {
        int partNumberSum = 0;
        int[][] directions = {{1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}};

        for (int row = 0; row < input.size(); row++) {
            for (int end = 0; end < input.get(row).length(); end++) {
                if (isNumber(input.get(row).charAt(end))) {
                    int start = end;
                    while (end < input.get(row).length() && isNumber(input.get(row).charAt(end))) {
                        end++;
                    }
                    int partNumber = Integer.valueOf(input.get(row).substring(start, end));

                    boolean isValid = false;
                    for (int column = start; column < end; column++) {
                        for (int d = 0; d < directions.length; d++) {
                            int newRow = row + directions[d][0];
                            int newColumn = column + directions[d][1];
                            if (0 <= newRow
                                    && newRow < input.size()
                                    && 0 <= newColumn
                                    && newColumn < input.get(row).length()
                                    && isSymbol(input.get(newRow).charAt(newColumn))) {
                                isValid = true;
                                break;
                            }
                            if (isValid) {
                                break;
                            }
                        }
                    }

                    partNumberSum += isValid ? partNumber : 0;
                }
            }
        }
        return partNumberSum;
    }

    @Override
    public int part2(final List<String> input) {
        int partNumberSum = 0;
        int[][] directions = {{1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}};
        record Coordinate(int row, int col) {}
        ;

        for (int row = 0; row < input.size(); row++) {
            for (int col = 0; col < input.get(row).length(); col++) {
                if (input.get(row).charAt(col) == '*') {
                    List<Coordinate> visited = new ArrayList<>();
                    List<Integer> gears = new ArrayList<>();

                    for (int d = 0; d < directions.length; d++) {
                        Coordinate coordinate =
                                new Coordinate(row + directions[d][0], col + directions[d][1]);

                        if (0 <= coordinate.row()
                                && coordinate.row() < input.size()
                                && 0 <= coordinate.col()
                                && coordinate.col() < input.get(row).length()
                                && isNumber(input.get(coordinate.row()).charAt(coordinate.col()))
                                && !visited.contains(coordinate)) {
                            int start, end;
                            start = end = coordinate.col();

                            while (start >= 0
                                    && isNumber(input.get(coordinate.row()).charAt(start))) {
                                start--;
                            }
                            start++;
                            while (end < input.get(coordinate.row()).length()
                                    && isNumber(input.get(coordinate.row()).charAt(end))) {
                                end++;
                            }

                            gears.add(
                                    Integer.valueOf(
                                            input.get(coordinate.row()).substring(start, end)));

                            for (int i = start; i < end; i++) {
                                visited.add(new Coordinate(coordinate.row(), i));
                            }
                        }
                    }

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
