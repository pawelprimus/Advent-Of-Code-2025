package DAY_12;

import Reader.FileReader;
import Reader.InputType;
import Utils.ResultLogger;
import Utils.Timer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class DAY_12_1 {

    private static final String DAY = "12";
    private static final String PART = "_1";

    private static final long MAX_ITERATIONS = 1_000_0;
    private static long iterationCount = 0;


    public static void main(String[] args) throws Exception {
        Timer.startTimer();
        String[] input = FileReader.readFileAsString(DAY, InputType.NORMAL).split("[\\r\\n]+");
        int result = 0;

        List<Shape> shapes = new ArrayList<>();
        List<Region> regions = new ArrayList<>();


        for (int i = 0; i < input.length; i++) {
            String line = input[i];
            if (checkUsingPatternClass(line)) {
                System.out.println(line);
                i++;
                shapes.add(new Shape(line, input[i++], input[i++], input[i]));
            }
            if (line.contains("x")) {
                regions.add(new Region(line));
            }

        }


        // === PRINT SHAPES ===
        System.out.println("=== PRINT SHAPES ===");
        for (Shape shape : shapes) {
            System.out.println(shape);
        }

        // === PRINT REGIONS ===
        System.out.println("=== PRINT REGIONS ===");
        for (Region region : regions) {
            System.out.println(region);
        }

        // === SOLVE ===
        System.out.println("=== SOLVE ===");
        for (Region region : regions) {
            System.out.println("SOLVE REGION " + region);
            List<Shape> presentsToPlace = buildPresentsList(shapes, region.presentCounts);

            if (canFitAllPresents(region, presentsToPlace)) {
                System.out.println("RESULT++");
                result++;
            }

            // Reset grid for next region (or create fresh Region)
            region.grid = new boolean[region.height][region.width];
        }

        System.out.println("RESULT: " + result);
        Timer.printEndTime();

        // Log the result to file
        long executionTime = Timer.getExecutionTimeNanos();
        ResultLogger.logResult(PART, DAY, executionTime, String.valueOf(result));
    }

    private static List<Shape> buildPresentsList(List<Shape> allShapes, int[] presentCounts) {
        List<Shape> presents = new ArrayList<>();

        for (int shapeIndex = 0; shapeIndex < presentCounts.length; shapeIndex++) {
            int count = presentCounts[shapeIndex];
            for (int i = 0; i < count; i++) {
                presents.add(allShapes.get(shapeIndex));
            }
        }

        return presents;
    }

    private static boolean canFitAllPresents(Region region, List<Shape> presents) {
        iterationCount = 0;
        return backtrack(region, presents, 0);
    }

    private static boolean backtrack(Region region, List<Shape> presents, int presentIndex) {
        // Base case: all presents placed successfully
        iterationCount++;
        if (iterationCount > MAX_ITERATIONS) {
            return false; // Give up after too many attempts
        }

        if (presentIndex == presents.size()) {
            return true;
        }

        Shape currentShape = presents.get(presentIndex);

        // Try each transformation of this shape
        for (boolean[][] transformation : currentShape.transformations) {
            // Try every position in the grid
            for (int row = 0; row < region.height; row++) {
                for (int col = 0; col < region.width; col++) {

                    if (canPlace(region.grid, transformation, row, col)) {
                        // Place the shape
                        place(region.grid, transformation, row, col, true);

                        // Recurse to place next present
                        if (backtrack(region, presents, presentIndex + 1)) {
                            return true;
                        }

                        // Backtrack: remove the shape
                        place(region.grid, transformation, row, col, false);
                    }
                }
            }
        }

        return false; // Couldn't place this present anywhere
    }

    private static boolean canPlace(boolean[][] grid, boolean[][] shape, int startRow, int startCol) {
        int shapeRows = shape.length;
        int shapeCols = shape[0].length;

        // Check bounds
        if (startRow + shapeRows > grid.length || startCol + shapeCols > grid[0].length) {
            return false;
        }

        for (int i = 0; i < shapeRows; i++) {
            for (int j = 0; j < shapeCols; j++) {
                if (shape[i][j] && grid[startRow + i][startCol + j]) {
                    return false;
                }
            }
        }

        return true;
    }

    private static void place(boolean[][] grid, boolean[][] shape, int startRow, int startCol, boolean occupy) {
        int shapeRows = shape.length;
        int shapeCols = shape[0].length;

        for (int i = 0; i < shapeRows; i++) {
            for (int j = 0; j < shapeCols; j++) {
                if (shape[i][j]) {
                    grid[startRow + i][startCol + j] = occupy;
                }
            }
        }
    }


    static boolean checkUsingPatternClass(String input) {
        return Pattern.compile(".*\\d.*:")
                .matcher(input)
                .matches();
    }

    static class Shape {
        int index;
        List<String> pattern = new ArrayList<>();
        List<boolean[][]> transformations;

        public Shape(String lineOne, String lineTwo, String lineThree, String lineFour) {
            this.index = Integer.parseInt(lineOne.replaceAll(":", ""));
            this.pattern.add(lineTwo);
            this.pattern.add(lineThree);
            this.pattern.add(lineFour);
            this.transformations = generateAllTransformations();
        }

        private boolean[][] patternToGrid() {
            boolean[][] grid = new boolean[3][3];
            for (int i = 0; i < pattern.size(); i++) {
                char[] patternLine = pattern.get(i).toCharArray();
                for (int j = 0; j < patternLine.length; j++) {
                    grid[i][j] = patternLine[j] == '#';
                }

            }
            return grid;
        }

        private List<boolean[][]> generateAllTransformations() {
            List<boolean[][]> allTransforms = new ArrayList<>();
            boolean[][] base = patternToGrid();

            allTransforms.add(base);

            boolean[][] rotated = base;
            for (int i = 0; i < 3; i++) {
                rotated = rotate90(rotated);
                if (isAlreadyNotContains(allTransforms, rotated)) {
                    allTransforms.add(rotated);
                }
            }

            boolean[][] flipped = flipHorizontal(base);
            allTransforms.add(flipped);
            for (int i = 0; i < 3; i++) {
                flipped = rotate90(flipped);
                if (isAlreadyNotContains(allTransforms, flipped)) {
                    allTransforms.add(flipped);
                }
            }
            return allTransforms;
        }

        public boolean isAlreadyNotContains(List<boolean[][]> allTransforms, boolean[][] toCheck) {
            List<String> parsed = new ArrayList<>();

            for (boolean[][] shape : allTransforms) {
                parsed.add(Arrays.deepToString(shape));
            }

            return !parsed.contains(Arrays.deepToString(toCheck));
        }


        private boolean[][] rotate90(boolean[][] grid) {
            int rows = grid.length;
            int cols = grid[0].length;
            boolean[][] rotated = new boolean[cols][rows];

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    rotated[j][rows - 1 - i] = grid[i][j];
                }
            }
            return rotated;
        }

        private boolean[][] flipHorizontal(boolean[][] grid) {
            int rows = grid.length;
            int cols = grid[0].length;
            boolean[][] flipped = new boolean[rows][cols];

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    flipped[i][cols - 1 - j] = grid[i][j];
                }
            }
            return flipped;
        }


        @Override
        public String toString() {
            return "Shape{" +
                    "index=" + index +
                    ", pattern=" + pattern +
                    '}';
        }
    }

    static class Region {
        int width;
        int height;
        int[] presentCounts;
        boolean[][] grid;

        public Region(String line) {
            String[] divided = line.split(":");
            String[] size = divided[0].split("x");
            String[] presentCountString = divided[1].trim().split(" ");
            this.width = Integer.parseInt(size[0]);
            this.height = Integer.parseInt(size[1]);
            this.presentCounts = new int[presentCountString.length];
            for (int i = 0; i < presentCountString.length; i++) {
                presentCounts[i] = Integer.parseInt(presentCountString[i]);
            }

            this.grid = new boolean[height][width];
        }

        @Override
        public String toString() {
            return "Region{" +
                    "width=" + width +
                    ", height=" + height +
                    ", presentCounts=" + Arrays.toString(presentCounts) +
                    ", grid=" + Arrays.toString(grid) +
                    '}';
        }
    }

}
