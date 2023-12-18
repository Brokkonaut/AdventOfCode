package day17;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Task2 {
    record PosDirAndStreak(int x, int y, int dir, int streak) {
    }

    record PosDirAndStreakWithPrice(PosDirAndStreak posdirstreak, int price) implements Comparable<PosDirAndStreakWithPrice> {

        @Override
        public int compareTo(PosDirAndStreakWithPrice o) {
            return price - o.price;
        }
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> input = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/java/day17/data17.txt"), StandardCharsets.UTF_8))) {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                input.add(line);
            }
        }

        int height = input.size();
        int width = input.get(0).length();

        HashSet<PosDirAndStreak> seen = new HashSet<>();
        PriorityQueue<PosDirAndStreakWithPrice> border = new PriorityQueue<>();
        PosDirAndStreak start = new PosDirAndStreak(0, 0, 0, 0);
        seen.add(start);
        border.add(new PosDirAndStreakWithPrice(start, 0));

        // 0 >
        // 1 <
        // 2 v
        // 3 ^

        while (!border.isEmpty()) {
            PosDirAndStreakWithPrice top = border.poll();
            PosDirAndStreak posdirstreak = top.posdirstreak;
            if (posdirstreak.x == width - 1 && posdirstreak.y == height - 1 && posdirstreak.streak >= 4) {
                System.out.println("task2: " + top.price);
                return;
            }
            if (posdirstreak.dir == 0) {
                if (posdirstreak.streak < 10) {
                    addIfOnTrack(width, height, seen, border, new PosDirAndStreak(posdirstreak.x + 1, posdirstreak.y, 0, posdirstreak.streak + 1), top.price, input);
                }
                if (posdirstreak.streak >= 4) {
                    addIfOnTrack(width, height, seen, border, new PosDirAndStreak(posdirstreak.x, posdirstreak.y - 1, 3, 1), top.price, input);
                    addIfOnTrack(width, height, seen, border, new PosDirAndStreak(posdirstreak.x, posdirstreak.y + 1, 2, 1), top.price, input);
                }
            }
            if (posdirstreak.dir == 1) {
                if (posdirstreak.streak < 10) {
                    addIfOnTrack(width, height, seen, border, new PosDirAndStreak(posdirstreak.x - 1, posdirstreak.y, 1, posdirstreak.streak + 1), top.price, input);
                }
                if (posdirstreak.streak >= 4) {
                    addIfOnTrack(width, height, seen, border, new PosDirAndStreak(posdirstreak.x, posdirstreak.y - 1, 3, 1), top.price, input);
                    addIfOnTrack(width, height, seen, border, new PosDirAndStreak(posdirstreak.x, posdirstreak.y + 1, 2, 1), top.price, input);
                }
            }
            if (posdirstreak.dir == 2) {
                if (posdirstreak.streak < 10) {
                    addIfOnTrack(width, height, seen, border, new PosDirAndStreak(posdirstreak.x, posdirstreak.y + 1, 2, posdirstreak.streak + 1), top.price, input);
                }
                if (posdirstreak.streak >= 4) {
                    addIfOnTrack(width, height, seen, border, new PosDirAndStreak(posdirstreak.x - 1, posdirstreak.y, 1, 1), top.price, input);
                    addIfOnTrack(width, height, seen, border, new PosDirAndStreak(posdirstreak.x + 1, posdirstreak.y, 0, 1), top.price, input);
                }
            }
            if (posdirstreak.dir == 3) {
                if (posdirstreak.streak < 10) {
                    addIfOnTrack(width, height, seen, border, new PosDirAndStreak(posdirstreak.x, posdirstreak.y - 1, 3, posdirstreak.streak + 1), top.price, input);
                }
                if (posdirstreak.streak >= 4) {
                    addIfOnTrack(width, height, seen, border, new PosDirAndStreak(posdirstreak.x - 1, posdirstreak.y, 1, 1), top.price, input);
                    addIfOnTrack(width, height, seen, border, new PosDirAndStreak(posdirstreak.x + 1, posdirstreak.y, 0, 1), top.price, input);
                }
            }
        }

        // System.out.println("task1: " + task1);
        // System.out.println("task2: " + task2);
    }

    private static void addIfOnTrack(int width, int height, HashSet<PosDirAndStreak> seen, PriorityQueue<PosDirAndStreakWithPrice> border, PosDirAndStreak posDirAndStreak, int oldPrice, ArrayList<String> field) {
        if (posDirAndStreak.x >= 0 && posDirAndStreak.y >= 0 && posDirAndStreak.x < width && posDirAndStreak.y < height && !seen.contains(posDirAndStreak)) {
            seen.add(posDirAndStreak);
            border.add(new PosDirAndStreakWithPrice(posDirAndStreak, oldPrice + (field.get(posDirAndStreak.y).charAt(posDirAndStreak.x) - '0')));
        }
    }
}
