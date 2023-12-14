package day5;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Task1 {
    public record Mapping(long from, long to, long count) {
        public boolean matches(long v) {
            return v >= from && v < from + count;
        }

        public long map(long v) {
            return v - from + to;
        }

        public long minMatches(long start) {
            if (from + count <= start) {
                return Long.MAX_VALUE; // no match
            }
            return Math.max(from, start); // match from here
        }
    }

    public record SeedRange(long from, long count) {
        public SeedRange(long from, long count) {
            this.from = from;
            this.count = count;
            if (count < 0) {
                throw new IllegalArgumentException();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> input = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/java/day5/data5.txt"), StandardCharsets.UTF_8))) {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                input.add(line);
            }
        }
        String[] seeds = input.get(0).substring("seeds: ".length()).split(" ");
        long[] values = new long[seeds.length];
        for (int i = 0; i < seeds.length; i++) {
            values[i] = Long.parseLong(seeds[i]);
        }
        ArrayList<SeedRange> ranges = new ArrayList<>();
        for (int i = 0; i < seeds.length; i += 2) {
            ranges.add(new SeedRange(values[i], values[i + 1]));
        }

        ArrayList<Mapping> mappings = new ArrayList<>();
        for (int lineNumber = 2; lineNumber < input.size(); lineNumber++) {
            String line = input.get(lineNumber);
            if (line.endsWith(":")) {
                map(values, mappings);
                ranges = mapRanges(ranges, mappings);
                mappings.clear();
            } else if (!line.isEmpty()) {
                String[] mapings = line.split(" ");
                mappings.add(new Mapping(Long.parseLong(mapings[1]), Long.parseLong(mapings[0]), Long.parseLong(mapings[2])));
            }
        }
        map(values, mappings);
        ranges = mapRanges(ranges, mappings);

        long min = Long.MAX_VALUE;
        for (int i = 0; i < values.length; i++) {
            if (values[i] < min) {
                min = values[i];
            }
        }
        System.out.println("task1: " + min);

        min = Long.MAX_VALUE;
        for (SeedRange r : ranges) {
            if (r.from < min) {
                min = r.from;
            }
        }
        System.out.println("task2: " + min);
    }

    private static ArrayList<SeedRange> mapRanges(ArrayList<SeedRange> ranges, ArrayList<Mapping> mappings) {
        ArrayList<SeedRange> rangesOut = new ArrayList<>();
        valuesFor: for (SeedRange r : ranges) {
            long v = r.from;
            while (true) {
                Mapping best = null;
                long bestStart = Long.MAX_VALUE;
                for (Mapping m : mappings) {
                    long minMatch = m.minMatches(v);
                    if (minMatch < bestStart) {
                        bestStart = minMatch;
                        best = m;
                    }
                }
                if (best != null && bestStart < r.from + r.count) {
                    // create new ranges
                    if (best.from > v) {
                        rangesOut.add(new SeedRange(v, best.from - v)); // unchanged range
                    }
                    long mappedStart = Math.max(best.from, v);
                    long mappedEnd = Math.min(best.from + best.count - 1, r.from + r.count - 1);
                    long mappedCount = mappedEnd - mappedStart + 1;
                    rangesOut.add(new SeedRange(best.map(mappedStart), mappedCount));
                    v = mappedEnd + 1;
                    if (v == r.from + r.count) {
                        continue valuesFor;
                    }
                } else {
                    rangesOut.add(new SeedRange(v, r.from + r.count - v)); // the remaining have no match
                    continue valuesFor;
                }
            }
        }
        return rangesOut;
    }

    private static void map(long[] values, ArrayList<Mapping> mappings) {
        valuesFor: for (int i = 0; i < values.length; i++) {
            long old = values[i];
            for (Mapping m : mappings) {
                if (m.matches(old)) {
                    values[i] = m.map(old);
                    continue valuesFor;
                }
            }
        }
    }
}
