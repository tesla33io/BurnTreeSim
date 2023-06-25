// Tree - 🌳 | Burning - 🔥 | Burned - 🪵 | Stone - 🪨 | Void - 🌫

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {
    public static Random rand = new Random();

    public static void main(String[] args) {
        String[][] grid = generateGrid(10,10, 30f, 45f);
        for (int i = 0; i < 60; i++) {
            String[][] result = simulate(grid, 3f, 10, 1);
            System.out.println("\n\n\n\n\n\n\n\n\n");
            for (String[] row: result) {
                System.out.println(Arrays.toString(row));
            }
        }
    }

    public static String[][] generateGrid(int height, int width, float growthProbabilityOrigin, float growthProbabilityBound) {
        String[][] grid = new String[height][];

        for (int i = 0; i < height; i++) {
            String[] row = new String[width];

            for (int j = 0; j < width; j++) {
                float chance = rand.nextInt(1,101);
                if (chance >= growthProbabilityOrigin && chance <= growthProbabilityBound) {
                    row[j] = "\uD83C\uDF33";
                } else if (chance < growthProbabilityOrigin) {
                    row[j] = "\uD83E\uDEA8";
                } else {
                    row[j] = "\uD83C\uDF2B";
                }
            }

            grid[i] = row;
        }

        return grid;
    }

    public static String[][] simulate(String[][] grid, float growthProbability, float ignitionProbability, int step) {
        try {
            // Waiting between steps
            TimeUnit.SECONDS.sleep(step);

            for (int i = 0; i < grid.length; i++) {
                String[] row = grid[i];
                for (int j = 0; j < row.length; j++) {
                    int chance = rand.nextInt(1, 101);
                    // Check if cell contains the tree and with chance setting it on fire
                    if (Objects.equals(row[j], "\uD83C\uDF33") && chance <= ignitionProbability) {
                        row[j] = "\uD83D\uDD25";
                    }
                    // Check if cell contains tree and setting it on fire if there is fire around
                    else if ( Objects.equals(row[j], "\uD83C\uDF33") && fireAround(grid, i, j)) {
                        row[j] = "\uD83D\uDD25";
                    }
                    // If tree is on fire turning it into logs
                    else if (Objects.equals(row[j], "\uD83D\uDD25")) {
                        row[j] = "\uD83E\uDEB5";
                    }
                    // If cell contains logs change it to void
                    else if (Objects.equals(row[j], "\uD83E\uDEB5")) {
                        row[j] = "\uD83C\uDF2B";
                    }
                    // With chance grow a new tree if there is no fire around
                    else if (Objects.equals(row[j], "\uD83C\uDF2B") && chance <= growthProbability && !fireAround(grid, i, j)) {
                        row[j] = "\uD83C\uDF33";
                    }
                }
            }
        } catch (InterruptedException e) {
            System.out.println(e);
            return grid;
        }

        return grid;
    }

    public static boolean fireAround(String[][] grid, int row, int col) {
        int numRows = grid.length;
        int numCols = grid[0].length;

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                // Check if the current cell is within the grid boundaries
                if (i >= 0 && i < numRows && j >= 0 && j < numCols) {
                    // Check if the neighboring cell contains a tree on fire
                    if (Objects.equals(grid[i][j], "\uD83D\uDD25")) {
                        return true; // Tree on fire nearby found
                    }
                }
            }
        }

        return false; // No tree on fire nearby
    }
}