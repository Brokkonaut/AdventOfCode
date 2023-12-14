package day14;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Task1 {
    public record Pos(int x, int y) {
    }

    public static void main(String[] args) throws IOException {
        ArrayList<char[]> input = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/java/day14/data14.txt"), StandardCharsets.UTF_8))) {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                input.add(line.toCharArray());
            }
        }

        for (int i = 0; i < input.size(); i++) {
            char[] line = input.get(i);
            for (int j = 0; j < line.length; j++) {
                char c = line[j];
                if (c == 'O') {
                    int newLine = i;
                    for (int k = i - 1; k >= 0; k--) {
                        char[] otherline = input.get(k);
                        if (otherline[j] != '.') {
                            break;
                        } else {
                            newLine = k;
                        }
                    }
                    if (newLine != i) {
                        input.get(newLine)[j] = 'O';
                        line[j] = '.';
                    }
                }
            }
        }

        int sum = 0;
        for (int i = 0; i < input.size(); i++) {
            char[] line = input.get(i);
            for (int j = 0; j < line.length; j++) {
                char c = line[j];
                if (c == 'O') {
                    sum += input.size() - i;
                }
            }
        }

        System.out.println("task1: " + sum);
    }
}
