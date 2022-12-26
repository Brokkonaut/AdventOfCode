package day16;

import java.util.HashMap;
import java.util.HashSet;

public class Day16Task1 {
    record Valve(String name, int flow, String[] next) {
    }

    static HashMap<String, Valve> valves = new HashMap<>();
    static HashSet<Valve> visited = new HashSet<>();
    static int max = 0;

    public static void main(String[] args) {

        Valve start = null;
        for (String line : INPUT.split("\n")) {
            line = line.replace("Valve ", "");
            String[] parts1 = line.split(" has flow rate\\=", 2);
            String[] parts2 = parts1[1].split("\\; tunnels lead to valves ");
            if (parts2.length == 1) {
                parts2 = parts1[1].split("\\; tunnel leads to valve ");
            }
            String[] next = parts2[1].split(", ");

            String name = parts1[0];
            int flowRate = Integer.parseInt(parts2[0]);
            Valve v = new Valve(name, flowRate, next);
            valves.put(name, v);
            if (name.equals("AA")) {
                start = v;
            }
            maxRemainingValue += flowRate;
        }
        int time = 30;

        visit(start, time, 0, null);
    }

    static HashSet<Valve> opened = new HashSet<>();
    static int maxRemainingValue = 0;

    record ValveListEntry(ValveListEntry prev, Valve current) {
    }

    public static void visit(Valve v, int timeLeft, int flow, ValveListEntry notTo) {
        if (flow > max) {
            max = flow;
            System.out.println(flow);
        }

        if (timeLeft > 2 && (flow + (timeLeft - 2) * maxRemainingValue > max)) {
            fffff: for (String nextName : v.next) {
                ValveListEntry first = notTo;
                while (first != null) {
                    if (nextName.equals(first.current.name)) {
                        continue fffff;
                    }
                    first = first.prev;
                }

                Valve n = valves.get(nextName);
                if (!visited.contains(n)) {
                    visit(n, timeLeft - 1, flow, new ValveListEntry(notTo, v));

                    if (!opened.contains(n)) {
                        opened.add(n);
                        maxRemainingValue -= n.flow;
                        int nextFlow = flow + n.flow * (timeLeft - 2);
                        visit(n, timeLeft - 2, nextFlow, null);
                        maxRemainingValue += n.flow;
                        opened.remove(n);
                    }
                }
            }
        }
    }

    public static final String INPUT_TEST = "Valve AA has flow rate=0; tunnels lead to valves DD, II, BB\n"
            + "Valve BB has flow rate=13; tunnels lead to valves CC, AA\n"
            + "Valve CC has flow rate=2; tunnels lead to valves DD, BB\n"
            + "Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE\n"
            + "Valve EE has flow rate=3; tunnels lead to valves FF, DD\n"
            + "Valve FF has flow rate=0; tunnels lead to valves EE, GG\n"
            + "Valve GG has flow rate=0; tunnels lead to valves FF, HH\n"
            + "Valve HH has flow rate=22; tunnel leads to valve GG\n"
            + "Valve II has flow rate=0; tunnels lead to valves AA, JJ\n"
            + "Valve JJ has flow rate=21; tunnel leads to valve II";

    public static final String INPUT = "Valve EV has flow rate=0; tunnels lead to valves WG, IB\n"
            + "Valve IB has flow rate=0; tunnels lead to valves EW, EV\n"
            + "Valve KL has flow rate=0; tunnels lead to valves JH, OY\n"
            + "Valve QJ has flow rate=0; tunnels lead to valves TX, JH\n"
            + "Valve OA has flow rate=12; tunnels lead to valves SB, GI, ED\n"
            + "Valve BQ has flow rate=0; tunnels lead to valves NK, JJ\n"
            + "Valve PZ has flow rate=0; tunnels lead to valves JH, VA\n"
            + "Valve QO has flow rate=8; tunnels lead to valves LN, LU, CU, SQ, YZ\n"
            + "Valve MP has flow rate=0; tunnels lead to valves LN, GO\n"
            + "Valve YZ has flow rate=0; tunnels lead to valves AA, QO\n"
            + "Valve CU has flow rate=0; tunnels lead to valves RY, QO\n"
            + "Valve UE has flow rate=16; tunnel leads to valve VP\n"
            + "Valve HT has flow rate=0; tunnels lead to valves AA, JE\n"
            + "Valve EF has flow rate=0; tunnels lead to valves ES, JE\n"
            + "Valve JJ has flow rate=15; tunnel leads to valve BQ\n"
            + "Valve JX has flow rate=0; tunnels lead to valves AA, GO\n"
            + "Valve AA has flow rate=0; tunnels lead to valves JX, TX, HT, YZ\n"
            + "Valve MI has flow rate=21; tunnels lead to valves PQ, QT\n"
            + "Valve ES has flow rate=0; tunnels lead to valves EF, NK\n"
            + "Valve VC has flow rate=0; tunnels lead to valves MC, IW\n"
            + "Valve LN has flow rate=0; tunnels lead to valves MP, QO\n"
            + "Valve ED has flow rate=0; tunnels lead to valves OA, RY\n"
            + "Valve WG has flow rate=20; tunnels lead to valves EV, OY, KF\n"
            + "Valve GI has flow rate=0; tunnels lead to valves WE, OA\n"
            + "Valve UK has flow rate=0; tunnels lead to valves TO, JE\n"
            + "Valve GY has flow rate=23; tunnels lead to valves EO, QT\n"
            + "Valve TX has flow rate=0; tunnels lead to valves AA, QJ\n"
            + "Valve OE has flow rate=0; tunnels lead to valves GO, NK\n"
            + "Valve OQ has flow rate=9; tunnels lead to valves VP, SB\n"
            + "Valve NK has flow rate=25; tunnels lead to valves OE, ES, BQ\n"
            + "Valve LU has flow rate=0; tunnels lead to valves JH, QO\n"
            + "Valve RY has flow rate=18; tunnels lead to valves ED, IW, CU\n"
            + "Valve KF has flow rate=0; tunnels lead to valves JE, WG\n"
            + "Valve IW has flow rate=0; tunnels lead to valves VC, RY\n"
            + "Valve SQ has flow rate=0; tunnels lead to valves MC, QO\n"
            + "Valve PQ has flow rate=0; tunnels lead to valves MC, MI\n"
            + "Valve TO has flow rate=0; tunnels lead to valves UK, JH\n"
            + "Valve OY has flow rate=0; tunnels lead to valves KL, WG\n"
            + "Valve JE has flow rate=10; tunnels lead to valves EF, ND, HT, KF, UK\n"
            + "Valve JH has flow rate=3; tunnels lead to valves QJ, KL, PZ, TO, LU\n"
            + "Valve VP has flow rate=0; tunnels lead to valves OQ, UE\n"
            + "Valve EW has flow rate=22; tunnel leads to valve IB\n"
            + "Valve ND has flow rate=0; tunnels lead to valves JE, GO\n"
            + "Valve VA has flow rate=0; tunnels lead to valves GO, PZ\n"
            + "Valve QT has flow rate=0; tunnels lead to valves MI, GY\n"
            + "Valve EO has flow rate=0; tunnels lead to valves GY, MC\n"
            + "Valve MC has flow rate=11; tunnels lead to valves PQ, SQ, WE, EO, VC\n"
            + "Valve GO has flow rate=4; tunnels lead to valves JX, VA, OE, MP, ND\n"
            + "Valve SB has flow rate=0; tunnels lead to valves OQ, OA\n"
            + "Valve WE has flow rate=0; tunnels lead to valves MC, GI";
}
