package com.arkinmodi.adventofcode.days;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.arkinmodi.adventofcode.AOCDayLong;
import com.arkinmodi.adventofcode.AOCInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AOCDay19Tests {

    private AOCInput aocInput = new AOCInput();
    private AOCDayLong aocDay;

    @BeforeEach
    public void setup() {
        aocDay = new AOCDay19();
    }

    @ParameterizedTest
    @CsvSource({"day-19-example.txt,19114", "day-19-input.txt,432788"})
    public void testPart1(String filepath, long expected) {
        var data = aocInput.readInputFile(filepath);
        var actual = aocDay.part1(data);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"day-19-example.txt,167409079868000", "day-19-input.txt,142863718918201"})
    public void testPart2(String filepath, long expected) {
        var data = aocInput.readInputFile(filepath);
        var actual = aocDay.part2(data);
        assertEquals(expected, actual);
    }
}
