package day8;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class Task2 {

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
        ArrayList<PathNode> currentList = new ArrayList<>();
        String instructions = input.get(0);
        for (int i = 2; i < input.size(); i++) {
            String line = input.get(i);
            String[] parts = line.replace(")", "").replace(" = (", ", ").split(", ");
            PathNode node = new PathNode(parts[0], parts[1], parts[2]);
            nodes.put(node.id, node);
            if (node.id.charAt(2) == 'A') {
                currentList.add(node);
            }
        }

        // this works, but is not realy correct for the task
        int numNodes = currentList.size();
        ArrayList<Long> stepsList = new ArrayList<>();
        for (int i = 0; i < numNodes; i++) {
            PathNode current = currentList.get(i);
            int currentInstruction = 0;
            long steps = 0;
            while (true) {
                char inst = instructions.charAt(currentInstruction);
                current = nodes.get(inst == 'L' ? current.left : current.right);
                steps++;
                currentInstruction = (currentInstruction + 1) % instructions.length();
                if (current.id.charAt(2) == 'Z') {
                    // System.out.println(steps + " " + (steps % instructions.length()));
                    stepsList.add(steps);
                    break;
                }
            }
        }
        long v = stepsList.get(0);
        for (int i = 1; i < stepsList.size(); i++) {
            v = kgV(v, stepsList.get(i));
        }

        // int currentInstruction = 0;
        // long steps = 0;
        // int matched = 0;
        // while (matched < numNodes) {
        // matched = 0;
        // char inst = instructions.charAt(currentInstruction);
        // for (int i = 0; i < numNodes; i++) {
        // PathNode n = currentList.get(i);
        // n = nodes.get(inst == 'L' ? n.left : n.right);
        // currentList.set(i, n);
        // if (n.id.charAt(2) == 'Z') {
        // matched++;
        // }
        // }
        // steps++;
        // currentInstruction = (currentInstruction + 1) % instructions.length();
        // }

        System.out.println("task2: " + v);
    }

    static long ggT(long a, long b) {
        if (a == b | b == 0) {
            return a;
        } else {
            return ggT(b, a % b);
        }
    }

    static long kgV(long a, long b) {
        return a * (b / ggT(a, b));
    }
}
