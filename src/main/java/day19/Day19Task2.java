package day19;

import java.util.ArrayList;

public class Day19Task2 {
    record Blueprint(int id, int oreRobotOrePrice, int clayRobotOrePrice, int obsidianRobotOrePrice, int obsidianRobotClayPrice, int geodeRobotOrePrice, int geodeRobotObsidianPrice) {
        public int maxOrePrice() {
            return Math.max(Math.max(Math.max(oreRobotOrePrice, clayRobotOrePrice), obsidianRobotOrePrice), geodeRobotOrePrice);
        }
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

    public static void main(String[] args) {
        ArrayList<Blueprint> blueprints = new ArrayList<>();
        int id = 0;
        for (String line : INPUT_TEST.split("\n")) {

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

        int score = 1;
        for (Blueprint blueprint : blueprints) {
            Inventory current = new Inventory(null, blueprint, 32, 0, 0, 0, 0, 1, 0, 0, 0);
            int geodes = simulate(current);
            score *= geodes;
        }
        System.out.println("Score: " + score);
    }

    private static int simulate(Inventory inventory) {
        if (inventory.ticks == 0) {
            return inventory.geodes;
        }
        if (inventory.ticks < 0) {
            throw new IllegalStateException("t: " + inventory.ticks);
        }
        boolean any = false;

        int max = 0;

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

                max = Math.max(max, simulate(i2.tick().buildGeodeRobot()));
                any = true;
            }
        }

        // next: obsidian robot
        if (inventory.clayRobots > 0 && inventory.obsidian < inventory.blueprint.geodeRobotObsidianPrice) {
            int ticksForObsiRobot1 = (inventory.blueprint.obsidianRobotOrePrice - inventory.ore + inventory.oreRobots - 1) / inventory.oreRobots;
            int ticksForObsiRobot2 = (inventory.blueprint.obsidianRobotClayPrice - inventory.clay + inventory.clayRobots - 1) / inventory.clayRobots;
            int ticksForClayRobot = Math.max(ticksForObsiRobot1, ticksForObsiRobot2);
            if (inventory.ticks > ticksForClayRobot) {
                Inventory i2 = inventory;
                for (int i = 0; i < ticksForClayRobot; i++) {
                    i2 = i2.tick();
                }

                max = Math.max(max, simulate(i2.tick().buildObsidianRobot()));
                any = true;
            }
        }

        // next: clay robot
        {
            if (inventory.clayRobots < inventory.blueprint.obsidianRobotClayPrice) {
                int ticksForClayRobot = (inventory.blueprint.clayRobotOrePrice - inventory.ore + inventory.oreRobots - 1) / inventory.oreRobots;
                if (inventory.ticks > ticksForClayRobot) {
                    Inventory i2 = inventory;
                    for (int i = 0; i < ticksForClayRobot; i++) {
                        i2 = i2.tick();
                    }

                    max = Math.max(max, simulate(i2.tick().buildClayRobot()));
                    any = true;
                }
            }
        }
        // next: ore robot
        {
            if (inventory.oreRobots < inventory.blueprint.maxOrePrice()) {
                int ticksForOreRobot = (inventory.blueprint.oreRobotOrePrice - inventory.ore + inventory.oreRobots - 1) / inventory.oreRobots;
                if (inventory.ticks > ticksForOreRobot) {
                    Inventory i2 = inventory;
                    for (int i = 0; i < ticksForOreRobot; i++) {
                        i2 = i2.tick();
                    }
                    max = Math.max(max, simulate(i2.tick().buildOreRobot()));
                    any = true;
                }
            }
        }

        // no more building
        if (!any) {
            Inventory i2 = inventory;
            for (int i = 0; i < inventory.ticks; i++) {
                i2 = i2.tick();
            }
            max = Math.max(max, simulate(i2));
        }
        return max;
    }

    public static final String INPUT_TEST = "Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.\n"
            + "Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.";

    public static final String INPUT = "Blueprint 1: Each ore robot costs 2 ore. Each clay robot costs 4 ore. Each obsidian robot costs 3 ore and 20 clay. Each geode robot costs 2 ore and 17 obsidian.\n"
            + "Blueprint 2: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 11 clay. Each geode robot costs 4 ore and 12 obsidian.\n"
            + "Blueprint 3: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 3 ore and 19 clay. Each geode robot costs 4 ore and 15 obsidian.";
}
