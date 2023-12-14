package day2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Task1 {
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
        nextline: for (String line : input) {
            int red = 12;
            int green = 13;
            int blue = 14;
            line = line.substring(5);
            String[] parts = line.split("\\: ");
            int gameNumber = Integer.parseInt(parts[0]);
            for (String pull : parts[1].split("\\; ")) {
                for (String countAndType : pull.split("\\, ")) {
                    String[] ctsplit = countAndType.split(" ");
                    int count = Integer.parseInt(ctsplit[0]);
                    String type = ctsplit[1];
                    if (type.equals("red")) {
                        if (count > red) {
                            continue nextline;
                        }
                    } else if (type.equals("green")) {
                        if (count > green) {
                            continue nextline;
                        }
                    } else if (type.equals("blue")) {
                        if (count > blue) {
                            continue nextline;
                        }
                    }
                }
            }
            sum += gameNumber;
        }
        System.out.println(sum);
    }
}
