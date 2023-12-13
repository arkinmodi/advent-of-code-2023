package com.arkinmodi.adventofcode.days;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.arkinmodi.adventofcode.AOCDayLong;
import com.arkinmodi.adventofcode.AOCInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AOCDay12Tests {

    private AOCInput aocInput = new AOCInput();
    private AOCDayLong aocDay;

    @BeforeEach
    public void setup() {
        aocDay = new AOCDay12();
    }

    @ParameterizedTest
    @CsvSource({"day-12-example.txt,21", "day-12-input.txt,7843"})
    public void testPart1(String filepath, long expected) {
        var data = aocInput.readInputFile(filepath);
        var actual = aocDay.part1(data);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"day-12-example.txt,525152", "day-12-input.txt,10153896718999"})
    public void testPart2(String filepath, long expected) {
        var data = aocInput.readInputFile(filepath);
        var actual = aocDay.part2(data);
        assertEquals(expected, actual);
    }
}
