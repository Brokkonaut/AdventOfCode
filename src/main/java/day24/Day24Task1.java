package day24;

import java.util.ArrayList;
import java.util.Arrays;

public class Day24Task1 {
    public static void main(String[] args) {
        String[] lines = INPUT.split("\n");

        // for (int perfRun = 0; perfRun < 10; perfRun++) {
        // long t0 = System.currentTimeMillis();

        int height = lines.length - 2;
        int width = lines[1].length() - 2;
        ArrayList<Blizzard> blizzards = new ArrayList<>();

        for (int line = 1; line < lines.length - 1; line++) {
            char[] lineString = lines[line].toCharArray();
            for (int cha = 1; cha < lineString.length - 1; cha++) {
                if (lineString[cha] != '.') {
                    blizzards.add(new Blizzard(cha - 1, line - 1, lineString[cha]));
                }
            }
        }

        int reachBottom = -1;
        int reachTop = -1;
        int reachBottom2 = -1;

        boolean[] blizzardFields = new boolean[height * width];
        boolean[] reachable = new boolean[height * width];
        boolean[] nextReachable = new boolean[height * width];
        int round = 1;
        while (true) {
            // update where blizzards are
            Arrays.fill(blizzardFields, false);
            for (Blizzard b : blizzards) {
                b.tick(width, height);
                blizzardFields[b.x + b.y * width] = true;
            }
            if (reachBottom < 0 || (reachTop >= 0 && reachBottom2 < 0)) {
                if (!blizzardFields[0]) {
                    nextReachable[0] = true;
                }
            }
            if (reachBottom >= 0 && reachTop < 0) {
                if (!blizzardFields[blizzardFields.length - 1]) {
                    nextReachable[blizzardFields.length - 1] = true;
                }
            }

            // update reachable positions
            for (int f = 0; f < reachable.length; f++) {
                if (reachable[f]) {
                    int x = f % width;
                    int y = f / width;
                    if (!blizzardFields[x + y * width]) {
                        nextReachable[x + y * width] = true;
                    }
                    if (x > 0 && !blizzardFields[x - 1 + y * width]) {
                        nextReachable[x - 1 + y * width] = true;
                    }
                    if (y > 0 && !blizzardFields[x + (y - 1) * width]) {
                        nextReachable[x + (y - 1) * width] = true;
                    }
                    if (x < width - 1 && !blizzardFields[x + 1 + y * width]) {
                        nextReachable[x + 1 + y * width] = true;
                    }
                    if (y < height - 1 && !blizzardFields[x + (y + 1) * width]) {
                        nextReachable[x + (y + 1) * width] = true;
                    }
                }
            }

            round++;
            if (reachBottom < 0 && nextReachable[nextReachable.length - 1]) {
                System.out.println("bottom: " + round);
                reachBottom = round;
                Arrays.fill(nextReachable, false);
                // break;
            } else if (reachBottom >= 0 && reachTop < 0 && nextReachable[0]) {
                System.out.println("top: " + round);
                reachTop = round;
                Arrays.fill(nextReachable, false);
                // break;
            } else if (reachBottom >= 0 && reachTop >= 0 && nextReachable[nextReachable.length - 1]) {
                System.out.println("bottom 2: " + round);
                reachBottom2 = round;
                Arrays.fill(nextReachable, false);
                break;
            }

            boolean[] temp = reachable;
            reachable = nextReachable;
            nextReachable = temp;
            Arrays.fill(nextReachable, false);
            // printField(blizzardFields, reachable, width);
            // System.out.println();

            // }
            // System.out.println("time: " + (System.currentTimeMillis() - t0));
        }
    }

    // private static void printField(boolean[] blizzardFields, boolean[] reachable, int width) {
    // for (int i = 0; i < blizzardFields.length; i++) {
    // if (blizzardFields[i]) {
    // System.out.print('#');
    // } else if (reachable[i]) {
    // System.out.print('E');
    // } else {
    // System.out.print('.');
    // }
    // if ((i + 1) % width == 0) {
    // System.out.println();
    // }
    // }
    // }

    private static class Blizzard {
        private int x;
        private int y;
        private int dx;
        private int dy;

        public Blizzard(int x, int y, int dx, int dy) {
            this.x = x;
            this.y = y;
            this.dx = dx;
            this.dy = dy;
        }

        public Blizzard(int x, int y, char c) {
            this(x, y, c == '>' ? 1 : c == '<' ? -1 : 0, c == 'v' ? 1 : c == '^' ? -1 : 0);
        }

        public void tick(int width, int height) {
            x = Math.floorMod(x + dx, width);
            y = Math.floorMod(y + dy, height);
        }
    }

