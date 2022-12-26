package day16;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;

public class Day16Task2 {

    record Valve(int id, int fullid, String name, int flow, String[] next, int[] paths) {
        @Override
        public String toString() {
            return name + Arrays.toString(paths);
        }
    }

    static int max = 0;
    static Valve[] valvesWithFlow;

    public static void main(String[] args) {
        String in = INPUT;

        HashMap<String, Valve> valves = new HashMap<>();
        Valve[] allValves;

        int flowNodes = 0;
        int allNodes = 0;
        for (String line : in.split("\n")) {
            line = line.replace("Valve ", "");
            String[] parts1 = line.split(" has flow rate\\=", 2);
            String[] parts2 = parts1[1].split("\\; tunnels lead to valves ");
            if (parts2.length == 1) {
                parts2 = parts1[1].split("\\; tunnel leads to valve ");
            }
            String name = parts1[0];
            int flowRate = Integer.parseInt(parts2[0]);
            if (flowRate > 0 || name.equals("AA")) {
                flowNodes++;
            }
            allNodes++;
        }
        valvesWithFlow = new Valve[flowNodes];
        allValves = new Valve[allNodes];
        opened = new boolean[valvesWithFlow.length];

        Valve start = null;
        int nr = 0;
        int fullNr = 0;
        for (String line : in.split("\n")) {
            line = line.replace("Valve ", "");
            String[] parts1 = line.split(" has flow rate\\=", 2);
            String[] parts2 = parts1[1].split("\\; tunnels lead to valves ");
            if (parts2.length == 1) {
                parts2 = parts1[1].split("\\; tunnel leads to valve ");
            }
            String[] next = parts2[1].split(", ");

            String name = parts1[0];
            int flowRate = Integer.parseInt(parts2[0]);
            int myNr = flowRate > 0 || name.equals("AA") ? nr++ : -1;
            int myfullnr = fullNr++;
            Valve v = new Valve(myNr, myfullnr, name, flowRate, next, new int[flowNodes]);
            if (myNr >= 0) {
                valvesWithFlow[myNr] = v;
            }
            allValves[myfullnr] = v;
            valves.put(name, v);
            if (name.equals("AA")) {
                start = v;
            }
            maxRemainingValue += flowRate;
        }

        ArrayDeque<Integer> border = new ArrayDeque<>();
        ArrayDeque<Integer> borderNext = new ArrayDeque<>();
        for (Valve v : valvesWithFlow) {
            border.add(v.fullid);
            boolean[] seen = new boolean[fullNr];
            seen[v.fullid] = true;

            int pathLength = 0;
            while (!border.isEmpty()) {
                while (!border.isEmpty()) {
                    int fullid = border.remove();
                    Valve va = allValves[fullid];
                    if (va.id >= 0) {
                        v.paths[va.id] = pathLength;
                    }
                    for (String next : va.next) {
                        Valve to = valves.get(next);
                        if (!seen[to.fullid]) {
                            seen[to.fullid] = true;
                            borderNext.add(to.fullid);
                        }
                    }
                }
                ArrayDeque<Integer> temp = borderNext;
                borderNext = border;
                border = temp;

                pathLength++;
            }
        }

        for (Valve e : valvesWithFlow) {
            System.out.println(e);
        }

        int time = 26;

        opened[start.id] = true;
        visit(start, 0, start, 0, time, 0);

    }

    static boolean[] opened;
    static int maxRemainingValue = 0;

    record ValveListEntry(ValveListEntry prev, Valve current) {
    }

