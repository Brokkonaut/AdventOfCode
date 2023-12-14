package day2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Task2 {
    public static void main(String[] args) throws IOException {
        ArrayList<String> input = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/java/day2/data2.txt"), StandardCharsets.UTF_8))) {
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
            int red = 0;
            int green = 0;
            int blue = 0;
            line = line.substring(5);
            String[] parts = line.split("\\: ");
            for (String pull : parts[1].split("\\; ")) {
                for (String countAndType : pull.split("\\, ")) {
                    String[] ctsplit = countAndType.split(" ");
                    int count = Integer.parseInt(ctsplit[0]);
                    String type = ctsplit[1];
                    if (type.equals("red")) {
                        if (count > red) {
                            red = count;
                        }
                    } else if (type.equals("green")) {
                        if (count > green) {
                            green = count;
                        }
                    } else if (type.equals("blue")) {
                        if (count > blue) {
                            blue = count;
                        }
                    }
                }
            }
            int power = red * green * blue;
            sum += power;
        }
        System.out.println(sum);
    }
}
