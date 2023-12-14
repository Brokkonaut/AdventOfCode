package day12;

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
        ArrayList<String> input = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/java/day12/data12.txt"), StandardCharsets.UTF_8))) {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                input.add(line);
            }
        }
        int sum = 0;
        for (String line : input) {
            String[] springsAndCounts = line.split(" ");
            char[] springs = springsAndCounts[0].toCharArray();
            String[] countStrings = springsAndCounts[1].split(",");
            int[] counts = new int[countStrings.length];
            for (int i = 0; i < countStrings.length; i++) {
                counts[i] = Integer.parseInt(countStrings[i]);
            }
            boolean[] found = new boolean[counts.length];
            int total = findRecursive(springs, counts, found, found.length, 0, 0);
            System.out.println(total);
            sum += total;
        }
        System.out.println(sum);
    }

    private static int findRecursive(char[] springs, int[] counts, boolean[] found, int unfound, int charnr, int currentStreak) {
        if (charnr > springs.length) {
            System.out.println(new String(springs) + " " + unfound);
            return unfound == 0 ? 1 : 0;// match if all are found
        }
        char c = charnr == springs.length ? '.' : springs[charnr];
        if (c == '?') {
            springs[charnr] = '#';
            int n0 = findRecursive(springs, counts, found, unfound, charnr, currentStreak); // might be spring
            springs[charnr] = '.';
            int n1 = findRecursive(springs, counts, found, unfound, charnr, currentStreak); // or no spring
            springs[charnr] = '?';
            return n0 + n1;
        } else if (c == '#') {
            return findRecursive(springs, counts, found, unfound, charnr + 1, currentStreak + 1);
        } else { // .
            if (currentStreak > 0) { // match?
                if (unfound == 0) {
                    return 0;
                }
                // for (int i = 0; i < counts.length; i++) {
                if (counts[counts.length - unfound] == currentStreak) { // matching found, continue
                    found[counts.length - unfound] = true;
                    int n = findRecursive(springs, counts, found, unfound - 1, charnr + 1, 0);
                    found[counts.length - unfound] = false;
                    return n;
                }
                // }
                return 0; // no matching streak
            } else { // there was no streak
                return findRecursive(springs, counts, found, unfound, charnr + 1, 0);
            }
        }
    }

}
