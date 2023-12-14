package day6;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Task1 {
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

        String[] times = input.get(0).substring("Time:".length()).trim().split(" ");
        String[] distances = input.get(1).substring("Distance:".length()).trim().split(" ");

        ArrayList<Integer> timesi = new ArrayList<>();
        for (int i = 0; i < times.length; i++) {
            if (!times[i].isEmpty()) {
                timesi.add(Integer.parseInt(times[i]));
            }
        }
        ArrayList<Integer> distancesi = new ArrayList<>();
        for (int i = 0; i < distances.length; i++) {
            if (!distances[i].isEmpty()) {
                distancesi.add(Integer.parseInt(distances[i]));
            }
        }

        long sum = 1;
        for (int i = 0; i < timesi.size(); i++) {
            int time = timesi.get(i);
            int distance = distancesi.get(i);
            long beaten = 0;
            System.out.println("time: " + time + " distance:" + distance);
            for (int j = 0; j < time; j++) {
                int d = j * (time - j);
                System.out.println("j: " + j + " d:" + d);
                if (d > distance) {
                    beaten++;
                }
            }
            sum *= beaten;
        }

        System.out.println("task1: " + sum);
    }
}
