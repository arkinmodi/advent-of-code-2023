package com.arkinmodi.adventofcode;

import com.arkinmodi.adventofcode.days.*;

public class Main {
    public static void main(String[] args) {
        AOCInput aocInput = new AOCInput();
        AOCDayLong aocDay = new AOCDay11();

        var data = aocDay.part1(aocInput.readInputFile("day-11-example.txt"));
        System.out.println("Part 1 (example):\t%d".formatted(data));

        data = aocDay.part1(aocInput.readInputFile("day-11-input.txt"));
        System.out.println("Part 1 (input):\t\t%d".formatted(data));

        data = aocDay.part2(aocInput.readInputFile("day-11-example.txt"));
        System.out.println("Part 2 (example):\t%d".formatted(data));

        data = aocDay.part2(aocInput.readInputFile("day-11-input.txt"));
        System.out.println("Part 2 (input):\t\t%d".formatted(data));
    }
}
