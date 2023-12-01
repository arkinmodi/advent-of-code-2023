package com.arkinmodi.adventofcode.days;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.arkinmodi.adventofcode.AOCDay;
import com.arkinmodi.adventofcode.AOCInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AOCDay01Tests {

    private AOCInput aocInput = new AOCInput();
    private AOCDay aocDay;

    @BeforeEach
    public void setup() {
        aocDay = new AOCDay01();
    }

    @ParameterizedTest
    @CsvSource({"day-01-example-part-1.txt,142", "day-01-input.txt,55029"})
    public void testPart1(String filepath, int expected) {
        var data = aocInput.readInputFile(filepath);
        var actual = aocDay.part1(data);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"day-01-example-part-2.txt,281", "day-01-input.txt,55686"})
    public void testPart2(String filepath, int expected) {
        var data = aocInput.readInputFile(filepath);
        var actual = aocDay.part2(data);
        assertEquals(expected, actual);
    }
}
