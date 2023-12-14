package day12;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class Task2 {
    public record PosAndRemaings(int pos, int remaining, char previous) {
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
        long sum = 0;
        int linenr = 0;
        HashMap<PosAndRemaings, Long> cachedMatches = new HashMap<>();

        for (String line : input) {
            cachedMatches.clear();
            linenr++;
            String[] springsAndCounts = line.split(" ");
            StringBuilder sb = new StringBuilder();
            sb.append(springsAndCounts[0]);
            sb.append('?');
            sb.append(springsAndCounts[0]);
            sb.append('?');
            sb.append(springsAndCounts[0]);
            sb.append('?');
            sb.append(springsAndCounts[0]);
            sb.append('?');
            sb.append(springsAndCounts[0]);
            char[] springs = sb.toString().toCharArray();
            String[] countStrings = springsAndCounts[1].split(",");
            int[] counts = new int[countStrings.length * 5];
            for (int i = 0; i < countStrings.length; i++) {
                counts[i] = Integer.parseInt(countStrings[i]);
                counts[i + countStrings.length] = counts[i];
                counts[i + countStrings.length * 2] = counts[i];
                counts[i + countStrings.length * 3] = counts[i];
                counts[i + countStrings.length * 4] = counts[i];
            }
            // boolean[] found = new boolean[counts.length];
            int requiredRemaining = counts.length - 1;
            for (int i = 0; i < counts.length; i++) {
                requiredRemaining += counts[i];
            }
            long total = findRecursive(springs, counts, requiredRemaining, 0, 0, 0, cachedMatches);
            System.out.println(linenr + ": " + total);
            sum += total;
        }
        System.out.println(sum);
    }

    private static long findRecursive(char[] springs, int[] counts, int requiredRemaining, int foundCount, int charnr, int currentStreak, HashMap<PosAndRemaings, Long> cachedMatches) {
        if (charnr > springs.length) {
            // System.out.println(new String(springs) + " " + unfound);
            return foundCount == counts.length ? 1 : 0;// match if all are found
        }
        char c = charnr == springs.length ? '.' : springs[charnr];
        if (c == '?') {
            springs[charnr] = '#';
            long n0 = findRecursive(springs, counts, requiredRemaining, foundCount, charnr, currentStreak, cachedMatches); // might be spring
            springs[charnr] = '.';
            char previos = charnr == 0 ? '.' : springs[charnr - 1];
            Long n1;
            if (previos == '.') {
                PosAndRemaings pr = new PosAndRemaings(charnr, foundCount, previos);
                n1 = cachedMatches.get(pr);
                if (n1 == null) {
                    n1 = findRecursive(springs, counts, requiredRemaining, foundCount, charnr, currentStreak, cachedMatches);
                    cachedMatches.put(pr, n1);
                }
            } else {
                n1 = findRecursive(springs, counts, requiredRemaining, foundCount, charnr, currentStreak, cachedMatches);
            }
            // long n1 = findRecursive(springs, counts, requiredRemaining, foundCount, charnr, currentStreak, cachedMatches); // or no spring
            springs[charnr] = '?';
            return n0 + n1;
        } else if (c == '#') {
            if (foundCount == counts.length) {
                return 0;
            }
            if (counts[foundCount] < currentStreak + 1) {
                return 0;// too long
            }
            return findRecursive(springs, counts, requiredRemaining, foundCount, charnr + 1, currentStreak + 1, cachedMatches);
        } else { // .
            if (currentStreak > 0) { // match?
                if (foundCount == counts.length) {
                    return 0;
                }
                // for (int i = 0; i < counts.length; i++) {
                if (counts[foundCount] == currentStreak) { // matching found, continue
                    // found[foundCountcounts.length - unfound] = true;
                    long n = findRecursive(springs, counts, requiredRemaining - currentStreak - 1, foundCount + 1, charnr + 1, 0, cachedMatches);
                    // found[counts.length - unfound] = false;
                    return n;
                }
                // }
                return 0; // no matching streak
            } else { // there was no streak
                if (requiredRemaining > springs.length - charnr) {
                    return 0; // does not match
                }
                return findRecursive(springs, counts, requiredRemaining, foundCount, charnr + 1, 0, cachedMatches);
            }
        }
    }

}
