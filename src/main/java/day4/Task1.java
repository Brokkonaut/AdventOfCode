package day4;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;

public class Task1 {
    public static void main(String[] args) throws IOException {
        ArrayList<String> input = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/java/day4/data4.txt"), StandardCharsets.UTF_8))) {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                input.add(line);
            }
        }

        int sum = 0;
        int sum2 = 0;
        long[] cardCopies = new long[input.size()];
        HashSet<Integer> drawn = new HashSet<>();
        for (int lineNumber = 0; lineNumber < input.size(); lineNumber++) {
            String line = input.get(lineNumber);
            drawn.clear();

            line = line.substring(5);
            String[] parts = line.split("\\: ");
            // int cardNumber = Integer.parseInt(parts[0].trim());
            String[] drawnAndOwned = parts[1].split(" \\| ");
            int matched = 0;
            for (int i = 0; i < 2; i++) {
                for (String numberString : drawnAndOwned[i].split(" ")) {
                    if (!numberString.isEmpty()) {
                        int number = Integer.parseInt(numberString);
                        if (i == 0) {
                            drawn.add(number);
                        } else if (drawn.contains(number)) {
                            matched++;
                        }
                    }
                }
            }
            if (matched > 0) {
                sum += 1 << (matched - 1);
            }
            long cardsHere = 1 + cardCopies[lineNumber];
            for (int i = 0; i < matched; i++) {
                if (lineNumber + i + 1 < cardCopies.length) {
                    cardCopies[lineNumber + i + 1] += cardsHere;
                }
            }
            sum2 += cardsHere;
        }
        System.out.println(sum);
        System.out.println(sum2);
    }
}
