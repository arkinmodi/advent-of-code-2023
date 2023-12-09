package com.arkinmodi.adventofcode.days;

import com.arkinmodi.adventofcode.AOCDayLong;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AOCDay08 implements AOCDayLong {

    @Override
    public long part1(final List<String> input) {
        record LeftRight(String left, String right) {}
        ;

        String instructions = input.get(0);

        final Map<String, LeftRight> nodes = new HashMap<>();
        input.stream()
                .skip(2)
                .forEach(
                        s -> {
                            nodes.put(
                                    s.substring(0, 3),
                                    new LeftRight(s.substring(7, 10), s.substring(12, 15)));
                        });

        int steps = 0;
        String n = "AAA";
        while (!n.equals("ZZZ")) {
            if (instructions.charAt(steps % instructions.length()) == 'L') {
                n = nodes.get(n).left();
            } else {
                n = nodes.get(n).right();
            }
            steps++;
        }
        return steps;
    }

    @Override
    public long part2(final List<String> input) {
        record LeftRight(String left, String right) {}
        ;

        String instructions = input.get(0);
        final List<String> starts = new ArrayList<>();
        final Map<String, LeftRight> nodes = new HashMap<>();

        input.stream()
                .skip(2)
                .forEach(
                        s -> {
                            String node = s.substring(0, 3);
                            nodes.put(node, new LeftRight(s.substring(7, 10), s.substring(12, 15)));
                            if (node.charAt(node.length() - 1) == 'A') {
                                starts.add(node);
                            }
                        });

        final List<Long> stepsToEnd =
                starts.stream()
                        .map(
                                s -> {
                                    int steps = 0;
                                    String n = s;
                                    while (n.charAt(n.length() - 1) != 'Z') {
                                        final int i = steps % instructions.length();
                                        if (instructions.charAt(i) == 'L') {
                                            n = nodes.get(n).left();
                                        } else {
                                            n = nodes.get(n).right();
                                        }
                                        steps++;
                                    }
                                    return steps;
                                })
                        .map(Long::valueOf)
                        .toList();
        return lcm(stepsToEnd);
    }

    private long gcd(long a, long b) {
        while (b > 0) {
            long t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    private long lcm(long a, long b) {
        return a * (b / gcd(a, b));
    }

    private long lcm(List<Long> nums) {
        return nums.stream().skip(1).reduce(nums.get(0), (acc, n) -> lcm(acc, n));
    }
}
