package DAY_07;

import Reader.FileReader;
import Reader.InputType;
import Utils.ResultLogger;
import Utils.Timer;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class DAY_07_1 {

    private static final String DAY = "07";
    private static final String PART = "_1";
    private static int counter = 0;

    public static void main(String[] args) throws Exception {
        Timer.startTimer();
        String[] input = FileReader.readFileAsString(DAY, InputType.NORMAL).split("[\\r\\n]+");
        String result = "abc";

        char[][] arr = new char[input.length][input[0].length()];

        for (int y = 0; y < input.length; y++) {
            char[] chars = input[y].toCharArray();

            for (int x = 0; x < chars.length; x++) {
                arr[y][x] = chars[x];
            }
        }

        Set<Point> points = new HashSet<>();

        for (int x = 0; x < arr[0].length; x++) {
            System.out.print(arr[0][x]);
            if (arr[0][x] == 'S') {
                points.add(new Point(x, 0));
            }
        }

        for (int y = 1; y < arr.length - 1; y++) {
            Set<Point> newPoints = new HashSet<>();

            for (Point point : points) {
                System.out.println(point);
                newPoints.addAll(movePointsDown(point, arr));
            }

            points = newPoints;

        }

        System.out.println("RESULT: " + counter);
        Timer.printEndTime();

        // Log the result to file
        long executionTime = Timer.getExecutionTimeNanos();
        //ResultLogger.logResult(PART, DAY, executionTime, result);
    }

    private static Set<Point> movePointsDown(Point point, char[][] arr) {
        Set<Point> pointsToReturn = new HashSet<>();
        char downPointChar = arr[point.getY() + 1][point.getX()];
        if (downPointChar == '^') {
            counter++;
            Point pointLeft = new Point(point.getX() - 1, point.getY() + 1);
            Point pointRight = new Point(point.getX() + 1, point.getY() + 1);
            pointsToReturn.add(pointLeft);
            pointsToReturn.add(pointRight);
        } else {
            Point pointDown = new Point(point.getX(), point.getY()  + 1);
            pointsToReturn.add(pointDown);
        }
        return pointsToReturn;
    }

    public static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public String toString() {
            return "[" + x + "," + y + "]";
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

}
