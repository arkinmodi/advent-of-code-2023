package com.arkinmodi.adventofcode.days;

import com.arkinmodi.adventofcode.AOCDayLong;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class AOCDay12 implements AOCDayLong {

    @Override
    public long part1(final List<String> input) {
        long numCombinations = 0;
        for (final String line : input) {
            final String[] data = line.split(" ");
            final String rows = data[0];
            final List<Integer> groups =
                    Arrays.stream(data[1].split(",")).map(Integer::valueOf).toList();
            numCombinations += dfs(rows, 0, groups, 0, new HashMap<>());
        }
        return numCombinations;
    }

    @Override
    public long part2(final List<String> input) {
        long numCombinations = 0;
        for (final String line : input) {
            final String[] data = line.split(" ");
            final String rows = (data[0] + "?").repeat(4) + data[0];
            final List<Integer> groups =
                    Arrays.stream(data[1].split(",")).map(Integer::valueOf).toList();

            final List<Integer> groupsFive = new ArrayList<>();
            IntStream.range(0, 5).forEach(_i -> groupsFive.addAll(groups));

            numCombinations += dfs(rows, 0, groupsFive, 0, new HashMap<>());
        }
        return numCombinations;
    }

    private record HashKey(String row, int rowIdx, List<Integer> groups, int groupsIdx) {}
    ;

    private long dfs(
            final String row,
            final int rowIdx,
            final List<Integer> groups,
            final int groupsIdx,
            final Map<HashKey, Long> cache) {

        HashKey hashKey = new HashKey(row, rowIdx, groups, groupsIdx);
        if (cache.containsKey(hashKey)) {
            return cache.get(hashKey);
        } else if (rowIdx >= row.length()) {
            return groupsIdx >= groups.size() ? 1 : 0;
        } else if (groupsIdx >= groups.size()) {
            return row.substring(rowIdx).indexOf('#') == -1 ? 1 : 0;
        }

        long numCombinations = 0;
        if (row.charAt(rowIdx) == '.' || row.charAt(rowIdx) == '?') {
            numCombinations += dfs(row, rowIdx + 1, groups, groupsIdx, cache);
        }

        if (row.charAt(rowIdx) == '#' || row.charAt(rowIdx) == '?') {
            final int currGroupSize = groups.get(groupsIdx);

            if (currGroupSize <= row.length() - rowIdx
                    && row.substring(rowIdx, rowIdx + currGroupSize).indexOf('.') == -1
                    && (currGroupSize == row.length() - rowIdx
                            || row.charAt(rowIdx + currGroupSize) != '#')) {

                numCombinations +=
                        dfs(row, rowIdx + currGroupSize + 1, groups, groupsIdx + 1, cache);
            }
        }
        cache.put(hashKey, numCombinations);
        return numCombinations;
    }
}
