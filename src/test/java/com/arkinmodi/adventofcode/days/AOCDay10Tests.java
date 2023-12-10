package com.arkinmodi.adventofcode.days;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.arkinmodi.adventofcode.AOCDay;
import com.arkinmodi.adventofcode.AOCInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AOCDay10Tests {

    private AOCInput aocInput = new AOCInput();
    private AOCDay aocDay;

    @BeforeEach
    public void setup() {
        aocDay = new AOCDay10();
    }

    @ParameterizedTest
    @CsvSource({
        "day-10-example-part-1-1.txt,4",
        "day-10-example-part-1-2.txt,8",
        "day-10-input.txt,6812"
    })
    public void testPart1(String filepath, int expected) {
        var data = aocInput.readInputFile(filepath);
        var actual = aocDay.part1(data);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({
        "day-10-example-part-2-1.txt,4",
        "day-10-example-part-2-2.txt,4",
        "day-10-example-part-2-3.txt,8",
        "day-10-example-part-2-4.txt,10",
        "day-10-input.txt,527"
    })
    public void testPart2(String filepath, int expected) {
        var data = aocInput.readInputFile(filepath);
        var actual = aocDay.part2(data);
        assertEquals(expected, actual);
    }
}
