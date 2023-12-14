package day14;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Task2 {
    public record Digest(byte[] value) {
        @Override
        public int hashCode() {
            return Arrays.hashCode(value);
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof Digest d && Arrays.equals(value, d.value);
        }
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        ArrayList<byte[]> input = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/java/day14/data14.txt"), StandardCharsets.UTF_8))) {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                input.add(line.getBytes());
            }
        }

        int allSideCycles = 1_000_000_000;

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        HashMap<Digest, Integer> previous = new HashMap<>();
        boolean foundCycle = false;
        for (int cycle = 0; cycle < allSideCycles; cycle++) {
            if (!foundCycle) {
                md.reset();
                for (byte[] line : input) {
                    md.update(line);
                }
                Digest digest = new Digest(md.digest());
                Integer old = previous.get(digest);
                if (old == null) {
                    previous.put(digest, cycle);
                } else {
                    System.out.println("Found a cycle at " + old + " to " + cycle);
                    foundCycle = true;
                    int cycleLength = cycle - old;
                    int skip = (allSideCycles - cycle) / cycleLength;
                    cycle = cycle + skip * cycleLength;
                }
            }

            if (foundCycle || (cycle % 100000) == 0) {
                System.out.println(cycle + " -> " + calculateWeight(input));
            }
            // move up
            for (int i = 0; i < input.size(); i++) {
                byte[] line = input.get(i);
                for (int j = 0; j < line.length; j++) {
                    byte c = line[j];
                    if (c == 'O') {
                        int newLine = i;
                        for (int k = i - 1; k >= 0; k--) {
                            byte[] otherline = input.get(k);
                            if (otherline[j] != '.') {
                                break;
                            } else {
                                newLine = k;
                            }
                        }
                        if (newLine != i) {
                            input.get(newLine)[j] = 'O';
                            line[j] = '.';
                        }
                    }
                }
            }
            printField("up", input);

            // move left
            for (int i = 0; i < input.size(); i++) {
                byte[] line = input.get(i);
                for (int j = 0; j < line.length; j++) {
                    byte c = line[j];
                    if (c == 'O') {
                        int newPos = j;
                        for (int k = j - 1; k >= 0; k--) {
                            if (line[k] != '.') {
                                break;
                            } else {
                                newPos = k;
                            }
                        }
                        if (newPos != j) {
                            line[newPos] = 'O';
                            line[j] = '.';
                        }
                    }
                }
            }
            printField("left", input);

            // move down
            for (int i = input.size() - 1; i >= 0; i--) {
                byte[] line = input.get(i);
                for (int j = 0; j < line.length; j++) {
                    byte c = line[j];
                    if (c == 'O') {
                        int newLine = i;
                        for (int k = i + 1; k < input.size(); k++) {
                            byte[] otherline = input.get(k);
                            if (otherline[j] != '.') {
                                break;
                            } else {
                                newLine = k;
                            }
                        }
                        if (newLine != i) {
                            input.get(newLine)[j] = 'O';
                            line[j] = '.';
                        }
                    }
                }
            }
            printField("down", input);

            // move right
            for (int i = 0; i < input.size(); i++) {
                byte[] line = input.get(i);
                for (int j = line.length - 1; j >= 0; j--) {
                    byte c = line[j];
                    if (c == 'O') {
                        int newPos = j;
                        for (int k = j + 1; k < line.length; k++) {
                            if (line[k] != '.') {
                                break;
                            } else {
                                newPos = k;
                            }
                        }
                        if (newPos != j) {
                            line[newPos] = 'O';
                            line[j] = '.';
                        }
                    }
                }
            }
            printField("right", input);
        }

        int sum = calculateWeight(input);

        System.out.println("task2: " + sum);
    }

    private static int calculateWeight(ArrayList<byte[]> input) {
        int sum = 0;
        for (int i = 0; i < input.size(); i++) {
            byte[] line = input.get(i);
            for (int j = 0; j < line.length; j++) {
                byte c = line[j];
                if (c == 'O') {
                    sum += input.size() - i;
                }
            }
        }
        return sum;
    }

    private static void printField(String name, ArrayList<byte[]> input) {
        // System.out.println(name);
        // for (byte[] line : input) {
        // System.out.println(new String(line));
        // }
        // System.out.println();
    }
}
