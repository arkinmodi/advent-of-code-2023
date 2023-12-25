package com.arkinmodi.adventofcode.days;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.arkinmodi.adventofcode.AOCInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AOCDay24Tests {

    private AOCInput aocInput = new AOCInput();
    private AOCDay24 aocDay;

    @BeforeEach
    public void setup() {
        aocDay = new AOCDay24();
    }

    @ParameterizedTest
    @CsvSource({
        "day-24-example.txt,7,27,2",
        "day-24-input.txt,200000000000000,400000000000000,12343"
    })
    public void testPart1(String filepath, long lower, long upper, int expected) {
        var data = aocInput.readInputFile(filepath);
        var actual = aocDay.part1(data, lower, upper);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"day-24-example.txt,47", "day-24-input.txt,769281292688187"})
    public void testPart2(String filepath, long expected) {
        var data = aocInput.readInputFile(filepath);
        var actual = aocDay.part2(data);
        assertEquals(expected, actual);
    }
}
