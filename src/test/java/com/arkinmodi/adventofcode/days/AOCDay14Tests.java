package com.arkinmodi.adventofcode.days;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.arkinmodi.adventofcode.AOCDay;
import com.arkinmodi.adventofcode.AOCInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AOCDay14Tests {

    private AOCInput aocInput = new AOCInput();
    private AOCDay aocDay;

    @BeforeEach
    public void setup() {
        aocDay = new AOCDay14();
    }

    @ParameterizedTest
    @CsvSource({"day-14-example.txt,136", "day-14-input.txt,107951"})
    public void testPart1(String filepath, int expected) {
        var data = aocInput.readInputFile(filepath);
        var actual = aocDay.part1(data);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"day-14-example.txt,64", "day-14-input.txt,95736"})
    public void testPart2(String filepath, int expected) {
        var data = aocInput.readInputFile(filepath);
        var actual = aocDay.part2(data);
        assertEquals(expected, actual);
    }
}
