package day11;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Map;

public class Monkey {
    private final int id;
    private final ArrayList<BigInteger> items;
    private final int divisibleTest;
    private final boolean operationMultiply; // false: add
    private final boolean operationOperand0Old;
    private final int operationOperand0Immediate;
    private final boolean operationOperand1Old;
    private final int operationOperand1Immediate;
    private final int targetWhenTrue;
    private final int targetWhenFalse;
    private int inspections;

    public Monkey(String data) {
        String[] lines = data.split("\n");
        id = Integer.parseInt(lines[0].substring(7, lines[0].length() - 1));
        items = new ArrayList<>();
        for (String itemValue : lines[1].replace("  Starting items: ", "").split(", ")) {
            items.add(BigInteger.valueOf(Integer.parseInt(itemValue)));
        }
        String operationString = lines[2].replace("  Operation: new = ", "");
        operationMultiply = operationString.contains(" * ");
        String[] operationStringPart = operationMultiply ? operationString.split(" \\* ") : operationString.split(" \\+ ");
        operationOperand0Old = operationStringPart[0].equals("old");
        operationOperand0Immediate = operationOperand0Old ? 0 : Integer.parseInt(operationStringPart[0]);

        operationOperand1Old = operationStringPart[1].equals("old");
        operationOperand1Immediate = operationOperand1Old ? 0 : Integer.parseInt(operationStringPart[1]);

        divisibleTest = Integer.parseInt(lines[3].replace("  Test: divisible by ", ""));
        targetWhenTrue = Integer.parseInt(lines[4].replace("    If true: throw to monkey ", ""));
        targetWhenFalse = Integer.parseInt(lines[5].replace("    If false: throw to monkey ", ""));
    }

    public int getId() {
        return id;
    }

    public void act(Map<Integer, Monkey> monkeys, boolean noWorry, long divisor) {
        ArrayList<BigInteger> itemsIn = new ArrayList<>(items);
        items.clear();
        for (BigInteger item : itemsIn) {
            inspections++;
            BigInteger wopperand0 = operationOperand0Old ? item : BigInteger.valueOf(operationOperand0Immediate);
            BigInteger wopperand1 = operationOperand1Old ? item : BigInteger.valueOf(operationOperand1Immediate);
            BigInteger newWorry = operationMultiply ? wopperand0.multiply(wopperand1) : wopperand0.add(wopperand1);
            if (noWorry) {
                newWorry = newWorry.divide(BigInteger.valueOf(3));
            } else {
                newWorry = newWorry.remainder(BigInteger.valueOf(divisor));
            }
            monkeys.get((newWorry.remainder(BigInteger.valueOf(divisibleTest)) == BigInteger.ZERO) ? targetWhenTrue : targetWhenFalse).addItem(newWorry);
        }
    }

    private void addItem(BigInteger itemWorry) {
        items.add(itemWorry);
    }

    @Override
    public String toString() {
        return "Monkey " + id + ": " + items;
    }

    public int getInspections() {
        return inspections;
    }

    public int getDivisibleTest() {
        return divisibleTest;
    }
}
