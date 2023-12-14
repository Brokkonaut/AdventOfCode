package day11;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Task1 {
    public record Pos(long x, long y) {
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> input = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/java/day11/data11.txt"), StandardCharsets.UTF_8))) {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                input.add(line);
            }
        }

        int oldheight = input.size();
        int oldwidth = input.get(0).length();
        boolean[] notempty = new boolean[oldwidth];
        boolean[] notemptyrows = new boolean[oldheight];
        for (int y = 0; y < oldheight; y++) {
            String line = input.get(y);
            boolean lineEmpty = true;
            for (int x = 0; x < oldwidth; x++) {
                if (line.charAt(x) == '#') {
                    notempty[x] = true;
                    lineEmpty = false;
                }
            }
            if (!lineEmpty) {
                notemptyrows[y] = true;
                // } else {
                // System.out.println("row: " + y);
            }
        }
        // for (int i = 0; i < notempty.length; i++) {
        // if (!notempty[i]) {
        // System.out.println("col: " + i);
        // }
        // }

        ArrayList<Pos> stars = new ArrayList<>();
        ArrayList<Pos> stars2 = new ArrayList<>();
        int addrows = 0;
        for (int y = 0; y < oldheight; y++) {
            if (!notemptyrows[y]) {
                addrows++;
            }
            int addCols = 0;
            String line = input.get(y);
            for (int x = 0; x < oldwidth; x++) {
                if (!notempty[x]) {
                    addCols++;
                } else {
                    if (line.charAt(x) == '#') {
                        // universe[y][x + addCols] = 1;
                        stars.add(new Pos(x + addCols, y + addrows));
                        stars2.add(new Pos(x + addCols * (1000000 - 1), y + addrows * (1000000 - 1)));
                    }
                }
            }
        }
        long sum = 0;
        // System.out.println(stars);
        for (int i = 0; i < stars.size(); i++) {
            Pos star1 = stars.get(i);
            for (int j = i + 1; j < stars.size(); j++) {
                Pos star2 = stars.get(j);
                long dx = Math.abs(star1.x - star2.x);
                long dy = Math.abs(star1.y - star2.y);
                long d = dx + dy;
                // System.out.println(d);
                sum += d;
            }
        }

        long sum2 = 0;
        // System.out.println(stars);
        for (int i = 0; i < stars2.size(); i++) {
            Pos star1 = stars2.get(i);
            for (int j = i + 1; j < stars2.size(); j++) {
                Pos star2 = stars2.get(j);
                long dx = Math.abs(star1.x - star2.x);
                long dy = Math.abs(star1.y - star2.y);
                long d = dx + dy;
                // System.out.println(d);
                sum2 += d;
            }
        }
        System.out.println("task1: " + sum);
        System.out.println("task2: " + sum2);
    }
}
