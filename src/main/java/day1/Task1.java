package day1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Task1 {
    public static void main(String[] args) throws IOException {
        ArrayList<String> input = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/java/day1/data1.txt"), StandardCharsets.UTF_8))) {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                input.add(line);
            }
        }
        for (int task = 0; task < 2; task++) {
            int sum = 0;
            for (String line : input) {
                int firstNumber = -1;
                int lastNumber = -1;
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    if (c >= '0' && c <= '9') {
                        if (firstNumber == -1) {
                            firstNumber = c - '0';
                        }
                        lastNumber = c - '0';
                    }
                    if (task > 0) {
                        int numberStringStart = getNumberStringStart(line, i);
                        if (numberStringStart > 0) {
                            if (firstNumber == -1) {
                                firstNumber = numberStringStart;
                            }
                            lastNumber = numberStringStart;
                        }
                    }
                }
                int nr = firstNumber * 10 + lastNumber;
                sum += nr;
            }
            System.out.println(sum);
        }
    }

    private static String[] namedNumbers = { "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };

    private static int getNumberStringStart(String line, int i) {
        for (int n = 0; n < namedNumbers.length; n++) {
            String namedNumber = namedNumbers[n];
            if (line.regionMatches(i, namedNumber, 0, namedNumber.length())) {
                return n + 1;
            }
        }
        return -1;
    }
}
