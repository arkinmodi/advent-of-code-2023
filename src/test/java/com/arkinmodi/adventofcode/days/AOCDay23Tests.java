package com.arkinmodi.adventofcode.days;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.arkinmodi.adventofcode.AOCDay;
import com.arkinmodi.adventofcode.AOCInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AOCDay23Tests {

    private AOCInput aocInput = new AOCInput();
    private AOCDay aocDay;

    @BeforeEach
    public void setup() {
        aocDay = new AOCDay23();
    }

    @ParameterizedTest
    @CsvSource({"day-23-example.txt,94", "day-23-input.txt,1966"})
    public void testPart1(String filepath, int expected) {
        var data = aocInput.readInputFile(filepath);
        var actual = aocDay.part1(data);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"day-23-example.txt,154", "day-23-input.txt,6286"})
    public void testPart2(String filepath, int expected) {
        var data = aocInput.readInputFile(filepath);
        var actual = aocDay.part2(data);
        assertEquals(expected, actual);
    }
}
