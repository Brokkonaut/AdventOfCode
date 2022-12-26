package day19;

import java.util.ArrayList;

public class Day19Task1 {
    record Blueprint(int id, int oreRobotOrePrice, int clayRobotOrePrice, int obsidianRobotOrePrice, int obsidianRobotClayPrice, int geodeRobotOrePrice, int geodeRobotObsidianPrice) {
    }

    record Inventory(Inventory previous, Blueprint blueprint, int ticks, int ore, int clay, int obsidian, int geodes, int oreRobots, int clayRobots, int obsidianRobots, int geodeRobots) {
        public Inventory tick() {
            return new Inventory(this, blueprint, ticks - 1, ore + oreRobots, clay + clayRobots, obsidian + obsidianRobots, geodes + geodeRobots, oreRobots, clayRobots, obsidianRobots, geodeRobots);
        }

        public Inventory buildOreRobot() {
            return new Inventory(this, blueprint, ticks, ore - blueprint.oreRobotOrePrice, clay, obsidian, geodes, oreRobots + 1, clayRobots, obsidianRobots, geodeRobots);
        }

        public Inventory buildClayRobot() {
            return new Inventory(this, blueprint, ticks, ore - blueprint.clayRobotOrePrice, clay, obsidian, geodes, oreRobots, clayRobots + 1, obsidianRobots, geodeRobots);
        }

        public Inventory buildObsidianRobot() {
            return new Inventory(this, blueprint, ticks, ore - blueprint.obsidianRobotOrePrice, clay - blueprint.obsidianRobotClayPrice, obsidian, geodes, oreRobots, clayRobots, obsidianRobots + 1, geodeRobots);
        }

        public Inventory buildGeodeRobot() {
            return new Inventory(this, blueprint, ticks, ore - blueprint.geodeRobotOrePrice, clay, obsidian - blueprint.geodeRobotObsidianPrice, geodes, oreRobots, clayRobots, obsidianRobots, geodeRobots + 1);
        }
    }

    public static int max = 0;

    public static void main(String[] args) {
        ArrayList<Blueprint> blueprints = new ArrayList<>();
        int id = 0;
        for (String line : INPUT.split("\n")) {
            id++;
            String[] parts1 = line.split(" Each ore robot costs ", 2);
            String[] parts2 = parts1[1].split(" ore\\. Each clay robot costs ", 2);
            String[] parts3 = parts2[1].split(" ore\\. Each obsidian robot costs ", 2);
            String[] parts4 = parts3[1].split(" ore and ", 2);
            String[] parts5 = parts4[1].split(" clay\\. Each geode robot costs ", 2);
            String[] parts6 = parts5[1].split(" ore and ", 2);
            String[] parts7 = parts6[1].split(" obsidian.", 2);

            int oreRobotOrePrice = Integer.parseInt(parts2[0]);
            int clayRobotOrePrice = Integer.parseInt(parts3[0]);
            int obsidianRobotOrePrice = Integer.parseInt(parts4[0]);
            int obsidianRobotClayPrice = Integer.parseInt(parts5[0]);
            int geodeRobotOrePrice = Integer.parseInt(parts6[0]);
            int geodeRobotObsidianPrice = Integer.parseInt(parts7[0]);
            blueprints.add(new Blueprint(id, oreRobotOrePrice, clayRobotOrePrice, obsidianRobotOrePrice, obsidianRobotClayPrice, geodeRobotOrePrice, geodeRobotObsidianPrice));
        }

        int score = 0;
        for (Blueprint blueprint : blueprints) {
            max = 0;
            Inventory current = new Inventory(null, blueprint, 24, 0, 0, 0, 0, 1, 0, 0, 0);
            simulate(current);
            score += max * current.blueprint.id;
        }
        System.out.println("Score: " + score);
    }

