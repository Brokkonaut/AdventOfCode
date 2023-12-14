package day8;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class Task1 {

    record PathNode(String id, String left, String right) {

    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> input = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/java/day8/data8.txt"), StandardCharsets.UTF_8))) {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                input.add(line);
            }
        }

        HashMap<String, PathNode> nodes = new HashMap<>();
        String instructions = input.get(0);
        for (int i = 2; i < input.size(); i++) {
            String line = input.get(i);
            String[] parts = line.replace(")", "").replace(" = (", ", ").split(", ");
            PathNode node = new PathNode(parts[0], parts[1], parts[2]);
            nodes.put(node.id, node);
        }

        PathNode current = nodes.get("AAA");
        int currentInstruction = 0;
        long steps = 0;
        while (!current.id.equals("ZZZ")) {
            char inst = instructions.charAt(currentInstruction);
            current = nodes.get(inst == 'L' ? current.left : current.right);
            steps++;
            currentInstruction = (currentInstruction + 1) % instructions.length();
        }

        System.out.println("task1: " + steps);
    }
}
