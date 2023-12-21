package com.arkinmodi.adventofcode.days;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.arkinmodi.adventofcode.AOCDayLong;
import com.arkinmodi.adventofcode.AOCInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AOCDay20Tests {

    private AOCInput aocInput = new AOCInput();
    private AOCDayLong aocDay;

    @BeforeEach
    public void setup() {
        aocDay = new AOCDay20();
    }

    @ParameterizedTest
    @CsvSource({
        "day-20-example-part-1-1.txt,32000000",
        "day-20-example-part-1-2.txt,11687500",
        "day-20-input.txt,681194780"
    })
    public void testPart1(String filepath, long expected) {
        var data = aocInput.readInputFile(filepath);
        var actual = aocDay.part1(data);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"day-20-input.txt,238593356738827"})
    public void testPart2(String filepath, long expected) {
        var data = aocInput.readInputFile(filepath);
        var actual = aocDay.part2(data);
        assertEquals(expected, actual);
    }
}
