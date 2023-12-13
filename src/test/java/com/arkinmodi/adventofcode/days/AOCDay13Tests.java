package com.arkinmodi.adventofcode.days;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.arkinmodi.adventofcode.AOCDay;
import com.arkinmodi.adventofcode.AOCInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AOCDay13Tests {

    private AOCInput aocInput = new AOCInput();
    private AOCDay aocDay;

    @BeforeEach
    public void setup() {
        aocDay = new AOCDay13();
    }

    @ParameterizedTest
    @CsvSource({"day-13-example.txt,405", "day-13-input.txt,35232"})
    public void testPart1(String filepath, int expected) {
        var data = aocInput.readInputFile(filepath);
        var actual = aocDay.part1(data);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"day-13-example.txt,400", "day-13-input.txt,37982"})
    public void testPart2(String filepath, int expected) {
        var data = aocInput.readInputFile(filepath);
        var actual = aocDay.part2(data);
        assertEquals(expected, actual);
    }
}
