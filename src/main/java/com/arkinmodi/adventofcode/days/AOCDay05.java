package com.arkinmodi.adventofcode.days;

import com.arkinmodi.adventofcode.AOCDay;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class AOCDay05 implements AOCDay {

    @Override
    public int part1(final List<String> input) {
        record Mapping(long destination, long source, long range) {}
        ;

        final List<Long> seeds =
                Arrays.stream(input.get(0).split(": ")[1].split(" "))
                        .map(s -> Long.valueOf(s))
                        .toList();
        final Map<String, List<Mapping>> almanac = new HashMap<>();

        for (int i = 2; i < input.size(); i++) {
            final String key = input.get(i).split(" ")[0];
            final List<Mapping> mappings = new ArrayList<>();

            i++;
            while (i < input.size() && !input.get(i).isEmpty()) {
                final String[] conversion = input.get(i).split(" ");
                final long destination = Long.parseLong(conversion[0]);
                final long source = Long.parseLong(conversion[1]);
                final long range = Long.parseLong(conversion[2]);

                mappings.add(new Mapping(destination, source, range));
                i++;
            }
            almanac.put(key, mappings);
        }

        BiFunction<String, Long, Long> getCategoryNumber =
                (category, source) -> {
                    for (Mapping m : almanac.get(category)) {
                        final long sourceStart = m.source();
                        final long sourceEnd = m.source() + m.range();

                        if (sourceStart <= source && source < sourceEnd) {
                            final long differnce = source - sourceStart;
                            return m.destination() + differnce;
                        }
                    }
                    return source;
                };

        return seeds.stream()
                .map(
                        s -> {
                            final long soil = getCategoryNumber.apply("seed-to-soil", s);
                            final long fertilizer =
                                    getCategoryNumber.apply("soil-to-fertilizer", soil);
                            final long water =
                                    getCategoryNumber.apply("fertilizer-to-water", fertilizer);
                            final long light = getCategoryNumber.apply("water-to-light", water);
                            final long temperature =
                                    getCategoryNumber.apply("light-to-temperature", light);
                            final long humidity =
                                    getCategoryNumber.apply("temperature-to-humidity", temperature);
                            final long location =
                                    getCategoryNumber.apply("humidity-to-location", humidity);
                            return location;
                        })
                .reduce(Long.MAX_VALUE, (acc, n) -> Math.min(acc, n))
                .intValue();
    }

    @Override
    public int part2(final List<String> input) {
        // The idea here is to process the seeds in ranged buckets instead of each individual value.
        // Every seed inside a range has the same conversion applied to it so we only need to track
        // the beginning and the end of each range. During each conversion, we check if the bucket
        // needs to be split into smaller buckets to uphold this rule.

        record Bucket(long start, long end) {}
        ;
        record Mapping(
                long sourceStart, long sourceEnd, long destinationStart, long destinationEnd) {}
        ;

        List<Bucket> toProcess = new ArrayList<>();
        final String[] seedData = input.get(0).split(": ")[1].split(" ");
        for (int i = 0; i < seedData.length; i += 2) {
            final long start = Long.parseLong(seedData[i]);
            final long range = Long.parseLong(seedData[i + 1]);
            toProcess.add(new Bucket(start, start + range - 1));
        }

        final Map<String, List<Mapping>> almanac = new HashMap<>();
        final List<String> orderedKeys = new ArrayList<>();
        for (int i = 2; i < input.size(); i++) {
            final String key = input.get(i).split(" ")[0];
            final List<Mapping> mappings = new ArrayList<>();

            i++;
            while (i < input.size() && !input.get(i).isEmpty()) {
                final String[] conversion = input.get(i).split(" ");
                final long dest = Long.parseLong(conversion[0]);
                final long src = Long.parseLong(conversion[1]);
                final long range = Long.parseLong(conversion[2]);

                mappings.add(new Mapping(src, src + range - 1, dest, dest + range - 1));
                i++;
            }

            almanac.put(key, mappings);
            orderedKeys.add(key);
        }

        for (final String k : orderedKeys) {
            final List<Bucket> doneProcessing = new ArrayList<>();
            while (!toProcess.isEmpty()) {
                Bucket b = toProcess.removeFirst();
                boolean processed = false;

                for (Mapping m : almanac.get(k)) {
                    final long offset = m.destinationStart() - m.sourceStart();

                    if (m.sourceStart() <= b.start() && b.end() <= m.sourceEnd()) {
                        // bucket perfectly fits
                        doneProcessing.add(new Bucket(b.start() + offset, b.end() + offset));
                        processed = true;

                    } else if (m.sourceStart() <= b.start() && b.start() <= m.sourceEnd()) {
                        // only start fits --> split into 2 buckets
                        doneProcessing.add(new Bucket(b.start() + offset, m.destinationEnd()));
                        toProcess.add(new Bucket(m.sourceEnd() + 1, b.end()));
                        processed = true;

                    } else if (m.sourceStart() <= b.end() && b.end() <= m.sourceEnd()) {
                        // only end fits --> split into 2 buckets
                        doneProcessing.add(new Bucket(m.destinationStart(), b.end() + offset));
                        toProcess.add(new Bucket(b.start(), m.sourceStart() - 1));
                        processed = true;

                    } else if (b.start() < m.sourceStart() && m.sourceEnd() < b.end()) {
                        // has overlap but neither side fits --> split into 3 buckets
                        doneProcessing.add(new Bucket(m.destinationStart(), m.destinationEnd()));
                        toProcess.add(new Bucket(b.start(), m.sourceStart() - 1));
                        toProcess.add(new Bucket(m.sourceEnd() + 1, b.end()));
                        processed = true;
                    }

                    if (processed) {
                        break;
                    }
                }

                if (!processed) {
                    // no overlap
                    doneProcessing.add(b);
                }
            }
            toProcess = doneProcessing;
        }
        return toProcess.stream().map(Bucket::start).sorted().findFirst().get().intValue();
    }
}
