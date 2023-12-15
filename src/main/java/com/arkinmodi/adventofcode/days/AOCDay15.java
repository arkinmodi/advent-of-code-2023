package com.arkinmodi.adventofcode.days;

import com.arkinmodi.adventofcode.AOCDay;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class AOCDay15 implements AOCDay {

    @Override
    public int part1(final List<String> input) {
        return Arrays.stream(input.getFirst().split(",")).mapToInt(this::hashAlgorithm).sum();
    }

    @Override
    public int part2(final List<String> input) {
        record Lens(String label, int focalLength) {}
        ;

        final List<List<Lens>> boxes = new ArrayList<>();
        IntStream.range(0, 256).forEach(i -> boxes.add(new ArrayList<>()));

        for (final String step : input.getFirst().split(",")) {
            if (step.endsWith("-")) {
                final String label = step.substring(0, step.length() - 1);
                final int boxId = hashAlgorithm(label);
                boxes.get(boxId).removeIf(lens -> lens.label.equals(label));

            } else {
                final String label = step.substring(0, step.length() - 2);
                final int focalLength = Character.getNumericValue(step.charAt(step.length() - 1));
                final int boxId = hashAlgorithm(label);

                final List<Lens> box = boxes.get(boxId);
                final int lensIdx =
                        IntStream.range(0, box.size())
                                .filter(i -> box.get(i).label().equals(label))
                                .findFirst()
                                .orElse(-1);

                if (lensIdx == -1) {
                    box.addLast(new Lens(label, focalLength));
                } else {
                    box.set(lensIdx, new Lens(label, focalLength));
                }
            }
        }

        // spotless:off
        return IntStream.range(0, boxes.size())
            .map(i -> IntStream.range(0, boxes.get(i).size())
                    .map(j -> (i + 1) * (j + 1) * boxes.get(i).get(j).focalLength())
                    .sum())
            .sum();
        // spotless:on
    }

    private int hashAlgorithm(final String input) {
        int sum = 0;
        for (final char c : input.toCharArray()) {
            sum += (int) c;
            sum *= 17;
            sum %= 256;
        }
        return sum;
    }
}
