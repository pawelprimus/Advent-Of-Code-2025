package DAY_07;

import Reader.FileReader;
import Reader.InputType;
import Utils.ResultLogger;
import Utils.Timer;

import java.math.BigInteger;
import java.util.*;

public class DAY_07_2 {

    private static final String DAY = "07";
    private static final String PART = "_2";
    private static int MAX_X;

    public static void main(String[] args) throws Exception {
        Timer.startTimer();
        String[] input = FileReader.readFileAsString(DAY, InputType.NORMAL).split("[\\r\\n]+");
        BigInteger result = BigInteger.ZERO;

        char[][] arr = new char[input.length][input[0].length()];

        for (int y = 0; y < input.length; y++) {
            char[] chars = input[y].toCharArray();

            for (int x = 0; x < chars.length; x++) {
                arr[y][x] = chars[x];
            }
        }

        List<Point> points = new ArrayList<>();

        // CREATE LIST WITH STARTING POINT
        for (int x = 0; x < arr[0].length; x++) {
            if (arr[0][x] == 'S') {
                points.add(new Point(x, 0, 1));
                break;
            }
        }
        MAX_X = arr[0].length;

        for (int y = 1; y < arr.length - 1; y++) {
            List<Point> newPoints = new ArrayList<>();

            for (Point point : points) {
                System.out.println(point);
                newPoints.addAll(movePointsDown(point, arr));
            }
            points = mergePointsWithSameX(newPoints);
        }

        for (Point point : points) {
            result = result.add(point.getPowerBig());
        }

        System.out.println("RESULT: " + result); // 9897897326778
        Timer.printEndTime();

        // Log the result to file/
        long executionTime = Timer.getExecutionTimeNanos();
        ResultLogger.logResult(PART, DAY, executionTime, "" + result);
    }

    private static List<Point> mergePointsWithSameX(List<Point> points) {
        List<Point> pointsToReturn = new ArrayList<>();

        int y = points.getFirst().getY();
        for (int i = 0; i < MAX_X; i++) {
            long power = 0;
            for (Point point : points) {
                if (point.getX() == i) {
                    power += point.getPower();
                }
            }
            if (power > 0) {
                pointsToReturn.add(new Point(i, y, power));
            }
        }
        return pointsToReturn;
    }

    private static List<Point> movePointsDown(Point point, char[][] arr) {
        List<Point> pointsToReturn = new ArrayList<>();
        char downPointChar = arr[point.getY() + 1][point.getX()];
        if (downPointChar == '^') {

            // LEFT POINT
            pointsToReturn.add(new Point(point.getX() - 1, point.getY() + 1, point.getPower()));

            // RIGHT POINT
            pointsToReturn.add(new Point(point.getX() + 1, point.getY() + 1, point.getPower()));
        } else {
            // NOTHING GOINGS ON, JUST MOVE POINT DOWN
            pointsToReturn.add(new Point(point.getX(), point.getY() + 1, point.getPower()));
        }
        return pointsToReturn;
    }

    public static class Point {
        int x;
        int y;
        long power;

        public Point(int x, int y, long power) {
            this.x = x;
            this.y = y;
            this.power = power;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public long getPower() {
            return power;
        }

        public BigInteger getPowerBig() {
            return BigInteger.valueOf(power);
        }


        @Override
        public String toString() {
            return "[" + x + "," + y + "] - " + power;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y && power == point.power;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, power);
        }
    }

}
