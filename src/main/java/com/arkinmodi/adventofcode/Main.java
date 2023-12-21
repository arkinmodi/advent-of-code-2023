package com.arkinmodi.adventofcode;

import com.arkinmodi.adventofcode.days.*;

public class Main {
    public static void main(String[] args) {
        AOCInput aocInput = new AOCInput();
        var aocDay = new AOCDay21();

        var data = aocDay.part1(aocInput.readInputFile("day-21-example.txt"));
        System.out.println("Part 1 (example):\t%d".formatted(data));

        data = aocDay.part1(aocInput.readInputFile("day-21-input.txt"));
        System.out.println("Part 1 (input):\t\t%d".formatted(data));

        // data = aocDay.part2(aocInput.readInputFile("day-21-example.txt"));
        // System.out.println("Part 2 (example):\t%d".formatted(data));

        data = aocDay.part2(aocInput.readInputFile("day-21-input.txt"));
        System.out.println("Part 2 (input):\t\t%d".formatted(data));
    }
}
