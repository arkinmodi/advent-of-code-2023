package com.arkinmodi.adventofcode.days;

import com.arkinmodi.adventofcode.AOCDay;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AOCDay25 implements AOCDay {

    @Override
    public int part1(final List<String> input) {
        record Edge(String src, String dest) {}
        ;

        final Set<String> nodes = new HashSet<>();
        final Set<Edge> edges = new HashSet<>();

        for (final String line : input) {
            final String[] data = line.split(": ");
            final String src = data[0];

            nodes.add(src);
            Arrays.stream(data[1].split(" "))
                    .forEach(
                            dest -> {
                                nodes.add(dest);
                                edges.add(new Edge(src, dest));
                            });
        }

        final int WIRES_TO_CUT = 3;

        // karger's algorithm
        // https://en.wikipedia.org/wiki/Karger%27s_algorithm
        while (true) {
            final List<Set<String>> subsets =
                    nodes.stream().map(n -> new HashSet<>(List.of(n))).collect(Collectors.toList());
            final Function<String, Set<String>> getSubset =
                    n -> subsets.stream().filter(s -> s.contains(n)).findFirst().orElseThrow();

            final List<Edge> edgeList = edges.stream().collect(Collectors.toList());
            Collections.shuffle(edgeList);
            final Iterator<Edge> edgeListIterator = edgeList.iterator();

            while (subsets.size() > 2) {
                final Edge e = edgeListIterator.next();
                final Set<String> s1 = getSubset.apply(e.src());
                final Set<String> s2 = getSubset.apply(e.dest());

                if (!s1.equals(s2)) {
                    s1.addAll(s2);
                    subsets.remove(s2);
                }
            }

            final long numPartitions =
                    edges.stream()
                            .filter(e -> getSubset.apply(e.src()) != getSubset.apply(e.dest()))
                            .count();

            if (numPartitions == WIRES_TO_CUT) {
                return subsets.stream().map(s -> s.size()).reduce(1, (acc, n) -> acc * n);
            }
        }
    }

    @Override
    public int part2(final List<String> input) {
        throw new RuntimeException("THERE IS NO PART 2!");
    }
}
