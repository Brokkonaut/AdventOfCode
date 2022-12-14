package day11;

import java.util.ArrayList;
import java.util.TreeMap;

public class Day11Task1 {
    public static void main(String[] args) {
        for (int task = 0; task < 2; task++) {
            long divisor = 1;
            TreeMap<Integer, Monkey> monkeys = new TreeMap<>();
            for (String monkeyData : INPUT.split("\n\n")) {
                Monkey monkey = new Monkey(monkeyData);
                monkeys.put(monkey.getId(), monkey);
                divisor = kgv(divisor, monkey.getDivisibleTest());
            }
            System.out.println("Divisor: " + divisor);
            for (int i = 0; i < (task == 0 ? 20 : 10000); i++) {
                for (Monkey m : monkeys.values()) {
                    m.act(monkeys, task == 0, divisor);
                }
                System.out.println("Round " + (i + 1));
                if (task == 0) {
                    for (Monkey m : monkeys.values()) {
                        System.out.println("  " + m.toString());
                    }
                }
            }
            System.out.println("Inspections");
            for (Monkey m : monkeys.values()) {
                System.out.println("  Monkey " + m.getId() + ": " + m.getInspections());
            }
            ArrayList<Monkey> sortedMonkeys = new ArrayList<>(monkeys.values());
            sortedMonkeys.sort((m1, m2) -> Integer.compare(m2.getInspections(), m1.getInspections()));
            System.out.println("Inspectionthing: " + ((long) sortedMonkeys.get(0).getInspections() * sortedMonkeys.get(1).getInspections()));
        }
    }

    public static long kgv(long x, long y) {
        if (x < 0 || y < 0) {
            return -1;
        }
        if (x == y) {
            return x;
        }
        if (x == 1) {
            return y;
        }
        if (y == 1) {
            return x;
        }

        long x1 = x, y1 = y;
        while (x != y) {
            if (x < y) {
                x += x1;
            } else {
                y += y1;
            }
        }

        return x;
    }

    private static final String INPUT_TEST = "Monkey 0:\n"
            + "  Starting items: 79, 98\n"
            + "  Operation: new = old * 19\n"
            + "  Test: divisible by 23\n"
            + "    If true: throw to monkey 2\n"
            + "    If false: throw to monkey 3\n"
            + "\n"
            + "Monkey 1:\n"
            + "  Starting items: 54, 65, 75, 74\n"
            + "  Operation: new = old + 6\n"
            + "  Test: divisible by 19\n"
            + "    If true: throw to monkey 2\n"
            + "    If false: throw to monkey 0\n"
            + "\n"
            + "Monkey 2:\n"
            + "  Starting items: 79, 60, 97\n"
            + "  Operation: new = old * old\n"
            + "  Test: divisible by 13\n"
            + "    If true: throw to monkey 1\n"
            + "    If false: throw to monkey 3\n"
            + "\n"
            + "Monkey 3:\n"
            + "  Starting items: 74\n"
            + "  Operation: new = old + 3\n"
            + "  Test: divisible by 17\n"
            + "    If true: throw to monkey 0\n"
            + "    If false: throw to monkey 1";

    private static final String INPUT = "Monkey 0:\n"
            + "  Starting items: 63, 84, 80, 83, 84, 53, 88, 72\n"
            + "  Operation: new = old * 11\n"
            + "  Test: divisible by 13\n"
            + "    If true: throw to monkey 4\n"
            + "    If false: throw to monkey 7\n"
            + "\n"
            + "Monkey 1:\n"
            + "  Starting items: 67, 56, 92, 88, 84\n"
            + "  Operation: new = old + 4\n"
            + "  Test: divisible by 11\n"
            + "    If true: throw to monkey 5\n"
            + "    If false: throw to monkey 3\n"
            + "\n"
            + "Monkey 2:\n"
            + "  Starting items: 52\n"
            + "  Operation: new = old * old\n"
            + "  Test: divisible by 2\n"
            + "    If true: throw to monkey 3\n"
            + "    If false: throw to monkey 1\n"
            + "\n"
            + "Monkey 3:\n"
            + "  Starting items: 59, 53, 60, 92, 69, 72\n"
            + "  Operation: new = old + 2\n"
            + "  Test: divisible by 5\n"
            + "    If true: throw to monkey 5\n"
            + "    If false: throw to monkey 6\n"
            + "\n"
            + "Monkey 4:\n"
            + "  Starting items: 61, 52, 55, 61\n"
            + "  Operation: new = old + 3\n"
            + "  Test: divisible by 7\n"
            + "    If true: throw to monkey 7\n"
            + "    If false: throw to monkey 2\n"
            + "\n"
            + "Monkey 5:\n"
            + "  Starting items: 79, 53\n"
            + "  Operation: new = old + 1\n"
            + "  Test: divisible by 3\n"
            + "    If true: throw to monkey 0\n"
            + "    If false: throw to monkey 6\n"
            + "\n"
            + "Monkey 6:\n"
            + "  Starting items: 59, 86, 67, 95, 92, 77, 91\n"
            + "  Operation: new = old + 5\n"
            + "  Test: divisible by 19\n"
            + "    If true: throw to monkey 4\n"
            + "    If false: throw to monkey 0\n"
            + "\n"
            + "Monkey 7:\n"
            + "  Starting items: 58, 83, 89\n"
            + "  Operation: new = old * 19\n"
            + "  Test: divisible by 17\n"
            + "    If true: throw to monkey 2\n"
            + "    If false: throw to monkey 1";
}
