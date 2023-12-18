package com.arkinmodi.adventofcode.days;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.arkinmodi.adventofcode.AOCDayLong;
import com.arkinmodi.adventofcode.AOCInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AOCDay18Tests {

    private AOCInput aocInput = new AOCInput();
    private AOCDayLong aocDay;

    @BeforeEach
    public void setup() {
        aocDay = new AOCDay18();
    }

    @ParameterizedTest
    @CsvSource({"day-18-example.txt,62", "day-18-input.txt,36807"})
    public void testPart1(String filepath, long expected) {
        var data = aocInput.readInputFile(filepath);
        var actual = aocDay.part1(data);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"day-18-example.txt,952408144115", "day-18-input.txt,48797603984357"})
    public void testPart2(String filepath, long expected) {
        var data = aocInput.readInputFile(filepath);
        var actual = aocDay.part2(data);
        assertEquals(expected, actual);
    }
}