    public static final String INPUT_TEST = "#.######\n"
            + "#>>.<^<#\n"
            + "#.<..<<#\n"
            + "#>v.><>#\n"
            + "#<^v^^>#\n"
            + "######.#";

    public static final String INPUT = "#.####################################################################################################\n"
            + "#<v>v>v><>^<>^.^vv<>><>>v.>v<.v>>v<<v<.^<>>>v<v^>.^<^>v>^<^v.^>v^>>><v<>v^<v<<^^.vv>.^>v>v<.^v<><v<>>#\n"
            + "#>>v<^<>vvv.>^.v^^>^<<vv>>v.<^<>v^<v^>v^<>>.v.^<<v^.^v^v><<.^vv<^<^v>.vvv<v>.<>>^>.<<<<<<.<^^<v.<>.v<#\n"
            + "#<<<>^^>>>>.v^>v<>^>^^v>v^^v..v^.<<^>^>.>v^^vv.<>>^><<>v.<v<^^<<v^v<.>^>v<>^^<<>v<<^^><vv>vv<<^^...^>#\n"
            + "#>>v><><<<^<.>v^<<v^>v^^<>>v<<>>.<>v><^<^<.^<><^^^vv>><v<v>vv>^>v^>>vv>><<v^<><>^v.vv>^<^<>v<vvvv^v><#\n"
            + "#<^<^>v^^<>>^<.^^>^vv><v<^^^.v^>><v^>v^.>>v^<..>>.v>v<v^^<.v<<^v<<>v.^>><>v<<>.^<<<<<>>>^<><v>><^>>v>#\n"
            + "#>v>^^^v<v^.>^>^>v>>>v>v<v^v^<v>>><<>v..>>vvv<^><<>>^^.>v^<^>>.^v><v<^<.^>>vv<v^<^vv^<<<^><v.>v<^<.v>#\n"
            + "#>v^>>v^<^<<^<v>..<.v^<.<><>v>v^<.><<<<^><<.<><><><.<.^.>^v^><<<vv.<>vvv<v^v.<^<>>>^>v<^v^<><><v>.^><#\n"
            + "#<><>>vv..<v^vv><^v^.^^vv.^>^>>v<>vv>^><<<><^vvv>>><>><>>^^<v<><^^v^^>.^>^<.<<^..v<v>v^>^v^v<<.v^<v<>#\n"
            + "#>><^<>.v.><^.^^..^<v.v><v^<<v>^<><^>^..<vv>><^^>v^>.^><v>v^v>>>>vv<<>><v^<<^^v^vv<.>vv^><v^.^v>...v>#\n"
            + "#>vv<v<><v>v>v>><^>>.<^<<^<^>^.^v>>vv^vv<^v><>>v^<^v.v^<v<vv^v><.^^>><.v^^><<^<><^>>vv^.><vv^<>>v.><<#\n"
            + "#<^<^<>v^v^^^<<v<>vv<<<.v<v^v^^><.>^.v.>vvv>^<<<v>^^>.^<<.v<v>^v..v<<<<<^^v^v^<>^>>^.>^<^v<.v..^v^>^>#\n"
            + "#<<^^<>^^><v>v.<<><<<v<^<^>^<<^v<<>^^<.^^^^<>^>><^>.v^v>v.<.^<<vv^^>>>^v<v<<vv^><<.<>>><.v<^^>>^v>^^<#\n"
            + "#>v><>..v^>v>^<<^>vv>.^v^v>>v^^^>><><<>v^.vv^.<><v>^^v^>.<^><<^^>>v^.^^^>v.<>^^<^>>^<^<<<^>^<>>^<<v<>#\n"
            + "#>vv<.vv><>^>>>>.<>.>v^>>v><<<v<.v^v>v^<v^<>>vv><.<><v>^>^<^^v^>v^<><>^^^>^<<^^.>..>.>^>^>>^>v>v<v<><#\n"
            + "#<>>v^>^<.v^^.^<vv^<>^>^^<^.^.>^<<^>><^vv><.v><^vv^^^^^v><><vv.v.>.^^v^v.>^v.<><v>>v>vv^.vv^^<>^>.><<#\n"
            + "#>>>>>>>.v<>^<^.>><v^^<v.<^<^>vv.^v>^>.>^>>^>>v^>v>.v.><<^^<^<vv.vv<vvvvv^^v><^^^>>><^>>v<v>>>v<^^.^>#\n"
            + "#>^^^^<<v^^^...^<v>>^^v^^><<.<><>^v^v^>>>^^>.v<v.v<v^^v<vv>v>vv>^v^<>^vv^vv.^vv>v^v>v>>.<>>><^^<^^>^<#\n"
            + "#<^^^<v>^.<>^>^<^<v>vv<vv<^vv^^<><v^>v>^.><^^v<>><>vvvv^>^^<<^v><<v^v<><^<<>^.v<><vv<><>>^.>v>vv>><^<#\n"
            + "#>v^^^^><^<vv<^<v>v.<^.><.^>>^<>v.<<^<>.>vv<<.vv.v>v^.<^<^^>>^.><>v<v<v>v<v>>vv>^<><^<v>>><v>><<vvv^.#\n"
            + "#>><>><^^<^^>v<^.^>><<v<^v<>v>v^<<<<^^.v>v^.>v^<^><^v<v.>><v<^>^^>^^^^.<v>^>.v^<^^<<><>.<v^v>^^>v<^.<#\n"
            + "#><v>v^^<>.vvv>>.<..^^.<<^>>v^<>.<v<<vv><v<^v.^>>>v>.<^>.vvv<v<^<^^>^>v>v<>v<^v<vv^>v<<<<<>.^v^^.>><.#\n"
            + "#<>>.<>^^>>><>>^<><<^^v^><v><<<<v>>^<^vv<<.v^<<<^>v>v^<^.<<<<^<^v^>vv.<.^><v<.^^<^>^.>^v^v>^>^<^.v><.#\n"
            + "#<^<.<^^<>^v<^<^>^>^.>.<<^.v<>v^^>>..v^v<.>^>^.^^v>^v^>vv>>^<^>>>^>><<v><><<v^<>>v.vv><>.><^.v<^<v<<>#\n"
            + "#<v^.v<>^^>v>.vv><>^v<^<^.<^>>v^^.><^<v<>v<<vv<>.<>v<v<>.>^^.v.^v><^^.^v<vv<<^<>>v.>^^.>^^><>>>>.^^.<#\n"
            + "#<>^<>^><.^^^v<^v^>v.>^<>^^v<vv^><v^^>^.><>^v<v>v<>.^><^^>^v<<.>>^^<.v<><vv>vv>>>v^v><v^<^<>v<^>>^<.<#\n"
            + "#>v^<v^>^>>^<^<^>v<^vvvv<<<vv<.><^<.^^>.>^.v<v<><<.<v>v^vv<>^<v<^<>>.^<>^v<vv<<><<.<^><v<<^><^<><<>^>#\n"
            + "#<<><..v<^v<>^.v>v^.<v.><>>v>>vv>v^^^><vvv<.>^^<v<vv<<v<.^^^..^<<<^^v^>><>>^v>v><<>^^v..>^<^vv^<v><^<#\n"
            + "#><>vv<<<.vv>^^<v>^<^<vvv^v<<<<<^v<.>>^><^v>vv><v>>>^^^<v.><>><<v^^.vv.^v><<<<<<^>^v>>.>>v.>v^<<>^v^.#\n"
            + "#.^>>>vv>vv>^^v>>^.<v>^v.>^>..<v.><^<^>.^<v^<>^v^v^>vv<>v^^><<.^>>^..<^^>><.vv>>v^<v><<^v>>>>v><>.^v>#\n"
            + "#>^^^>v<><><v>>>^^vv^^^v.v><v<>>^v^v^^>^v.>vv..^>>^v^<.^<<v<>^v<^<..^v>^<^^^v.v^>^^<>v><^<.^<v^.v.v>>#\n"
            + "#>v>v>>>vvvv<>>v^><>v<v^.^>>v>.v^vv.^^v.^>>>^^^>v.v<^>>v<v<>^^v^^>^<v..v^<^>^v^vv<<v><vv.v^v.vv>v<^.>#\n"
            + "#><^<>v^>>^><>vv>v^><<v<.>^.v^><<<^v>^>.v<<>^vvvv>.vv^v.><<^<v^.<.vv.vv^^>v^>v>>v^v><<v<^<v<><^v<>^^<#\n"
            + "#<vv^^>v>v<^>.<>..>>vv^^v^<vv>^.>^<^><v><^>>>><.^>v^<v^>v^<>.<.>.<<^<v>><v>>vvv<>.v^<>vvvv>v<.>^vv^v>#\n"
            + "#>.<^<<^<>^v.^v^>v^<<>^^>^<.vvvv<^<.v^<>v>><v^^.v^v^<>><.^<v<<>.>v^>^><.>^>>.v^.^..^<v^^v^vv<^v.><<>>#\n"
            + "#<>>>v<vv.^v>v^.v<<v<><v..v>vv^^v^<vv>><<v<v^>v<.>^.^v^<.>><><>^<>>vv>^>.>v><><^vv>>v>^^<<..><.>.>^.>#\n"
            + "####################################################################################################.#";
}
