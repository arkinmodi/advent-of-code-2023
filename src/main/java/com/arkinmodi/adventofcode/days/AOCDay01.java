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
                    firstDigit = c;
                    break;
                }
            }

            for (int i = line.length() - 1; 0 <= i; i--) {
                char c = line.charAt(i);
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
    public int part2(List<String> input) {
        int calibrationSum = 0;
        for (String line : input) {
            char firstDigit = ' ';
            char lastDigit = ' ';

            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                char digit = isNumber(c) ? c : getNumberWord(line, i);
                if (digit != ' ') {
                    firstDigit = digit;
                    break;
                }
            }

            for (int i = line.length() - 1; 0 <= i; i--) {
                char c = line.charAt(i);
                char digit = isNumber(c) ? c : getNumberWord(line, i);
                if (digit != ' ') {
                    lastDigit = digit;
                    break;
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
            String digit = digits.get(i);
            if (start + digit.length() - 1 < line.length()
                    && line.substring(start, start + digit.length()).equals(digit)) {
                return Character.forDigit(i + 1, 10);
            }
        }
        return ' ';
    }
}
