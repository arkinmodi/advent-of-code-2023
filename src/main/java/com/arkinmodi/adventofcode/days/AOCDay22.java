package com.arkinmodi.adventofcode.days;

import com.arkinmodi.adventofcode.AOCDay;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AOCDay22 implements AOCDay {
    private record Brick(int x1, int y1, int z1, int x2, int y2, int z2)
            implements Comparable<Brick> {
        @Override
        public int compareTo(Brick o) {
            return Integer.compare(z1, o.z1());
        }
    }
    ;

    @Override
    public int part1(final List<String> input) {
        final List<Brick> bricks =
                input.stream()
                        .map(
                                line -> {
                                    final List<Integer> data =
                                            Arrays.stream(line.replace('~', ',').split(","))
                                                    .map(Integer::valueOf)
                                                    .toList();
                                    return new Brick(
                                            data.get(0),
                                            data.get(1),
                                            data.get(2),
                                            data.get(3),
                                            data.get(4),
                                            data.get(5));
                                })
                        .sorted()
                        .collect(Collectors.toList());

        for (int i = 0; i < bricks.size(); i++) {
            final Brick b = bricks.get(i);
            int maxZ = 1;

            for (int j = 0; j < i; j++) {
                final Brick check = bricks.get(j);
                if (overlaps(b, check)) {
                    maxZ = Math.max(maxZ, check.z2() + 1);
                }
            }

            bricks.set(
                    i, new Brick(b.x1(), b.y1(), maxZ, b.x2(), b.y2(), b.z2() - (b.z1() - maxZ)));
        }

        bricks.sort(Brick::compareTo);

        final Map<Integer, Set<Integer>> keySupportsValue =
                IntStream.range(0, bricks.size())
                        .boxed()
                        .collect(Collectors.toMap(i -> i, _i -> new HashSet<>()));

        final Map<Integer, Set<Integer>> valueSupportsKey =
                IntStream.range(0, bricks.size())
                        .boxed()
                        .collect(Collectors.toMap(i -> i, _i -> new HashSet<>()));

        for (int i = 0; i < bricks.size(); i++) {
            final Brick upper = bricks.get(i);
            for (int j = 0; j < i; j++) {
                final Brick lower = bricks.get(j);

                if (overlaps(lower, upper) && upper.z1() == lower.z2() + 1) {
                    keySupportsValue.get(j).add(i);
                    valueSupportsKey.get(i).add(j);
                }
            }
        }

        return IntStream.range(0, bricks.size())
                .filter(
                        i ->
                                keySupportsValue.get(i).stream()
                                        .allMatch(j -> valueSupportsKey.get(j).size() >= 2))
                .reduce(0, (acc, _n) -> acc + 1);
    }

    @Override
    public int part2(final List<String> input) {
        final List<Brick> bricks =
                input.stream()
                        .map(
                                line -> {
                                    final List<Integer> data =
                                            Arrays.stream(line.replace('~', ',').split(","))
                                                    .map(Integer::valueOf)
                                                    .toList();
                                    return new Brick(
                                            data.get(0),
                                            data.get(1),
                                            data.get(2),
                                            data.get(3),
                                            data.get(4),
                                            data.get(5));
                                })
                        .sorted()
                        .collect(Collectors.toList());

        for (int i = 0; i < bricks.size(); i++) {
            final Brick b = bricks.get(i);
            int maxZ = 1;

            for (int j = 0; j < i; j++) {
                final Brick check = bricks.get(j);
                if (overlaps(b, check)) {
                    maxZ = Math.max(maxZ, check.z2() + 1);
                }
            }

            bricks.set(
                    i, new Brick(b.x1(), b.y1(), maxZ, b.x2(), b.y2(), b.z2() - (b.z1() - maxZ)));
        }

        bricks.sort(Brick::compareTo);

        final Map<Integer, Set<Integer>> keySupportsValue =
                IntStream.range(0, bricks.size())
                        .boxed()
                        .collect(Collectors.toMap(i -> i, _i -> new HashSet<>()));

        final Map<Integer, Set<Integer>> valueSupportsKey =
                IntStream.range(0, bricks.size())
                        .boxed()
                        .collect(Collectors.toMap(i -> i, _i -> new HashSet<>()));

        for (int i = 0; i < bricks.size(); i++) {
            final Brick upper = bricks.get(i);
            for (int j = 0; j < i; j++) {
                final Brick lower = bricks.get(j);

                if (overlaps(lower, upper) && upper.z1() == lower.z2() + 1) {
                    keySupportsValue.get(j).add(i);
                    valueSupportsKey.get(i).add(j);
                }
            }
        }

        return IntStream.range(0, bricks.size())
                .map(
                        i -> {
                            final Queue<Integer> queue =
                                    keySupportsValue.get(i).stream()
                                            .filter(j -> valueSupportsKey.get(j).size() == 1)
                                            .collect(Collectors.toCollection(LinkedList::new));

                            final Set<Integer> falling = new HashSet<>(queue);
                            falling.add(i);

                            while (!queue.isEmpty()) {
                                keySupportsValue.get(queue.remove()).stream()
                                        .filter(k -> !falling.contains(k))
                                        .filter(k -> falling.containsAll(valueSupportsKey.get(k)))
                                        .forEach(
                                                k -> {
                                                    queue.add(k);
                                                    falling.add(k);
                                                });
                            }
                            return falling.size() - 1;
                        })
                .sum();
    }

    private boolean overlaps(final Brick a, final Brick b) {
        return Math.max(a.x1(), b.x1()) <= Math.min(a.x2(), b.x2())
                && Math.max(a.y1(), b.y1()) <= Math.min(a.y2(), b.y2());
    }
}
