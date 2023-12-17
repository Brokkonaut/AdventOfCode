package day15;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Task1 {
    public static void main(String[] args) throws IOException {
        ArrayList<String> input = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/java/day15/data15.txt"), StandardCharsets.UTF_8))) {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                input.add(line);
            }
        }

        int sum = 0;
        String[] parts = input.get(0).split("\\,");
        for (String s : parts) {
            int v = 0;
            for (char c : s.toCharArray()) {
                v += c;
                v *= 17;
                v %= 256;
            }
            sum += v;
        }

        System.out.println("task1: " + sum);
    }
}
