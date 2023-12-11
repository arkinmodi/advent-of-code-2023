package com.arkinmodi.adventofcode.days;

import com.arkinmodi.adventofcode.AOCDay;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AOCDay10 implements AOCDay {

    @Override
    public int part1(final List<String> input) {
        record Coordinate(int row, int col) {}
        ;

        enum DIRECTIONS {
            NORTH(-1, 0),
            SOUTH(1, 0),
            EAST(0, 1),
            WEST(0, -1);
            public int row, col;

            private DIRECTIONS(int row, int col) {
                this.row = row;
                this.col = col;
            }
        }

        final Coordinate start =
                IntStream.range(0, input.size())
                        .boxed()
                        .flatMap(
                                r ->
                                        IntStream.range(0, input.get(r).length())
                                                .boxed()
                                                .map(c -> new Coordinate(r, c)))
                        .filter(coord -> input.get(coord.row()).charAt(coord.col()) == 'S')
                        .findFirst()
                        .orElseThrow();

        final BiFunction<Coordinate, DIRECTIONS, DIRECTIONS> getNextDirection =
                (coord, enterDir) -> {
                    final char c = input.get(coord.row()).charAt(coord.col());
                    return switch (enterDir) {
                        case NORTH -> {
                            yield switch (c) {
                                case '|' -> DIRECTIONS.NORTH;
                                case 'F' -> DIRECTIONS.EAST;
                                case '7' -> DIRECTIONS.WEST;
                                default -> throw new IllegalArgumentException(
                                        "Unexpected value: " + c);
                            };
                        }
                        case SOUTH -> {
                            yield switch (c) {
                                case '|' -> DIRECTIONS.SOUTH;
                                case 'L' -> DIRECTIONS.EAST;
                                case 'J' -> DIRECTIONS.WEST;
                                default -> throw new IllegalArgumentException(
                                        "Unexpected value: " + c);
                            };
                        }
                        case EAST -> {
                            yield switch (c) {
                                case 'J' -> DIRECTIONS.NORTH;
                                case '7' -> DIRECTIONS.SOUTH;
                                case '-' -> DIRECTIONS.EAST;
                                default -> throw new IllegalArgumentException(
                                        "Unexpected value: " + c);
                            };
                        }
                        case WEST -> {
                            yield switch (c) {
                                case 'L' -> DIRECTIONS.NORTH;
                                case 'F' -> DIRECTIONS.SOUTH;
                                case '-' -> DIRECTIONS.WEST;
                                default -> throw new IllegalArgumentException(
                                        "Unexpected value: " + c);
                            };
                        }
                        default -> throw new IllegalArgumentException(
                                "Unexpected value: " + enterDir);
                    };
                };

        DIRECTIONS nextDir =
                Arrays.stream(DIRECTIONS.values())
                        .filter(
                                d -> {
                                    final char c =
                                            input.get(start.row() + d.row)
                                                    .charAt(start.col() + d.col);
                                    return switch (d) {
                                        case NORTH -> c == '|' || c == '7' || c == 'F';
                                        case SOUTH -> c == '|' || c == 'L' || c == 'J';
                                        case EAST -> c == '-' || c == '7' || c == 'J';
                                        case WEST -> c == '-' || c == 'L' || c == 'F';
                                    };
                                })
                        .findFirst()
                        .orElseThrow();

        int steps = 0;
        Coordinate current = new Coordinate(start.row() + nextDir.row, start.col() + nextDir.col);
        while (input.get(current.row()).charAt(current.col()) != 'S') {
            nextDir = getNextDirection.apply(current, nextDir);
            current = new Coordinate(current.row() + nextDir.row, current.col() + nextDir.col);
            steps++;
        }
        return Math.ceilDiv(steps, 2);
    }

    @Override
    public int part2(final List<String> input) {
        List<List<Character>> maze =
                input.stream()
                        .map(
                                s ->
                                        s.chars()
                                                .boxed()
                                                .map(i -> Character.toChars(i)[0])
                                                .collect(Collectors.toList()))
                        .toList();

        record Coordinate(int row, int col) {}
        ;

        enum DIRECTIONS {
            NORTH(-1, 0),
            SOUTH(1, 0),
            EAST(0, 1),
            WEST(0, -1);
            public int row, col;

            private DIRECTIONS(int row, int col) {
                this.row = row;
                this.col = col;
            }
        }

        final Coordinate start =
                IntStream.range(0, input.size())
                        .boxed()
                        .flatMap(
                                r ->
                                        IntStream.range(0, input.get(r).length())
                                                .boxed()
                                                .map(c -> new Coordinate(r, c)))
                        .filter(coord -> input.get(coord.row()).charAt(coord.col()) == 'S')
                        .findFirst()
                        .orElseThrow();

        final BiFunction<Coordinate, DIRECTIONS, DIRECTIONS> getNextDirection =
                (coord, enterDir) -> {
                    final char c = input.get(coord.row()).charAt(coord.col());
                    return switch (enterDir) {
                        case NORTH -> {
                            yield switch (c) {
                                case '|' -> DIRECTIONS.NORTH;
                                case 'F' -> DIRECTIONS.EAST;
                                case '7' -> DIRECTIONS.WEST;
                                default -> throw new IllegalArgumentException(
                                        "Unexpected value: " + c);
                            };
                        }
                        case SOUTH -> {
                            yield switch (c) {
                                case '|' -> DIRECTIONS.SOUTH;
                                case 'L' -> DIRECTIONS.EAST;
                                case 'J' -> DIRECTIONS.WEST;
                                default -> throw new IllegalArgumentException(
                                        "Unexpected value: " + c);
                            };
                        }
                        case EAST -> {
                            yield switch (c) {
                                case 'J' -> DIRECTIONS.NORTH;
                                case '7' -> DIRECTIONS.SOUTH;
                                case '-' -> DIRECTIONS.EAST;
                                default -> throw new IllegalArgumentException(
                                        "Unexpected value: " + c);
                            };
                        }
                        case WEST -> {
                            yield switch (c) {
                                case 'L' -> DIRECTIONS.NORTH;
                                case 'F' -> DIRECTIONS.SOUTH;
                                case '-' -> DIRECTIONS.WEST;
                                default -> throw new IllegalArgumentException(
                                        "Unexpected value: " + c);
                            };
                        }
                        default -> throw new IllegalArgumentException(
                                "Unexpected value: " + enterDir);
                    };
                };

        final List<DIRECTIONS> startDirs =
                Arrays.stream(DIRECTIONS.values())
                        .filter(
                                d -> {
                                    final int row = start.row() + d.row;
                                    final int col = start.col() + d.col;
                                    if (row < 0
                                            || input.size() <= row
                                            || col < 0
                                            || input.get(row).length() <= col) {
                                        return false;
                                    }

                                    final char c = input.get(row).charAt(col);
                                    return switch (d) {
                                        case NORTH -> c == '|' || c == '7' || c == 'F';
                                        case SOUTH -> c == '|' || c == 'L' || c == 'J';
                                        case EAST -> c == '-' || c == '7' || c == 'J';
                                        case WEST -> c == '-' || c == 'L' || c == 'F';
                                    };
                                })
                        .toList();

        assert startDirs.size() == 2 : "expect there to only be two ways to traverse";

        DIRECTIONS nextDir = startDirs.getFirst();
        Coordinate current = new Coordinate(start.row() + nextDir.row, start.col() + nextDir.col);
        final Set<Coordinate> border = new HashSet<>();
        border.add(current);

        while (input.get(current.row()).charAt(current.col()) != 'S') {
            nextDir = getNextDirection.apply(current, nextDir);
            current = new Coordinate(current.row() + nextDir.row, current.col() + nextDir.col);
            border.add(current);
        }

        // set starting position to pipe
        char startingPipe = '|';
        if (startDirs.getFirst().equals(startDirs.getLast())) {
            if (startDirs.getFirst().equals(DIRECTIONS.NORTH)
                    || startDirs.getFirst().equals(DIRECTIONS.SOUTH)) {
                startingPipe = '|';
            } else {
                startingPipe = '-';
            }
        } else {
            if (startDirs.getFirst().equals(DIRECTIONS.NORTH)) {
                if (startDirs.getLast().equals(DIRECTIONS.EAST)) {
                    startingPipe = 'L';
                } else {
                    startingPipe = 'J';
                }
            } else if (startDirs.getFirst().equals(DIRECTIONS.SOUTH)) {
                if (startDirs.getLast().equals(DIRECTIONS.EAST)) {
                    startingPipe = 'F';
                } else {
                    startingPipe = '7';
                }
            } else if (startDirs.getFirst().equals(DIRECTIONS.EAST)) {
                if (startDirs.getLast().equals(DIRECTIONS.NORTH)) {
                    startingPipe = 'L';
                } else {
                    startingPipe = 'F';
                }
            } else {
                if (startDirs.getLast().equals(DIRECTIONS.NORTH)) {
                    startingPipe = 'J';
                } else {
                    startingPipe = '7';
                }
            }
        }
        maze.get(start.row()).set(start.col(), startingPipe);

        int numEnclosed = 0;
        final List<Character> flip = List.of('|', 'L', 'J');
        for (int r = 0; r < maze.size(); r++) {
            boolean isEnclosed = false;
            for (int c = 0; c < maze.get(r).size(); c++) {
                if (border.contains(new Coordinate(r, c))) {
                    if (flip.contains(maze.get(r).get(c))) {
                        isEnclosed = !isEnclosed;
                    }
                } else {
                    if (isEnclosed) {
                        numEnclosed += 1;
                    }
                }
            }
        }

        return numEnclosed;
    }
}