    private static void simulate(Inventory inventory) {
        if (inventory.ticks == 0) {
            if (inventory.geodes > max) {
                max = inventory.geodes;
                System.out.println(inventory.blueprint.id + " --> " + max);
                System.out.println(inventory);
            }
            return;
        }
        if (inventory.ticks < 0) {
            throw new IllegalStateException("t: " + inventory.ticks);
        }
        boolean any = false;
        // next: ore robot
        {
            int ticksForOreRobot = (inventory.blueprint.oreRobotOrePrice - inventory.ore + inventory.oreRobots - 1) / inventory.oreRobots;
            if (inventory.ticks > ticksForOreRobot) {
                Inventory i2 = inventory;
                for (int i = 0; i < ticksForOreRobot; i++) {
                    i2 = i2.tick();
                }
                simulate(i2.tick().buildOreRobot());
                any = true;
            }
        }

        // next: clay robot
        {
            int ticksForClayRobot = (inventory.blueprint.clayRobotOrePrice - inventory.ore + inventory.oreRobots - 1) / inventory.oreRobots;
            if (inventory.ticks > ticksForClayRobot) {
                Inventory i2 = inventory;
                for (int i = 0; i < ticksForClayRobot; i++) {
                    i2 = i2.tick();
                }

                simulate(i2.tick().buildClayRobot());
                any = true;
            }
        }

        // next: obsidian robot
        if (inventory.clayRobots > 0) {
            int ticksForObsiRobot1 = (inventory.blueprint.obsidianRobotOrePrice - inventory.ore + inventory.oreRobots - 1) / inventory.oreRobots;
            int ticksForObsiRobot2 = (inventory.blueprint.obsidianRobotClayPrice - inventory.clay + inventory.clayRobots - 1) / inventory.clayRobots;
            int ticksForClayRobot = Math.max(ticksForObsiRobot1, ticksForObsiRobot2);
            if (inventory.ticks > ticksForClayRobot) {
                Inventory i2 = inventory;
                for (int i = 0; i < ticksForClayRobot; i++) {
                    i2 = i2.tick();
                }

                simulate(i2.tick().buildObsidianRobot());
                any = true;
            }
        }

        // next: geode robot
        if (inventory.obsidianRobots > 0) {
            int ticksForObsiRobot1 = (inventory.blueprint.geodeRobotOrePrice - inventory.ore + inventory.oreRobots - 1) / inventory.oreRobots;
            int ticksForObsiRobot2 = (inventory.blueprint.geodeRobotObsidianPrice - inventory.obsidian + inventory.obsidianRobots - 1) / inventory.obsidianRobots;
            int ticksForClayRobot = Math.max(ticksForObsiRobot1, ticksForObsiRobot2);
            if (inventory.ticks > ticksForClayRobot) {
                Inventory i2 = inventory;
                for (int i = 0; i < ticksForClayRobot; i++) {
                    i2 = i2.tick();
                }

                simulate(i2.tick().buildGeodeRobot());
                any = true;
            }
        }

        // no more building
        if (!any) {
            Inventory i2 = inventory;
            for (int i = 0; i < inventory.ticks; i++) {
                i2 = i2.tick();
            }
            simulate(i2);
        }
    }

    public static final String INPUT_TEST = "Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.\n"
            + "Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.";

