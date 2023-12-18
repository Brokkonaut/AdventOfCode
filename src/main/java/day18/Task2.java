package day18;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Task2 {
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
            int amount = Integer.parseInt(parts[2].substring(0, parts[2].length() - 1), 16);
            char dir = parts[2].charAt(parts[2].length() - 1);
            instructions.add(new Instruction(dir, amount));
        }

        long size = 0;

        int x = 0;
        // int y = 0;
        Instruction prevoius = instructions.get(instructions.size() - 1);
        for (Instruction i : instructions) {
            char dir = i.dir;
            if (dir == '0') {
                if (prevoius.dir == '3') {
                    size -= x;
                }
                x += i.amount;
            } else if (dir == '2') {
                if (prevoius.dir == '1') {
                    size += x + 1;
                }
                x -= i.amount;
            } else if (dir == '1') {
                if (prevoius.dir == '0') {
                    size += x + 1;
                }
                // y += i.amount;
                size += (i.amount - 1L) * (x + 1);
            } else if (dir == '3') {
                if (prevoius.dir == '2') {
                    size -= x;
                }
                // y -= i.amount;
                size -= (i.amount - 1L) * x;
            } else {
                throw new IllegalArgumentException("dir: " + dir);
            }
            prevoius = i;
        }
        System.out.println("task2: " + size);
    }

}
