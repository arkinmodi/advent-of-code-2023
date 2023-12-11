package com.arkinmodi.adventofcode.days;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.arkinmodi.adventofcode.AOCDayLong;
import com.arkinmodi.adventofcode.AOCInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AOCDay11Tests {

    private AOCInput aocInput = new AOCInput();
    private AOCDayLong aocDay;

    @BeforeEach
    public void setup() {
        aocDay = new AOCDay11();
    }

    @ParameterizedTest
    @CsvSource({"day-11-example.txt,374", "day-11-input.txt,9329143"})
    public void testPart1(String filepath, long expected) {
        var data = aocInput.readInputFile(filepath);
        var actual = aocDay.part1(data);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"day-11-example.txt,82000210", "day-11-input.txt,710674907809"})
    public void testPart2(String filepath, long expected) {
        var data = aocInput.readInputFile(filepath);
        var actual = aocDay.part2(data);
        assertEquals(expected, actual);
    }
}