    public static final String INPUT = "Blueprint 1: Each ore robot costs 2 ore. Each clay robot costs 4 ore. Each obsidian robot costs 3 ore and 20 clay. Each geode robot costs 2 ore and 17 obsidian.\n"
            + "Blueprint 2: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 11 clay. Each geode robot costs 4 ore and 12 obsidian.\n"
            + "Blueprint 3: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 3 ore and 19 clay. Each geode robot costs 4 ore and 15 obsidian.\n"
            + "Blueprint 4: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 3 ore and 20 clay. Each geode robot costs 2 ore and 10 obsidian.\n"
            + "Blueprint 5: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 18 clay. Each geode robot costs 2 ore and 19 obsidian.\n"
            + "Blueprint 6: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 11 clay. Each geode robot costs 2 ore and 16 obsidian.\n"
            + "Blueprint 7: Each ore robot costs 4 ore. Each clay robot costs 3 ore. Each obsidian robot costs 4 ore and 8 clay. Each geode robot costs 3 ore and 7 obsidian.\n"
            + "Blueprint 8: Each ore robot costs 3 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 17 clay. Each geode robot costs 2 ore and 13 obsidian.\n"
            + "Blueprint 9: Each ore robot costs 3 ore. Each clay robot costs 4 ore. Each obsidian robot costs 3 ore and 12 clay. Each geode robot costs 3 ore and 17 obsidian.\n"
            + "Blueprint 10: Each ore robot costs 3 ore. Each clay robot costs 3 ore. Each obsidian robot costs 2 ore and 15 clay. Each geode robot costs 3 ore and 9 obsidian.\n"
            + "Blueprint 11: Each ore robot costs 3 ore. Each clay robot costs 3 ore. Each obsidian robot costs 2 ore and 16 clay. Each geode robot costs 2 ore and 18 obsidian.\n"
            + "Blueprint 12: Each ore robot costs 3 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 15 clay. Each geode robot costs 2 ore and 8 obsidian.\n"
            + "Blueprint 13: Each ore robot costs 4 ore. Each clay robot costs 3 ore. Each obsidian robot costs 4 ore and 11 clay. Each geode robot costs 3 ore and 15 obsidian.\n"
            + "Blueprint 14: Each ore robot costs 3 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 9 clay. Each geode robot costs 3 ore and 7 obsidian.\n"
            + "Blueprint 15: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 2 ore and 16 clay. Each geode robot costs 2 ore and 8 obsidian.\n"
            + "Blueprint 16: Each ore robot costs 3 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 16 clay. Each geode robot costs 3 ore and 15 obsidian.\n"
            + "Blueprint 17: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 20 clay. Each geode robot costs 2 ore and 8 obsidian.\n"
            + "Blueprint 18: Each ore robot costs 2 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 13 clay. Each geode robot costs 3 ore and 11 obsidian.\n"
            + "Blueprint 19: Each ore robot costs 3 ore. Each clay robot costs 3 ore. Each obsidian robot costs 2 ore and 12 clay. Each geode robot costs 2 ore and 10 obsidian.\n"
            + "Blueprint 20: Each ore robot costs 3 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 19 clay. Each geode robot costs 3 ore and 19 obsidian.\n"
            + "Blueprint 21: Each ore robot costs 4 ore. Each clay robot costs 3 ore. Each obsidian robot costs 2 ore and 19 clay. Each geode robot costs 3 ore and 10 obsidian.\n"
            + "Blueprint 22: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 16 clay. Each geode robot costs 2 ore and 11 obsidian.\n"
            + "Blueprint 23: Each ore robot costs 4 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 10 clay. Each geode robot costs 3 ore and 10 obsidian.\n"
            + "Blueprint 24: Each ore robot costs 3 ore. Each clay robot costs 4 ore. Each obsidian robot costs 3 ore and 15 clay. Each geode robot costs 4 ore and 16 obsidian.\n"
            + "Blueprint 25: Each ore robot costs 4 ore. Each clay robot costs 3 ore. Each obsidian robot costs 2 ore and 5 clay. Each geode robot costs 2 ore and 10 obsidian.\n"
            + "Blueprint 26: Each ore robot costs 3 ore. Each clay robot costs 4 ore. Each obsidian robot costs 3 ore and 18 clay. Each geode robot costs 4 ore and 16 obsidian.\n"
            + "Blueprint 27: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 11 clay. Each geode robot costs 3 ore and 14 obsidian.\n"
            + "Blueprint 28: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 2 ore and 7 clay. Each geode robot costs 4 ore and 13 obsidian.\n"
            + "Blueprint 29: Each ore robot costs 3 ore. Each clay robot costs 4 ore. Each obsidian robot costs 3 ore and 20 clay. Each geode robot costs 3 ore and 14 obsidian.\n"
            + "Blueprint 30: Each ore robot costs 4 ore. Each clay robot costs 3 ore. Each obsidian robot costs 4 ore and 8 clay. Each geode robot costs 2 ore and 8 obsidian.";
}
