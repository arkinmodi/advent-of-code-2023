package com.arkinmodi.adventofcode.days;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.arkinmodi.adventofcode.AOCInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AOCDay21Tests {

    private AOCInput aocInput = new AOCInput();
    private AOCDay21 aocDay;

    @BeforeEach
    public void setup() {
        aocDay = new AOCDay21();
    }

    @ParameterizedTest
    @CsvSource({"day-21-example.txt,6,16", "day-21-input.txt,64,3632"})
    public void testPart1(String filepath, int remainingSteps, long expected) {
        var data = aocInput.readInputFile(filepath);
        var actual = aocDay.part1(data, remainingSteps);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"day-21-input.txt,26501365,600336060511101"})
    public void testPart2(String filepath, int remainingSteps, long expected) {
        var data = aocInput.readInputFile(filepath);
        var actual = aocDay.part2(data, remainingSteps);
        assertEquals(expected, actual);
    }
}
