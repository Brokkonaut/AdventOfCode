package day15;

import java.util.ArrayList;

public class Day15Task2 {
    public record Read(int xsensor, int ysensor, int xbeacon, int ybeacon) {
    }

    public record Range(int min, int max) implements Comparable<Range> {

        @Override
        public int compareTo(Range o) {
            return Integer.compare(min, o.min);
        }
    }

    public static void main(String[] args) {
        int maxrow = 4000000;

        ArrayList<Read> r = new ArrayList<>();
        for (String line : INPUT.split("\n")) {
            line = line.replace("Sensor at x=", "");
            String[] lineParts = line.split(": closest beacon is at x=");
            String[] xySensor = lineParts[0].split(", y=");
            String[] xyBeacon = lineParts[1].split(", y=");

            int xsensor = Integer.parseInt(xySensor[0]);
            int ysensor = Integer.parseInt(xySensor[1]);
            int xbeacon = Integer.parseInt(xyBeacon[0]);
            int ybeacon = Integer.parseInt(xyBeacon[1]);
            r.add(new Read(xsensor, ysensor, xbeacon, ybeacon));
        }

        for (int row = 0; row <= maxrow; row++) {
            if (row % 100000 == 0) {
                System.out.print(".");
            }
            ArrayList<Range> ranges = new ArrayList<>();
            for (Read rr : r) {
                int xsensor = rr.xsensor;
                int ysensor = rr.ysensor;
                int xbeacon = rr.xbeacon;
                int ybeacon = rr.ybeacon;
                new Read(xsensor, ysensor, xbeacon, ybeacon);

                int dist = Math.abs(xsensor - xbeacon) + Math.abs(ysensor - ybeacon);
                int minx = xsensor - dist + Math.abs(ysensor - row);
                int maxx = xsensor + dist - Math.abs(ysensor - row);
                if (minx <= maxx) {
                    if (minx < 0) {
                        minx = 0;
                    }
                    if (maxx > maxrow) {
                        maxx = maxrow;
                    }
                    ranges.add(new Range(minx, maxx));

                }
            }
            ranges.sort(null);
            int minx = Integer.MAX_VALUE;
            int maxx = 0;
            for (Range rrr : ranges) {
                if (rrr.min < minx) {
                    minx = rrr.min;
                }
                if (rrr.min > maxx + 1) {
                    System.out.println();
                    System.out.println((maxx + 1) + "," + row);
                    System.out.println((maxx + 1) * 4000000L + row);
                }
                if (rrr.max > maxx) {
                    maxx = rrr.max;
                }
            }
        }
    }

    public static final String INPUT_TEST = "Sensor at x=2, y=18: closest beacon is at x=-2, y=15\n"
            + "Sensor at x=9, y=16: closest beacon is at x=10, y=16\n"
            + "Sensor at x=13, y=2: closest beacon is at x=15, y=3\n"
            + "Sensor at x=12, y=14: closest beacon is at x=10, y=16\n"
            + "Sensor at x=10, y=20: closest beacon is at x=10, y=16\n"
            + "Sensor at x=14, y=17: closest beacon is at x=10, y=16\n"
            + "Sensor at x=8, y=7: closest beacon is at x=2, y=10\n"
            + "Sensor at x=2, y=0: closest beacon is at x=2, y=10\n"
            + "Sensor at x=0, y=11: closest beacon is at x=2, y=10\n"
            + "Sensor at x=20, y=14: closest beacon is at x=25, y=17\n"
            + "Sensor at x=17, y=20: closest beacon is at x=21, y=22\n"
            + "Sensor at x=16, y=7: closest beacon is at x=15, y=3\n"
            + "Sensor at x=14, y=3: closest beacon is at x=15, y=3\n"
            + "Sensor at x=20, y=1: closest beacon is at x=15, y=3";

    public static final String INPUT = "Sensor at x=1555825, y=18926: closest beacon is at x=1498426, y=-85030\n"
            + "Sensor at x=697941, y=3552290: closest beacon is at x=595451, y=3788543\n"
            + "Sensor at x=3997971, y=2461001: closest beacon is at x=3951198, y=2418718\n"
            + "Sensor at x=3818312, y=282332: closest beacon is at x=4823751, y=1061753\n"
            + "Sensor at x=2874142, y=3053631: closest beacon is at x=3074353, y=3516891\n"
            + "Sensor at x=1704479, y=2132468: closest beacon is at x=1749091, y=2000000\n"
            + "Sensor at x=3904910, y=2080560: closest beacon is at x=3951198, y=2418718\n"
            + "Sensor at x=657061, y=3898803: closest beacon is at x=595451, y=3788543\n"
            + "Sensor at x=3084398, y=3751092: closest beacon is at x=3074353, y=3516891\n"
            + "Sensor at x=2582061, y=972407: closest beacon is at x=1749091, y=2000000\n"
            + "Sensor at x=2886721, y=3971936: closest beacon is at x=3074353, y=3516891\n"
            + "Sensor at x=303399, y=548513: closest beacon is at x=-1010425, y=986825\n"
            + "Sensor at x=3426950, y=2290126: closest beacon is at x=3951198, y=2418718\n"
            + "Sensor at x=3132858, y=3592272: closest beacon is at x=3074353, y=3516891\n"
            + "Sensor at x=3773724, y=3751243: closest beacon is at x=3568452, y=3274758\n"
            + "Sensor at x=3987212, y=2416515: closest beacon is at x=3951198, y=2418718\n"
            + "Sensor at x=61559, y=3806326: closest beacon is at x=595451, y=3788543\n"
            + "Sensor at x=2693503, y=2291389: closest beacon is at x=2269881, y=2661753\n"
            + "Sensor at x=3953437, y=2669220: closest beacon is at x=3951198, y=2418718\n"
            + "Sensor at x=763035, y=3997568: closest beacon is at x=595451, y=3788543\n"
            + "Sensor at x=3999814, y=2370103: closest beacon is at x=3951198, y=2418718\n"
            + "Sensor at x=1290383, y=1696257: closest beacon is at x=1749091, y=2000000\n"
            + "Sensor at x=3505508, y=2805537: closest beacon is at x=3568452, y=3274758\n"
            + "Sensor at x=3276207, y=3323122: closest beacon is at x=3568452, y=3274758\n"
            + "Sensor at x=2244609, y=3517499: closest beacon is at x=3074353, y=3516891\n"
            + "Sensor at x=1370860, y=3436382: closest beacon is at x=595451, y=3788543\n"
            + "Sensor at x=3831063, y=3042662: closest beacon is at x=3568452, y=3274758\n"
            + "Sensor at x=551202, y=3971545: closest beacon is at x=595451, y=3788543\n"
            + "Sensor at x=336629, y=2519780: closest beacon is at x=595451, y=3788543\n"
            + "Sensor at x=2033602, y=2882628: closest beacon is at x=2269881, y=2661753\n"
            + "Sensor at x=3939808, y=2478271: closest beacon is at x=3951198, y=2418718\n"
            + "Sensor at x=1958708, y=2370822: closest beacon is at x=1749091, y=2000000\n"
            + "Sensor at x=3039958, y=3574483: closest beacon is at x=3074353, y=3516891";
}
