package DAY_08;

import Reader.FileReader;
import Reader.InputType;
import Utils.ResultLogger;
import Utils.Timer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class DAY_08_2 {

    private static final String DAY = "08";
    private static final String PART = "_2";

    public static void main(String[] args) throws Exception {
        Timer.startTimer();
        String[] input = FileReader.readFileAsString(DAY, InputType.NORMAL).split("[\\r\\n]+");

        List<Point> points = new ArrayList<>();

        for (String str : input) {
            String[] splited = str.split(",");
            points.add(new Point(splited[0], splited[1], splited[2]));
        }


        List<Pair> pairs = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {

            for (int j = i; j < points.size(); j++) {
                double distance = getDistanceBeetwenPoints(points.get(i), points.get(j));
                if (distance != 0) {
                    pairs.add(new Pair(points.get(i), points.get(j), distance));
                }

                //System.out.println(points.get(i) + " -> " + points.get(j) + " ==>" + distance);
            }
        }

        System.out.println(pairs.size());
        pairs.sort(Comparator.comparingDouble(Pair::getDistance).reversed());
        List<Group> groups = new ArrayList<>();

        Pair lastPairThatIsNotInBoth = null;

        while (!pairs.isEmpty()) {

            // for (int index = 0; index < 1000; index++) {
            Pair minDistancePair = pairs.getLast();
            System.out.println(minDistancePair);

            boolean isContain = false;
            boolean isInBoth = false;

            int containsCounter = 0;

            List<Group> containsGroup = new ArrayList<>();
            for (Group group : groups) {

                if (group.isContainsBoth(minDistancePair)) {
                    System.out.println("CONTAINS TO BOTH");
                    isContain = true;
                    isInBoth = true;
                    break;
                }
                if (group.isContains(minDistancePair)) {
                    containsGroup.add(group);
                    isContain = true;
                }

            }

            if (containsGroup.size() == 1) {
                containsGroup.getFirst().add(minDistancePair);
            }
            if (containsGroup.size() > 1) {
                containsGroup.get(0).add(minDistancePair);

                for (int i = 1; i < containsGroup.size(); i++) {
                    containsGroup.get(0).mergeGroup(containsGroup.get(i));
                    groups.remove(containsGroup.get(i));
                }
            }

            if (!isContain) {
                groups.add(new Group(minDistancePair));
            }

            if (!isInBoth) {
                lastPairThatIsNotInBoth = pairs.getLast();
            }


//            for (Group group : groups) {
//                System.out.println(group.getSize());
//            }

            pairs.removeLast();
        }


        //  }

        groups.sort(Comparator.comparingInt(Group::getSize).reversed());

        for (Group group : groups) {
            System.out.println(group);
        }


//        for (int i = 0; i < 3; i++) {
//            result *= groups.get(i).getSize();
//        }

        System.out.println(groups.size());

        System.out.println("LAST PAIR" + lastPairThatIsNotInBoth);

        BigInteger result = BigInteger.valueOf(lastPairThatIsNotInBoth.getPoint1().getX()).multiply(BigInteger.valueOf(lastPairThatIsNotInBoth.getPoint2().getX()));

        System.out.println("RESULT: " + result); // 244188
        Timer.printEndTime();

        // Log the result to file
        long executionTime = Timer.getExecutionTimeNanos();
        ResultLogger.logResult(PART, DAY, executionTime, String.valueOf(result));
    }

    private static double getDistanceBeetwenPoints(Point point_1, Point point_2) {
        return Math.sqrt(
                Math.pow(point_1.getX() - point_2.getX(), 2) +
                        Math.pow(point_1.getY() - point_2.getY(), 2) +
                        Math.pow(point_1.getZ() - point_2.getZ(), 2)
        );


    }

    public static class Pair {
        Point point1;
        Point point2;
        double distance;

        public Pair(Point point1, Point point2, double distance) {
            this.point1 = point1;
            this.point2 = point2;
            this.distance = distance;
        }

        public Point getPoint1() {
            return point1;
        }

        public Point getPoint2() {
            return point2;
        }

        public double getDistance() {
            return distance;
        }

        @Override
        public String toString() {
            return String.format("Pair[%s <-> %s, distance=",
                    point1, point2) + distance + "]";
        }
    }

    public static class Group {
        private List<Point> points;

        public Group(Pair pair) {
            points = new ArrayList<>();
            points.add(pair.getPoint1());
            points.add(pair.getPoint2());
        }

//        public void add(Point point) {
//            points.add(point);
//        }

        public void add(Pair pair) {
            if (!points.contains(pair.getPoint1())) {
                points.add(pair.getPoint1());
            }
            if (!points.contains(pair.getPoint2())) {
                points.add(pair.getPoint2());
            }
        }

        public void mergeGroup(Group group) {
            for (Point point : group.points) {
                if (!points.contains(point)) {
                    this.points.add(point);
                }
            }
        }

        public void clear() {
            this.points.clear();
        }

        public int getSize() {
            return points.size();
        }

        public boolean isContains(Point point) {
            return points.contains(point);
        }

        public boolean isContains(Pair pair) {
            return points.contains(pair.getPoint1()) || points.contains(pair.getPoint2());
        }

        public boolean isContainsBoth(Pair pair) {
            return points.contains(pair.getPoint1()) && points.contains(pair.getPoint2());
        }

        @Override
        public String toString() {
            return "Group" + points;
        }

    }


    public static class Point {
        int x;
        int y;
        int z;

        public Point(String x, String y, String z) {
            this.x = Integer.parseInt(x);
            this.y = Integer.parseInt(y);
            this.z = Integer.parseInt(z);
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y && z == point.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }

        @Override
        public String toString() {
            return "[" + x + "," + y + "," + z + "]";
        }
    }
}
