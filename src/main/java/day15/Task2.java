package day15;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class Task2 {
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

        @SuppressWarnings("unchecked")
        LinkedHashMap<String, Integer>[] boxes = new LinkedHashMap[256];
        for (int i = 0; i < 256; i++) {
            boxes[i] = new LinkedHashMap<>();
        }

        String[] parts = input.get(0).split("\\,");
        for (String s : parts) {
            int v = 0;
            boolean eq = false;
            StringBuilder sb = new StringBuilder();
            String label = null;
            for (char c : s.toCharArray()) {
                if (c == '=') {
                    eq = true;
                    label = sb.toString();
                } else if (c == '-') {
                    label = sb.toString();
                    LinkedHashMap<String, Integer> box = boxes[v];
                    box.remove(label);
                } else if (eq) {
                    int focalLength = c - '0';
                    LinkedHashMap<String, Integer> box = boxes[v];
                    box.put(label, focalLength);
                } else {
                    sb.append(c);
                    v += c;
                    v *= 17;
                    v %= 256;
                }
            }
        }
        int sum2 = 0;
        for (int i = 0; i < 256; i++) {
            LinkedHashMap<String, Integer> box = boxes[i];
            if (!box.isEmpty()) {
                // System.out.println(i + ": " + box);
                int lenscount = 1;
                for (Entry<String, Integer> e : box.entrySet()) {
                    sum2 += (i + 1) * lenscount * e.getValue();
                    lenscount++;
                }
            }
        }

        System.out.println("task2: " + sum2);
    }
}
