package day7;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Task2 {
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
        int jokers = cardCount[0];
        boolean have5 = false;
        boolean have4 = false;
        boolean have3 = false;
        int count2 = 0;
        for (int i = 1; i < 14; i++) {// start from 1 -> no jokers
            int cc = cardCount[i];
            if (cc == 5) {
                have5 = true;
            } else if (cc == 4) {
                have4 = true;
            } else if (cc == 3) {
                have3 = true;
            } else if (cc == 2) {
                count2++;
            }
        }
        if (have5 || have4 && jokers == 1 || have3 && jokers == 2 || count2 == 1 && jokers == 3 || jokers >= 4) {
            return 7; // 5 of a kind
        }
        if (have4 || have3 && jokers == 1 || count2 == 1 && jokers == 2 || jokers == 3) {
            return 6; // 4 of a kind
        }
        if (have3 && count2 == 1 || count2 == 2 && jokers == 1 || count2 == 1 && jokers == 2) {
            return 5; // full house
        }
        if (have3 || count2 == 1 && jokers == 1 || jokers == 2) {
            return 4; // 3 of a kind
        }
        if (count2 == 2) {
            return 3; // 2 pair
        }
        if (count2 == 1 || jokers == 1) {
            return 2; // one pair
        }
        return 1;
    }

    static int[] cardValues = new int[256];

    static {
        cardValues['J'] = 0;
        cardValues['2'] = 1;
        cardValues['3'] = 2;
        cardValues['4'] = 3;
        cardValues['5'] = 4;
        cardValues['6'] = 5;
        cardValues['7'] = 6;
        cardValues['8'] = 7;
        cardValues['9'] = 8;
        cardValues['T'] = 9;
        cardValues['Q'] = 10;
        cardValues['K'] = 11;
        cardValues['A'] = 12;
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

        System.out.println("task2: " + sum);
    }
}
