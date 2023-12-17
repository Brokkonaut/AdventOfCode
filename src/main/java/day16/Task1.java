package day16;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Task1 {
    public static void main(String[] args) throws IOException {
        ArrayList<String> input = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/java/day16/data16.txt"), StandardCharsets.UTF_8))) {
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

        int task1 = castBeamAndCountEnergized(input, height, width, -1, 0, 0);

        int task2 = 0;
        for (int y = 0; y < height; y++) {
            task2 = Math.max(task2, castBeamAndCountEnergized(input, height, width, -1, y, 0));
            task2 = Math.max(task2, castBeamAndCountEnergized(input, height, width, width, y, 1));
        }
        for (int x = 0; x < width; x++) {
            task2 = Math.max(task2, castBeamAndCountEnergized(input, height, width, x, -1, 2));
            task2 = Math.max(task2, castBeamAndCountEnergized(input, height, width, x, height, 3));
        }

        System.out.println("task1: " + task1);
        System.out.println("task2: " + task2);
    }

    private static int castBeamAndCountEnergized(ArrayList<String> input, int height, int width, int x, int y, int beamdir) {
        int energy[][] = new int[height][width];
        castBeam(input, height, width, energy, x, y, beamdir);
        int energized = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (energy[i][j] != 0) {
                    energized++;
                }
            }
        }
        return energized;
    }

    private static void castBeam(ArrayList<String> input, int height, int width, int[][] energy, int beamx, int beamy, int beamdir) {
        while (true) {
            if (beamdir == 0) {
                beamx += 1;
            } else if (beamdir == 1) {
                beamx -= 1;
            } else if (beamdir == 2) {
                beamy += 1;
            } else {
                beamy -= 1;
            }

            if (beamx < 0 || beamx >= width || beamy < 0 || beamy >= height) {
                break; // the beam has left the building
            }

            if ((energy[beamy][beamx] & (1 << beamdir)) != 0) {
                break; // had the same beam here before
            }

            energy[beamy][beamx] |= (1 << beamdir);

            char block = input.get(beamy).charAt(beamx);
            if (block == '-') {
                if (beamdir >= 2) {
                    beamdir = 0;
                    castBeam(input, height, width, energy, beamx, beamy, 1);
                }
            } else if (block == '|') {
                if (beamdir <= 1) {
                    beamdir = 2;
                    castBeam(input, height, width, energy, beamx, beamy, 3);
                }
            } else if (block == '\\') {
                beamdir = (beamdir + 2) % 4;
                // if (beamdir == 0) {
                // beamdir = 2;
                // } else if (beamdir == 1) {
                // beamdir = 3;
                // } else if (beamdir == 2) {
                // beamdir = 0;
                // } else if (beamdir == 3) {
                // beamdir = 1;
                // }
            } else if (block == '/') {
                beamdir = 3 - beamdir;
                // if (beamdir == 0) {
                // beamdir = 3;
                // } else if (beamdir == 1) {
                // beamdir = 2;
                // } else if (beamdir == 2) {
                // beamdir = 1;
                // } else if (beamdir == 3) {
                // beamdir = 0;
                // }
            }
        }
    }
}
