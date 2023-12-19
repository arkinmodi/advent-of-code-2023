package com.arkinmodi.adventofcode.days;

import com.arkinmodi.adventofcode.AOCDayLong;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

public class AOCDay19 implements AOCDayLong {

    private enum OP {
        LESS,
        GREATER;
    }

    private record WorkFlow(
            Optional<Character> category,
            Optional<OP> op,
            Optional<Integer> value,
            String next,
            boolean isEnd) {}

    private record Range(int start, int end) {}

    @Override
    public long part1(final List<String> input) {
        record Part(int x, int m, int a, int s) {}
        ;

        final Map<String, List<WorkFlow>> workflows = new HashMap<>();
        final List<Part> parts = new ArrayList<>();

        int inputIdx = 0;
        while (!input.get(inputIdx).isEmpty()) {
            final String[] data = input.get(inputIdx).split("\\{");
            final String[] rulesData = data[1].substring(0, data[1].length() - 1).split(",");

            final List<WorkFlow> rules = new ArrayList<>();
            for (int i = 0; i < rulesData.length - 1; i++) {
                final String r = rulesData[i];
                final OP op =
                        switch (r.charAt(1)) {
                            case '<' -> OP.LESS;
                            case '>' -> OP.GREATER;
                            default -> throw new RuntimeException(
                                    "unknown workflow operation %c".formatted(r.charAt(1)));
                        };
                final String[] ruleData = r.split(":");
                final int value = Integer.valueOf(ruleData[0].substring(2));

                rules.add(
                        new WorkFlow(
                                Optional.of(r.charAt(0)),
                                Optional.of(op),
                                Optional.of(value),
                                ruleData[1],
                                false));
            }
            rules.add(
                    new WorkFlow(
                            Optional.empty(),
                            Optional.empty(),
                            Optional.empty(),
                            rulesData[rulesData.length - 1],
                            true));

            workflows.put(data[0], rules);
            inputIdx++;
        }

        inputIdx++;
        while (inputIdx < input.size()) {
            final String line = input.get(inputIdx);
            final String[] data = line.substring(1, line.length() - 1).split(",");

            int x, m, a, s;
            x = m = a = s = -1;
            for (String categoryData : data) {
                final String[] category = categoryData.split("=");
                final int categoryValue = Integer.valueOf(category[1]);

                switch (category[0]) {
                    case "x" -> x = categoryValue;
                    case "m" -> m = categoryValue;
                    case "a" -> a = categoryValue;
                    case "s" -> s = categoryValue;
                    default -> throw new RuntimeException(
                            "unknown category %s for part %d".formatted(category[0], inputIdx));
                }
            }

            assert x != -1 && m != -1 && a != -1 && s != -1
                    : "failed to get all category data for part %d:\nx -> %d\nm -> %d\na -> %d\ns -> %d"
                            .formatted(inputIdx, x, m, a, s);

            parts.add(new Part(x, m, a, s));
            inputIdx++;
        }

        final List<Part> accepted = new ArrayList<>();
        for (final Part part : parts) {
            String current = "in";
            while (!current.equals("A") && !current.equals("R")) {
                for (final WorkFlow wf : workflows.get(current)) {
                    if (wf.isEnd()) {
                        current = wf.next();
                        break;
                    }

                    final int category =
                            switch (wf.category().get()) {
                                case 'x' -> part.x();
                                case 'm' -> part.m();
                                case 'a' -> part.a();
                                case 's' -> part.s();
                                default -> throw new RuntimeException("unknown category");
                            };

                    final boolean isMatch =
                            switch (wf.op().get()) {
                                case LESS -> category < wf.value().get();
                                case GREATER -> category > wf.value().get();
                            };

                    if (isMatch) {
                        current = wf.next();
                        break;
                    }
                }
            }

            if (current.equals("A")) {
                accepted.add(part);
            }
        }
        return accepted.stream().map(p -> p.x() + p.m() + p.a() + p.s()).reduce(Integer::sum).get();
    }

    @Override
    public long part2(final List<String> input) {
        final Map<String, List<WorkFlow>> workflows = new HashMap<>();

        int inputIdx = 0;
        while (!input.get(inputIdx).isEmpty()) {
            final String[] data = input.get(inputIdx).split("\\{");
            final String[] rulesData = data[1].substring(0, data[1].length() - 1).split(",");

            final List<WorkFlow> rules = new ArrayList<>();
            for (int i = 0; i < rulesData.length - 1; i++) {
                final String r = rulesData[i];
                final OP op =
                        switch (r.charAt(1)) {
                            case '<' -> OP.LESS;
                            case '>' -> OP.GREATER;
                            default -> throw new RuntimeException(
                                    "unknown workflow operation %c".formatted(r.charAt(1)));
                        };
                final String[] ruleData = r.split(":");
                final int value = Integer.valueOf(ruleData[0].substring(2));

                rules.add(
                        new WorkFlow(
                                Optional.of(r.charAt(0)),
                                Optional.of(op),
                                Optional.of(value),
                                ruleData[1],
                                false));
            }
            rules.add(
                    new WorkFlow(
                            Optional.empty(),
                            Optional.empty(),
                            Optional.empty(),
                            rulesData[rulesData.length - 1],
                            true));

            workflows.put(data[0], rules);
            inputIdx++;
        }

        return computeRanges(
                workflows,
                Map.of(
                        'x', new Range(1, 4000),
                        'm', new Range(1, 4000),
                        'a', new Range(1, 4000),
                        's', new Range(1, 4000)),
                "in");
    }

    private long computeRanges(
            final Map<String, List<WorkFlow>> workflows,
            final Map<Character, Range> ranges,
            final String wf) {

        if ("R".equals(wf)) {
            return 0;
        } else if ("A".equals(wf)) {
            return ranges.values().stream()
                    .map(r -> (long) r.end() - r.start() + 1)
                    .reduce(1L, (acc, n) -> acc * n);
        }

        long total = 0;
        final List<WorkFlow> workflow = workflows.get(wf);
        Map<Character, Range> current = ranges;
        boolean fallback = true;

        for (int i = 0; i < workflow.size() - 1; i++) {
            final Character key = workflow.get(i).category().get();
            final OP op = workflow.get(i).op().get();
            final int value = workflow.get(i).value().get();
            final String next = workflow.get(i).next();

            final int start = current.get(key).start();
            final int end = current.get(key).end();

            Range valid, invalid;
            if (OP.LESS.equals(op)) {
                valid = new Range(start, Math.min(value - 1, end));
                invalid = new Range(Math.max(value, start), end);
            } else {
                valid = new Range(Math.max(value + 1, start), end);
                invalid = new Range(start, Math.min(value, end));
            }

            if (valid.start() <= valid.end()) {
                final Map<Character, Range> copy =
                        current.entrySet().stream()
                                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
                copy.put(key, valid);
                total += computeRanges(workflows, copy, next);
            }

            if (invalid.start() <= invalid.end()) {
                current =
                        current.entrySet().stream()
                                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
                current.put(key, invalid);
            } else {
                fallback = false;
                break;
            }
        }

        if (fallback) {
            total += computeRanges(workflows, current, workflow.getLast().next());
        }

        return total;
    }
}
