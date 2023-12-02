package com.arkinmodi.adventofcode.days;

import com.arkinmodi.adventofcode.AOCDay;
import java.util.List;

public class AOCDay02 implements AOCDay {

    @Override
    public int part1(List<String> input) {
        final int RED_CUBES = 12;
        final int GREEN_CUBES = 13;
        final int BLUE_CUBES = 14;

        int possibleGames = 0;
        for (final String game : input) {
            final String[] colonSplit = game.split(": ");
            final int gameId = Integer.valueOf(colonSplit[0].split(" ")[1]);

            final String[] subsets = colonSplit[1].split("; ");
            boolean isValid = true;
            for (int i = 0; i < subsets.length; i++) {
                final String[] cubes = subsets[i].split(", ");
                final int[] seen = new int[] {0, 0, 0};

                for (int j = 0; j < cubes.length; j++) {
                    final String[] numberAndColour = cubes[j].split(" ");
                    final int number = Integer.valueOf(numberAndColour[0]);
                    final String colour = numberAndColour[1];

                    switch (colour) {
                        case "red":
                            seen[0] += number;
                            break;
                        case "green":
                            seen[1] += number;
                            break;
                        case "blue":
                            seen[2] += number;
                            break;
                        default:
                            System.err.println("Unknown colour: %s".formatted(colour));
                            break;
                    }
                }

                isValid = seen[0] <= RED_CUBES && seen[1] <= GREEN_CUBES && seen[2] <= BLUE_CUBES;
                if (!isValid) {
                    break;
                }
            }
            possibleGames += isValid ? gameId : 0;
        }
        return possibleGames;
    }

    @Override
    public int part2(List<String> input) {
        int totalPower = 0;
        for (final String game : input) {
            final String[] subsets = game.split(": ")[1].split("; ");
            final int[] requiredCubes = new int[] {0, 0, 0};

            for (int i = 0; i < subsets.length; i++) {
                final String[] cubes = subsets[i].split(", ");

                for (int j = 0; j < cubes.length; j++) {
                    final String[] numberAndColour = cubes[j].split(" ");
                    final int number = Integer.valueOf(numberAndColour[0]);
                    final String colour = numberAndColour[1];

                    switch (colour) {
                        case "red":
                            requiredCubes[0] = Math.max(requiredCubes[0], number);
                            break;
                        case "green":
                            requiredCubes[1] = Math.max(requiredCubes[1], number);
                            break;
                        case "blue":
                            requiredCubes[2] = Math.max(requiredCubes[2], number);
                            break;
                        default:
                            System.err.println("Unknown colour: %s".formatted(colour));
                            break;
                    }
                }
            }
            totalPower += requiredCubes[0] * requiredCubes[1] * requiredCubes[2];
        }
        return totalPower;
    }
}