    public static void visit(Valve v, int moveTime, Valve vel, int moveTimeEl, int timeLeft, int flow) {
        if (flow > max) {
            max = flow;
            System.out.println(flow + " " + Arrays.toString(opened));
        }
        if (flow + maxRemainingValue * timeLeft < max) {
            return;
        }

        if (timeLeft >= 1) {
            if (moveTime > 0 && moveTimeEl > 0) {
                // both are still moving
                visit(v, moveTime - 1, vel, moveTimeEl - 1, timeLeft - 1, flow);
            } else if (moveTime == 0) {
                if (moveTimeEl == 0) {
                    // both look for a new target
                    for (int i = 0; i <= opened.length; i++) { // i == opened.length -> i have no target
                        if (i == opened.length || !opened[i]) {
                            if (i < opened.length) {
                                opened[i] = true;
                                maxRemainingValue -= valvesWithFlow[i].flow;
                            }
                            int myMoveTime = (i < opened.length) ? v.paths[i] + 1 : 0;
                            if (timeLeft - myMoveTime > 0) {
                                int mynewflow = (i < opened.length) ? (timeLeft - myMoveTime) * valvesWithFlow[i].flow : 0;
                                flow += mynewflow;
                                for (int j = 0; j < opened.length; j++) {
                                    if (!opened[j]) {
                                        opened[j] = true;
                                        maxRemainingValue -= valvesWithFlow[j].flow;
                                        int elMoveTime = vel.paths[j] + 1;
                                        if (timeLeft - elMoveTime > 0) {
                                            int elnewflow = (timeLeft - elMoveTime) * valvesWithFlow[j].flow;
                                            flow += elnewflow;
                                            // i move to i, el moves to j
                                            visit((i < opened.length) ? valvesWithFlow[i] : null, (i < opened.length) ? myMoveTime - 1 : (timeLeft + 10), valvesWithFlow[j], elMoveTime - 1, timeLeft - 1, flow);

                                            flow -= elnewflow;
                                        }
                                        maxRemainingValue += valvesWithFlow[j].flow;
                                        opened[j] = false;
                                    }
                                }
                                if (i < opened.length) {
                                    visit(valvesWithFlow[i], myMoveTime - 1, null, timeLeft + 10, timeLeft - 1, flow); // el has no target
                                }

                                flow -= mynewflow;
                            }
                            if (i < opened.length) {
                                maxRemainingValue += valvesWithFlow[i].flow;
                                opened[i] = false;
                            }
                        }
                    }
                } else {
                    // i look for a new target

                    for (int i = 0; i < opened.length; i++) {
                        if (!opened[i]) {
                            opened[i] = true;
                            maxRemainingValue -= valvesWithFlow[i].flow;
                            int myMoveTime = v.paths[i] + 1;
                            if (timeLeft - myMoveTime > 0) {
                                int mynewflow = (timeLeft - myMoveTime) * valvesWithFlow[i].flow;
                                flow += mynewflow;
                                // i move to i
                                visit(valvesWithFlow[i], myMoveTime - 1, vel, moveTimeEl - 1, timeLeft - 1, flow);

                                flow -= mynewflow;
                            }
                            maxRemainingValue += valvesWithFlow[i].flow;
                            opened[i] = false;
                        }
                    }
                    visit(null, timeLeft + 10, vel, moveTimeEl - 1, timeLeft - 1, flow); // option: i have no target
                }
            } else { // moveTimeEl == 0
                // el looks for a new target

                for (int i = 0; i < opened.length; i++) {
                    if (!opened[i]) {
                        opened[i] = true;
                        maxRemainingValue -= valvesWithFlow[i].flow;
                        int elMoveTime = vel.paths[i] + 1;
                        if (timeLeft - elMoveTime > 0) {
                            int elnewflow = (timeLeft - elMoveTime) * valvesWithFlow[i].flow;
                            flow += elnewflow;
                            // i move to i
                            visit(v, moveTime - 1, valvesWithFlow[i], elMoveTime - 1, timeLeft - 1, flow);

                            flow -= elnewflow;
                        }
                        maxRemainingValue += valvesWithFlow[i].flow;
                        opened[i] = false;
                    }
                }
                visit(v, moveTime - 1, null, timeLeft + 10, timeLeft - 1, flow); // option: el has no target
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
