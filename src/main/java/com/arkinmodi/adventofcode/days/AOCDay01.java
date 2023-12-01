package com.arkinmodi.adventofcode.days;

import com.arkinmodi.adventofcode.AOCDay;
import java.util.List;

public class AOCDay01 implements AOCDay {

    @Override
    public int part1(List<String> input) {
        int calibrationSum = 0;
        for (String line : input) {
            char firstDigit = ' ';
            char lastDigit = ' ';
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (isNumber(c)) {
                    if (firstDigit == ' ') {
                        firstDigit = c;
                    }
                    lastDigit = c;
                }
            }
            calibrationSum += Integer.parseInt(String.valueOf(new char[] {firstDigit, lastDigit}));
        }
        return calibrationSum;
    }

    @Override
    public int part2(List<String> input) {
        int calibrationSum = 0;
        for (String line : input) {
            char firstDigit = ' ';
            char lastDigit = ' ';
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                char digit = isNumber(c) ? c : getNumberWord(line, i);
                if (digit != ' ') {
                    if (firstDigit == ' ') {
                        firstDigit = digit;
                    }
                    lastDigit = digit;
                }
            }
            calibrationSum += Integer.parseInt(String.valueOf(new char[] {firstDigit, lastDigit}));
        }
        return calibrationSum;
    }

    private boolean isNumber(char c) {
        return 48 <= c && c <= 57;
    }

    private char getNumberWord(String line, int start) {
        List<String> digits =
                List.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine");
        for (int i = 0; i < digits.size(); i++) {
            if (start + digits.get(i).length() - 1 < line.length()
                    && line.substring(start, start + digits.get(i).length())
                            .equals(digits.get(i))) {
                return Character.forDigit(i + 1, 10);
            }
        }
        return ' ';
    }
}
