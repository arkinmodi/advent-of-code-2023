package com.arkinmodi.adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class AOCInput {
    public List<String> readInputFile(String filepath) {
        try (InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(filepath);
                BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            return br.lines().toList();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: %s".formatted(filepath));
        } catch (NullPointerException e) {
            throw new RuntimeException("Failed to find file: %s".formatted(filepath));
        }
    }
}
