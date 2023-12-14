package day10;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class Task1 {
    public record Pos(int x, int y) {
    }

    public static void main(String[] args) throws IOException {
        int xs = 0;
        int ys = 0;
        ArrayList<String> input = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/java/day10/data10.txt"), StandardCharsets.UTF_8))) {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                input.add(line);
                int start = line.indexOf('S');
                if (start >= 0) {
                    xs = start;
                    ys = input.size() - 1;
                }
            }
        }

        int[][] distances = new int[input.size()][input.get(0).length()];// y,x
        ArrayDeque<Pos> fields = new ArrayDeque<>();
        distances[ys][xs] = 1; // one more to avoid collision with default 0
        boolean cl = false;
        boolean cr = false;
        boolean cu = false;
        boolean cd = false;
        if (ys > 0) {
            char upper = input.get(ys - 1).charAt(xs);
            if (upper == '|' || upper == 'F' || upper == '7') {
                addIfZero(xs, ys - 1, 1, distances, fields);
                cu = true;
            }
        }
        if (ys < distances.length - 1) {
            char lower = input.get(ys + 1).charAt(xs);
            if (lower == '|' || lower == 'L' || lower == 'J') {
                addIfZero(xs, ys + 1, 1, distances, fields);
                cd = true;
            }
        }
        if (xs > 0) {
            char left = input.get(ys).charAt(xs - 1);
            if (left == '-' || left == 'L' || left == 'F') {
                addIfZero(xs - 1, ys, 1, distances, fields);
                cl = true;
            }
        }
        if (xs < distances[0].length - 1) {
            char right = input.get(ys).charAt(xs + 1);
            if (right == '-' || right == '7' || right == 'J') {
                addIfZero(xs + 1, ys, 1, distances, fields);
                cr = true;
            }
        }
        char[] chars = input.get(ys).toCharArray();
        if (cu && cd) {
            chars[xs] = '|';
        } else if (cu && cl) {
            chars[xs] = 'J';
        } else if (cu && cr) {
            chars[xs] = 'L';
        } else if (cd && cr) {
            chars[xs] = 'F';
        } else if (cd && cl) {
            chars[xs] = '7';
        } else {
            chars[xs] = '-';
        }
        input.set(ys, new String(chars));

        Pos pos = null;
        while (!fields.isEmpty()) {
            pos = fields.removeFirst();
            int x = pos.x;
            int y = pos.y;
            int distanceOld = distances[pos.y][pos.x];
            char c = input.get(pos.y).charAt(pos.x);
            if (c == '|') {
                addIfZero(x, y - 1, distanceOld, distances, fields);
                addIfZero(x, y + 1, distanceOld, distances, fields);
            } else if (c == '-') {
                addIfZero(x - 1, y, distanceOld, distances, fields);
                addIfZero(x + 1, y, distanceOld, distances, fields);
            } else if (c == 'L') {
                addIfZero(x, y - 1, distanceOld, distances, fields);
                addIfZero(x + 1, y, distanceOld, distances, fields);
            } else if (c == 'J') {
                addIfZero(x, y - 1, distanceOld, distances, fields);
                addIfZero(x - 1, y, distanceOld, distances, fields);
            } else if (c == 'F') {
                addIfZero(x, y + 1, distanceOld, distances, fields);
                addIfZero(x + 1, y, distanceOld, distances, fields);
            } else if (c == '7') {
                addIfZero(x, y + 1, distanceOld, distances, fields);
                addIfZero(x - 1, y, distanceOld, distances, fields);
            }
        }

        System.out.println("task1: " + (distances[pos.y][pos.x] - 1));

        int inside = 0;
        for (int y = 0; y < distances.length; y++) {
            int[] values = distances[y];
            boolean isinside = false;
            boolean startFromUp = false;
            for (int x = 0; x < values.length; x++) {
                if (values[x] != 0) {
                    char c = input.get(y).charAt(x);
                    if (c == 'L') {
                        startFromUp = true;
                    } else if (c == 'F') {
                        startFromUp = false;
                    } else if (c == '|' || c == (startFromUp ? '7' : 'J')) {
                        isinside = !isinside;
                    }
                } else if (isinside) {
                    inside++;
                }
            }
        }
        System.out.println("task2: " + inside);
    }

    private static void addIfZero(int x, int y, int distanceOld, int[][] distances, ArrayDeque<Pos> fields) {
        int old = distances[y][x];
        if (old == 0) {
            distances[y][x] = distanceOld + 1;
            fields.add(new Pos(x, y));
        }
    }
}
