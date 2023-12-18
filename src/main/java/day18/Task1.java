package day18;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Task1 {
    record Instruction(char dir, int amount) {
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> input = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/java/day18/data18.txt"), StandardCharsets.UTF_8))) {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                input.add(line);
            }
        }

        ArrayList<Instruction> instructions = new ArrayList<>();

        for (String line : input) {
            String[] parts = line.replace("(#", "").replace(")", "").split(" ");
            char dir = parts[0].charAt(0);
            int amount = Integer.parseInt(parts[1]);
            instructions.add(new Instruction(dir, amount));
        }

        long size = 0;

        int x = 0;
        // int y = 0;
        Instruction prevoius = instructions.get(instructions.size() - 1);
        for (Instruction i : instructions) {
            char dir = i.dir;
            if (dir == 'R') {
                if (prevoius.dir == 'U') {
                    size -= x;
                }
                x += i.amount;
            } else if (dir == 'L') {
                if (prevoius.dir == 'D') {
                    size += x + 1;
                }
                x -= i.amount;
            } else if (dir == 'D') {
                if (prevoius.dir == 'R') {
                    size += x + 1;
                }
                // y += i.amount;
                size += (i.amount - 1L) * (x + 1);
            } else if (dir == 'U') {
                if (prevoius.dir == 'L') {
                    size -= x;
                }
                // y -= i.amount;
                size -= (i.amount - 1L) * x;
            } else {
                throw new IllegalArgumentException("dir: " + dir);
            }
            prevoius = i;
        }
        System.out.println("task1: " + size);
    }

}
