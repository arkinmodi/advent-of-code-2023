package com.arkinmodi.adventofcode;

import com.arkinmodi.adventofcode.days.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String EXAMPLE = "day-09-example.txt";
        String INPUT = "day-09-input.txt";
        AOCDay aocDay = new AOCDay09();

        AOCInput aocInput = new AOCInput();
        List<String> exampleData = aocInput.readInputFile(EXAMPLE);
        List<String> inputData = aocInput.readInputFile(INPUT);

        var examplePart1 = aocDay.part1(exampleData);
        System.out.println("Part 1 (example):\t%d".formatted(examplePart1));

        var inputPart1 = aocDay.part1(inputData);
        System.out.println("Part 1 (input):\t\t%d".formatted(inputPart1));

        var examplePart2 = aocDay.part2(exampleData);
        System.out.println("Part 2 (example):\t%d".formatted(examplePart2));

        var inputPart2 = aocDay.part2(inputData);
        System.out.println("Part 2 (input):\t\t%d".formatted(inputPart2));
    }
}
