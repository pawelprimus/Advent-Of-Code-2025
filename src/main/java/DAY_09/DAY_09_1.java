package DAY_09;

import Reader.FileReader;
import Reader.InputType;
import Utils.ResultLogger;
import Utils.Timer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class DAY_09_1 {

    private static final String DAY = "09";
    private static final String PART = "_1";

    public static void main(String[] args) throws Exception {
        Timer.startTimer();
        String[] input = FileReader.readFileAsString(DAY, InputType.NORMAL).split("[\\r\\n]+");

        List<Point> pointList = new ArrayList<>();
        for (String str : input) {

            pointList.add(new Point(str));
        }

        BigInteger max = BigInteger.ZERO;
        for (int i = 0; i < pointList.size(); i++) {
            Point point_I = pointList.get(i);
            for (int j = i; j < pointList.size(); j++) {
                Point point_J = pointList.get(j);
                BigInteger rectangleArea = calculateRectangle(point_I, point_J);
                if (rectangleArea.compareTo(max) > 0) {
                    max = rectangleArea;
                }

            }

        }

        System.out.println("RESULT: " + max);
        Timer.printEndTime();

        // Log the result to file
        long executionTime = Timer.getExecutionTimeNanos();
        ResultLogger.logResult(PART, DAY, executionTime, String.valueOf(max));
    }

    public static BigInteger calculateRectangle(Point point1, Point point2) {
        BigInteger x = point1.getX().subtract(point2.getX()).abs().add(BigInteger.ONE);
        BigInteger y = point1.getY().subtract(point2.getY()).abs().add(BigInteger.ONE);
        return x.multiply(y);
    }


    public static class Point {
        BigInteger x;
        BigInteger y;

        public Point(String line) {
            this.x = new BigInteger(line.split(",")[0]);
            this.y = new BigInteger(line.split(",")[1]);
        }

        public BigInteger getX() {
            return x;
        }

        public BigInteger getY() {
            return y;
        }
    }
}
