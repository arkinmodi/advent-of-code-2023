package com.arkinmodi.adventofcode.days;

import com.arkinmodi.adventofcode.AOCDay;
import java.util.List;
import java.util.Map;

public class AOCDay01 implements AOCDay {

    @Override
    public int part1(final List<String> input) {
        int calibrationSum = 0;

        for (String line : input) {
            char firstDigit = ' ';
            char lastDigit = ' ';

            for (int i = 0; i < line.length(); i++) {
                final char c = line.charAt(i);
                if (isNumber(c)) {
                    firstDigit = c;
                    break;
                }
            }

            for (int i = line.length() - 1; 0 <= i; i--) {
                final char c = line.charAt(i);
                if (isNumber(c)) {
                    lastDigit = c;
                    break;
                }
            }

            calibrationSum += Integer.parseInt(String.valueOf(new char[] {firstDigit, lastDigit}));
        }
        return calibrationSum;
    }

    @Override
    public int part2(final List<String> input) {
        int calibrationSum = 0;
        for (String line : input) {
            char firstDigit = ' ';
            char lastDigit = ' ';

            for (int i = 0; i < line.length(); i++) {
                final char digit = getNumberWord(line, i);
                if (digit != ' ') {
                    firstDigit = digit;
                    break;
                }
            }

            for (int i = line.length() - 1; 0 <= i; i--) {
                final char digit = getNumberWord(line, i);
                if (digit != ' ') {
                    lastDigit = digit;
                    break;
                }
            }

            calibrationSum += Integer.parseInt("%c%c".formatted(firstDigit, lastDigit));
        }
        return calibrationSum;
    }

    private boolean isNumber(final char c) {
        return 48 <= c && c <= 57;
    }

    private char getNumberWord(final String line, final int start) {
        final Map<String, Character> digits =
                Map.ofEntries(
                        Map.entry("one", '1'),
                        Map.entry("two", '2'),
                        Map.entry("three", '3'),
                        Map.entry("four", '4'),
                        Map.entry("five", '5'),
                        Map.entry("six", '6'),
                        Map.entry("seven", '7'),
                        Map.entry("eight", '8'),
                        Map.entry("nine", '9'),
                        Map.entry("1", '1'),
                        Map.entry("2", '2'),
                        Map.entry("3", '3'),
                        Map.entry("4", '4'),
                        Map.entry("5", '5'),
                        Map.entry("6", '6'),
                        Map.entry("7", '7'),
                        Map.entry("8", '8'),
                        Map.entry("9", '9'));
        for (final String d : digits.keySet()) {
            if (start + d.length() - 1 < line.length()
                    && line.substring(start, start + d.length()).equals(d)) {
                return digits.get(d);
            }
        }
        return ' ';
    }
}
