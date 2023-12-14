package day3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Task2 {
    record Part(int number) {
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> input = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/java/day3/data3.txt"), StandardCharsets.UTF_8))) {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                input.add(line);
            }
        }

        Part[][] parts = new Part[input.size()][input.get(0).length()];

        long sum = 0;
        for (int lineNumber = 0; lineNumber < input.size(); lineNumber++) {
            String line = input.get(lineNumber);
            int currentPartNumberLength = -1;
            int partNumber = 0;
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (c >= '0' && c <= '9') {
                    if (currentPartNumberLength > 0) {
                        currentPartNumberLength++;
                        partNumber = partNumber * 10 + (c - '0');
                    } else {
                        currentPartNumberLength = 1;
                        partNumber = c - '0';
                    }
                } else {
                    if (currentPartNumberLength > 0) {
                        Part part = new Part(partNumber);
                        for (int j = i - currentPartNumberLength; j < i; j++) {
                            parts[lineNumber][j] = part;
                        }
                        currentPartNumberLength = -1;
                    }
                }
            }
            if (currentPartNumberLength > 0) {
                Part part = new Part(partNumber);
                for (int j = input.size() - currentPartNumberLength; j < input.size(); j++) {
                    parts[lineNumber][j] = part;
                }
            }
        }

        for (int lineNumber = 0; lineNumber < input.size(); lineNumber++) {
            String line = input.get(lineNumber);
            nextPart: for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (c == '*') {
                    Part firstPart = null;
                    Part secondPart = null;
                    for (int lineN2 = lineNumber - 1; lineN2 <= lineNumber + 1; lineN2++) {
                        if (lineN2 >= 0 && lineN2 < input.size()) {
                            for (int cN2 = i - 1; cN2 <= i + 1; cN2++) {
                                if (cN2 >= 0 && cN2 < parts[lineN2].length) {
                                    Part p = parts[lineN2][cN2];
                                    if (p != null) {
                                        if (firstPart == null) {
                                            firstPart = p;
                                        } else if (firstPart != p) {
                                            if (secondPart == null) {
                                                secondPart = p;
                                            } else if (secondPart != p) {
                                                continue nextPart; // too many part numbers
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (firstPart != null && secondPart != null) {
                        sum += (long) firstPart.number * secondPart.number;
                    }
                }
            }
        }
        System.out.println(sum);
    }
}
