package day7;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Task1 {
    public record HandAndBid(String hand, int bid, int typevalue) implements Comparable<HandAndBid> {

        public HandAndBid(String hand, int bid) {
            this(hand, bid, calculateTypeValue(hand));
        }

        @Override
        public int compareTo(HandAndBid o) {
            int c = Integer.compare(typevalue, o.typevalue);
            if (c != 0) {
                return c;
            }
            for (int i = 0; i < 5; i++) {
                c = Integer.compare(cardValues[hand.charAt(i)], cardValues[o.hand.charAt(i)]);
                if (c != 0) {
                    return c;
                }
            }
            return 0;
        }
    }

    public static int calculateTypeValue(String hand) {
        int[] cardCount = new int[14];
        for (int i = 0; i < 5; i++) {
            cardCount[cardValues[hand.charAt(i)]]++;
        }
        boolean have3 = false;
        int count2 = 0;
        for (int i = 0; i < 14; i++) {
            int cc = cardCount[i];
            if (cc == 5) {
                return 7; // 5 of a kind
            } else if (cc == 4) {
                return 6; // 4 of a kind
            } else if (cc == 3) {
                have3 = true;
            } else if (cc == 2) {
                count2++;
            }
        }
        if (have3) {
            return count2 > 0 ? 5 : 4; // full house or three of a kind
        }
        return count2 + 1; // two pairs, one pair, no pair
    }

    static int[] cardValues = new int[256];

    static {
        cardValues['2'] = 1;
        cardValues['3'] = 2;
        cardValues['4'] = 3;
        cardValues['5'] = 4;
        cardValues['6'] = 5;
        cardValues['7'] = 6;
        cardValues['8'] = 7;
        cardValues['9'] = 8;
        cardValues['T'] = 9;
        cardValues['J'] = 10;
        cardValues['Q'] = 11;
        cardValues['K'] = 12;
        cardValues['A'] = 13;
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> input = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/java/day7/data7.txt"), StandardCharsets.UTF_8))) {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                input.add(line);
            }
        }

        ArrayList<HandAndBid> handAndBids = new ArrayList<>();
        for (String line : input) {
            String[] lineParts = line.split(" ");
            handAndBids.add(new HandAndBid(lineParts[0], Integer.parseInt(lineParts[1])));
        }
        handAndBids.sort(null);
        long sum = 0;
        for (int i = 0; i < handAndBids.size(); i++) {
            sum += (i + 1) * handAndBids.get(i).bid;
        }

        System.out.println("task1: " + sum);
    }
}
