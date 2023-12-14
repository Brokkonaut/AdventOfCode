package day9;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Task1 {
    public static void main(String[] args) throws IOException {
        ArrayList<String> input = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/java/day9/data9.txt"), StandardCharsets.UTF_8))) {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                input.add(line);
            }
        }

        long sum = 0;
        long sum2 = 0;

        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            String[] parts = line.split(" ");
            long[][] numStack = new long[parts.length][];

            numStack[0] = new long[parts.length];
            for (int j = 0; j < parts.length; j++) {
                numStack[0][j] = Integer.parseInt(parts[j]);
            }
            int k = 1;
            while (true) {
                boolean anySet = false;
                numStack[k] = new long[parts.length - k];
                for (int j = 0; j < parts.length - k; j++) {
                    long v = numStack[k - 1][j + 1] - numStack[k - 1][j];
                    numStack[k][j] = v;
                    if (v != 0) {
                        anySet = true;
                    }
                }
                if (!anySet) {
                    break;
                }
                k++;
            }
            long v = 0;
            for (int j = k - 1; j >= -1; j--) {
                long v2 = numStack[j + 1][parts.length - (j + 1) - 1];
                // System.out.println(v2);
                v += v2;
            }
            // System.out.println("a -> " + v);
            sum += v;

            v = 0;
            for (int j = k - 1; j >= -1; j--) {
                long v2 = numStack[j + 1][0];
                // v2 - new = v
                // System.out.println(v2);
                v = v2 - v;
            }
            // System.out.println("b -> " + v);
            sum2 += v;

        }
        System.out.println("task1: " + sum);
        System.out.println("task2: " + sum2);
    }
}
