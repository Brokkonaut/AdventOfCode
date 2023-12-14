package day6;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Task2 {
    public static void main(String[] args) throws IOException {
        ArrayList<String> input = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/java/day6/data6.txt"), StandardCharsets.UTF_8))) {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                input.add(line);
            }
        }

        long time = Long.parseLong(input.get(0).substring("Time:".length()).trim().replace(" ", ""));
        long distance = Long.parseLong(input.get(1).substring("Distance:".length()).trim().replace(" ", ""));

        for (int j = 0; j < time; j++) {
            long d = j * (time - j);
            if (d > distance) {
                long beaten = time - 1 - (j - 1) * 2;
                System.out.println("task2: " + beaten);

                break;
            }
        }
    }
}
