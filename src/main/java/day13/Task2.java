package day13;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Task2 {
    public record Pos(int x, int y) {
    }

    public static void main(String[] args) throws IOException {
        ArrayList<ArrayList<String>> patterns = new ArrayList<>();
        ArrayList<String> input = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/java/day13/data13.txt"), StandardCharsets.UTF_8))) {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                if (line.isEmpty()) {
                    patterns.add(input);
                    input = new ArrayList<>();
                } else {
                    input.add(line);
                }
            }
        }
        patterns.add(input);
        long sum = 0;
        for (int i = 0; i < patterns.size(); i++) {
            ArrayList<String> lines = patterns.get(i);
            int width = lines.get(0).length();
            int[] notvertical = new int[width - 1];
            for (String line : lines) {
                for (int j = 0; j < width - 1; j++) {
                    // if (!notvertical[j]) {
                    for (int k = 0; k < width - 1; k++) {
                        int pos0 = j - k;
                        int pos1 = j + k + 1;
                        if (pos0 >= 0 && pos1 < width) {
                            if (line.charAt(pos0) != line.charAt(pos1)) {
                                notvertical[j] += 1;
                                // continue vtest;
                            }
                        }
                    }
                    // }
                }
            }
            for (int j = 0; j < width - 1; j++) {
                if (notvertical[j] == 1) {
                    System.out.println("vert: " + (j + 1));
                    sum += (j + 1);
                }
            }

            int height = lines.size();
            int[] nothorizontal = new int[height - 1];
            for (int ch = 0; ch < width; ch++) {
                for (int j = 0; j < height - 1; j++) {
                    // if (!nothorizontal[j]) {
                    for (int k = 0; k < height - 1; k++) {
                        int pos0 = j - k;
                        int pos1 = j + k + 1;
                        if (pos0 >= 0 && pos1 < height) {
                            if (lines.get(pos0).charAt(ch) != lines.get(pos1).charAt(ch)) {
                                nothorizontal[j]++;
                                // continue htest;
                            }
                        }
                    }
                    // }
                }
            }
            for (int j = 0; j < height - 1; j++) {
                if (nothorizontal[j] == 1) {
                    System.out.println("horiz: " + (j + 1));
                    sum += (j + 1) * 100;
                }
            }
        }

        System.out.println("task1: " + sum);
    }
}
