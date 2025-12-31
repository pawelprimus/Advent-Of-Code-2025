package DAY_09;

import Reader.FileReader;
import Reader.InputType;
import Utils.ResultLogger;
import Utils.Timer;

import java.math.BigInteger;
import java.util.*;

public class DAY_09_2 {

    private static final String DAY = "09";
    private static final String PART = "_2";


    public static void main(String[] args) throws Exception {
        Timer.startTimer();
        String[] input = FileReader.readFileAsString(DAY, InputType.NORMAL).split("[\\r\\n]+");

        List<Point> pointList = new ArrayList<>();
        for (String str : input) {
            pointList.add(new Point(str));
        }


        BigInteger max = BigInteger.ZERO;

        List<BigInteger> ares = new ArrayList<>();

        List<Rectangle> rectangles = new ArrayList<>();


        for (int i = 0; i < pointList.size(); i++) {
            System.out.println(i + "/" + pointList.size());
            Point point_I = pointList.get(i);
            for (int j = i + 1; j < pointList.size(); j++) {
                Point point_J = pointList.get(j);

                if (isRectangleValid(point_I, point_J, pointList)) {
                    BigInteger rectangleArea = calculateRectangle(point_I, point_J);
                    rectangles.add(new Rectangle(point_I, new Point(point_I.getX(), point_J.getY()),
                            point_J, new Point(point_J.getX(), point_I.getY()),
                            rectangleArea));
                    if (rectangleArea.compareTo(max) > 0) {
                        max = rectangleArea;
                    }
                }
            }
        }

        rectangles.sort(Comparator.comparing((Rectangle rect) -> rect.rectangleArea).reversed());
        Collections.sort(ares);
        System.out.println(ares);

        // ----------------- PRINT RECTANGLES
//        for (int i = 0; i < rectangles.size(); i++) {
//            System.out.println(i + " / " + rectangles.size());
//            Rectangle rectangle = rectangles.get(i);
//            System.out.println(rectangle);
//
//        }

        System.out.println("RESULT: " + max); // 1573359081
        Timer.printEndTime();

        // Log the result to file
        long executionTime = Timer.getExecutionTimeNanos();
        ResultLogger.logResult(PART, DAY, executionTime, String.valueOf(max));
    }

    public static boolean isRectangleValid(Point p1, Point p2, List<Point> polygon) {
        int minX = Math.min(p1.getX(), p2.getX());
        int maxX = Math.max(p1.getX(), p2.getX());
        int minY = Math.min(p1.getY(), p2.getY());
        int maxY = Math.max(p1.getY(), p2.getY());

        if (!isPointInOrOnPolygon(new Point(minX, minY), polygon)) return false;
        if (!isPointInOrOnPolygon(new Point(maxX, minY), polygon)) return false;
        if (!isPointInOrOnPolygon(new Point(minX, maxY), polygon)) return false;
        if (!isPointInOrOnPolygon(new Point(maxX, maxY), polygon)) return false;


        for (int i = 0; i < polygon.size(); i++) {
            Point edgeStart = polygon.get(i);
            Point edgeEnd = polygon.get((i + 1) % polygon.size());

            // HORIZONTAL
            if (edgeStart.getY() == edgeEnd.getY()) {
                int y = edgeStart.getY();
                int startX = Math.min(edgeStart.getX(), edgeEnd.getX());
                int endX = Math.max(edgeStart.getX(), edgeEnd.getX());

                for (int x = startX; x <= endX; x++) {
                    if (x > minX && x < maxX && y > minY && y < maxY) {
                        return false;
                    }
                }
            }

            // VERTICAL
            if (edgeStart.getX() == edgeEnd.getX()) {
                int x = edgeStart.getX();
                int startY = Math.min(edgeStart.getY(), edgeEnd.getY());
                int endY = Math.max(edgeStart.getY(), edgeEnd.getY());

                for (int y = startY; y <= endY; y++) {
                    if (x > minX && x < maxX && y > minY && y < maxY) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static boolean isPointInOrOnPolygon(Point point, List<Point> polygon) {
        int x = point.getX();
        int y = point.getY();

        // CHECK IF IS ON VERTEX
        for (Point p : polygon) {
            if (p.getX() == x && p.getY() == y) {
                return true;
            }
        }

        // CHECK IF IS ON EDGE
        for (int i = 0; i < polygon.size(); i++) {
            Point p1 = polygon.get(i);
            Point p2 = polygon.get((i + 1) % polygon.size());

            // HORIZONTAL edge
            if (p1.getY() == p2.getY() && p1.getY() == y) {
                int minX = Math.min(p1.getX(), p2.getX());
                int maxX = Math.max(p1.getX(), p2.getX());
                if (x >= minX && x <= maxX) {
                    return true;
                }
            }

            // VERTICAL edge
            if (p1.getX() == p2.getX() && p1.getX() == x) {
                int minY = Math.min(p1.getY(), p2.getY());
                int maxY = Math.max(p1.getY(), p2.getY());
                if (y >= minY && y <= maxY) {
                    return true;
                }
            }
        }

        // CHECK INTERSECTION
        return isPointInPolygon(point, polygon);
    }

    public static boolean isPointInPolygon(Point point, List<Point> polygon) {
        int x = point.getX();
        int y = point.getY();
        int intersections = 0;

        for (int i = 0, j = polygon.size() - 1; i < polygon.size(); j = i++) {
            int xi = polygon.get(i).getX();
            int yi = polygon.get(i).getY();
            int xj = polygon.get(j).getX();
            int yj = polygon.get(j).getY();


            // https://wrfranklin.org/Research/Short_Notes/pnpoly.html
            if ((yi > y) != (yj > y) &&
                    (x < (xj - xi) * (y - yi) / (yj - yi) + xi)) {
                intersections++;
            }
        }

        return intersections % 2 != 0;
    }


    public static BigInteger calculateRectangle(Point point1, Point point2) {
        BigInteger x = point1.getBigX().subtract(point2.getBigX()).abs().add(BigInteger.ONE);
        BigInteger y = point1.getBigY().subtract(point2.getBigY()).abs().add(BigInteger.ONE);
        return x.multiply(y);
    }

    public static class Rectangle {
        Point A;
        Point B;
        Point C;
        Point D;
        BigInteger rectangleArea;

        public Rectangle(Point a, Point b, Point c, Point d, BigInteger rectangleArea) {
            A = a;
            B = b;
            C = c;
            D = d;
            this.rectangleArea = rectangleArea;
        }

        @Override
        public String toString() {
            return "Rectangle{" +
                    "A=" + A +
                    ", B=" + B +
                    ", C=" + C +
                    ", D=" + D +
                    ", rectangleArea=" + rectangleArea +
                    '}';
        }
    }

    public static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point(String line) {
            this.x = Integer.valueOf(line.split(",")[0]);
            this.y = Integer.valueOf(line.split(",")[1]);
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }


        public BigInteger getBigX() {
            return new BigInteger(String.valueOf(x));
        }

        public BigInteger getBigY() {
            return new BigInteger(String.valueOf(y));
        }

        @Override
        public String toString() {
            return "[" + x + "," + y + "]";
        }
    }
}
