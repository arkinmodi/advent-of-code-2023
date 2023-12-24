package com.arkinmodi.adventofcode.days;

import com.arkinmodi.adventofcode.AOCDay;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class AOCDay23 implements AOCDay {

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

    private record Coordinate(int row, int col) {}
    ;

    private record Hike(Coordinate coordinate, int distance) {}
    ;

    @Override
    public int part1(final List<String> input) {
        final Coordinate start = new Coordinate(0, input.getFirst().indexOf('.'));
        final Coordinate end = new Coordinate(input.size() - 1, input.getLast().indexOf('.'));

        final List<Coordinate> points = new ArrayList<>();
        points.add(start);
        points.add(end);

        for (int r = 0; r < input.size(); r++) {
            for (int c = 0; c < input.get(r).length(); c++) {
                if (input.get(r).charAt(c) == '#') {
                    continue;
                }

                int neighbours = 0;
                for (DIRECTION d : DIRECTION.values()) {
                    final int nr = r + d.row;
                    final int nc = c + d.col;

                    if (0 <= nr
                            && nr < input.size()
                            && 0 <= nc
                            && nc < input.get(nr).length()
                            && input.get(nr).charAt(nc) != '#') {
                        neighbours++;
                    }
                }

                if (neighbours >= 3) {
                    points.add(new Coordinate(r, c));
                }
            }
        }

        final Map<Character, List<DIRECTION>> dirs =
                Map.ofEntries(
                        Map.entry('^', List.of(DIRECTION.UP)),
                        Map.entry('v', List.of(DIRECTION.DOWN)),
                        Map.entry('<', List.of(DIRECTION.LEFT)),
                        Map.entry('>', List.of(DIRECTION.RIGHT)),
                        Map.entry('.', Arrays.asList(DIRECTION.values())));

        final Map<Coordinate, Map<Coordinate, Integer>> graph =
                points.stream().collect(Collectors.toMap(p -> p, _p -> new HashMap<>()));

        for (final Coordinate p : points) {
            final Stack<Hike> stack = new Stack<>();
            stack.add(new Hike(p, 0));

            final Set<Coordinate> visited = new HashSet<>();
            visited.add(p);

            while (!stack.isEmpty()) {
                final Hike current = stack.pop();

                if (current.distance() != 0 && points.contains(current.coordinate())) {
                    graph.get(p).put(current.coordinate(), current.distance());
                    continue;
                }

                final int r = current.coordinate().row();
                final int c = current.coordinate().col();
                for (final DIRECTION d : dirs.get(input.get(r).charAt(c))) {
                    final int nr = r + d.row;
                    final int nc = c + d.col;
                    final Coordinate coord = new Coordinate(nr, nc);

                    if (0 <= nr
                            && nr < input.size()
                            && 0 <= nc
                            && nc < input.get(nr).length()
                            && input.get(nr).charAt(nc) != '#'
                            && !visited.contains(coord)) {
                        stack.push(new Hike(coord, current.distance() + 1));
                        visited.add(coord);
                    }
                }
            }
        }

        return dfs(start, new HashSet<>(), graph, end);
    }

    @Override
    public int part2(final List<String> input) {
        final Coordinate start = new Coordinate(0, input.getFirst().indexOf('.'));
        final Coordinate end = new Coordinate(input.size() - 1, input.getLast().indexOf('.'));

        final List<Coordinate> points = new ArrayList<>();
        points.add(start);
        points.add(end);

        for (int r = 0; r < input.size(); r++) {
            for (int c = 0; c < input.get(r).length(); c++) {
                if (input.get(r).charAt(c) == '#') {
                    continue;
                }

                int neighbours = 0;
                for (DIRECTION d : DIRECTION.values()) {
                    final int nr = r + d.row;
                    final int nc = c + d.col;

                    if (0 <= nr
                            && nr < input.size()
                            && 0 <= nc
                            && nc < input.get(nr).length()
                            && input.get(nr).charAt(nc) != '#') {
                        neighbours++;
                    }
                }

                if (neighbours >= 3) {
                    points.add(new Coordinate(r, c));
                }
            }
        }

        final Map<Coordinate, Map<Coordinate, Integer>> graph =
                points.stream().collect(Collectors.toMap(p -> p, _p -> new HashMap<>()));

        for (final Coordinate p : points) {
            final Stack<Hike> stack = new Stack<>();
            stack.add(new Hike(p, 0));

            final Set<Coordinate> visited = new HashSet<>();
            visited.add(p);

            while (!stack.isEmpty()) {
                final Hike current = stack.pop();

                if (current.distance() != 0 && points.contains(current.coordinate())) {
                    graph.get(p).put(current.coordinate(), current.distance());
                    continue;
                }

                final int r = current.coordinate().row();
                final int c = current.coordinate().col();
                for (final DIRECTION d : DIRECTION.values()) {
                    final int nr = r + d.row;
                    final int nc = c + d.col;
                    final Coordinate coord = new Coordinate(nr, nc);

                    if (0 <= nr
                            && nr < input.size()
                            && 0 <= nc
                            && nc < input.get(nr).length()
                            && input.get(nr).charAt(nc) != '#'
                            && !visited.contains(coord)) {
                        stack.push(new Hike(coord, current.distance() + 1));
                        visited.add(coord);
                    }
                }
            }
        }

        return dfs(start, new HashSet<>(), graph, end);
    }

    private int dfs(
            final Coordinate current,
            final Set<Coordinate> visited,
            final Map<Coordinate, Map<Coordinate, Integer>> graph,
            final Coordinate end) {

        if (current.equals(end)) {
            return 0;
        }

        int distance = Integer.MIN_VALUE;

        visited.add(current);
        for (final Coordinate next : graph.get(current).keySet()) {
            if (!visited.contains(next)) {
                distance =
                        Math.max(
                                distance,
                                dfs(next, visited, graph, end) + graph.get(current).get(next));
            }
        }
        visited.remove(current);
        return distance;
    }
}
