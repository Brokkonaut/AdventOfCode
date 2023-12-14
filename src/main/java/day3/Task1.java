package day3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Task1 {
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

        int sum = 0;
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
                        if (isRealPartNumber(input, lineNumber, i - currentPartNumberLength, currentPartNumberLength)) {
                            sum += partNumber;
                        }
                        currentPartNumberLength = -1;
                    }
                }
            }
            if (currentPartNumberLength > 0) {
                if (isRealPartNumber(input, lineNumber, input.size() - currentPartNumberLength, currentPartNumberLength)) {
                    sum += partNumber;
                }
            }
        }
        System.out.println(sum);
    }

    private static boolean isRealPartNumber(ArrayList<String> input, int lineNumber, int start, int length) {
        String line = input.get(lineNumber);
        if (start > 0 && line.charAt(start - 1) != '.') {
            return true;
        }
        if (start + length < line.length() && line.charAt(start + length) != '.') {
            return true;
        }
        if (lineNumber > 0) {
            line = input.get(lineNumber - 1);
            for (int i = start - 1; i < start + length + 1; i++) {
                if (i >= 0 && i < line.length()) {
                    char c = line.charAt(i);
                    if (c != '.' && !(c >= '0' && c <= '9')) {
                        return true;
                    }
                }
            }
        }
        if (lineNumber < input.size() - 1) {
            line = input.get(lineNumber + 1);
            for (int i = start - 1; i < start + length + 1; i++) {
                if (i >= 0 && i < line.length()) {
                    char c = line.charAt(i);
                    if (c != '.' && !(c >= '0' && c <= '9')) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
