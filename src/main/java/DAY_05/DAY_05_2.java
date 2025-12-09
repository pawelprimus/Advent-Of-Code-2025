package DAY_05;

import Reader.FileReader;
import Reader.InputType;
import Utils.Timer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DAY_05_2 {

    private static final String DAY = "05";

    public static void main(String[] args) throws Exception {
        Timer.startTimer();
        String[] input = FileReader.readFileAsString(DAY, InputType.NORMAL).split("[\\r\\n]+");

        BigDecimal result = BigDecimal.ZERO;

        List<Range> ranges = new ArrayList<>();
        List<BigDecimal> nums = new ArrayList<>();
        for (String str : input) {
            if (str.contains("-")) {
                ranges.add(new Range(str));
            } else {
                nums.add(new BigDecimal(str));
            }

        }

        // for (BigDecimal num : nums) {

        for (int i = 0; i < ranges.size(); i++) {

            for (int j = i + 1; j < ranges.size(); j++) {
                ranges.get(i).changeOverlap(ranges.get(j));
            }

        }

        System.out.println("------");
        for (Range range : ranges) {
            System.out.println(range);

            result = result.add(range.amountOfFresh());
        }

        // }


        System.out.println("RESULT: " + result); // 347468726696961
        Timer.printEndTime();
    }

    static class Range {
        BigDecimal start;
        BigDecimal end;

        boolean isDestroyed = false;

        public Range(String rangeStr) {
            this.start = new BigDecimal(rangeStr.split("-")[0]);
            this.end = new BigDecimal(rangeStr.split("-")[1]);
        }

        public void changeOverlap(Range range) {
            if (this.isDestroyed || range.isDestroyed) {
                return;
            }
            System.out.println(this + " -> " + range);


            //  |||         <- this
            //       |||    <- range
            if (this.start.compareTo(range.start) < 0 && this.end.compareTo(range.end) < 0 && this.end.compareTo(range.start) < 0) {
                System.out.println("1");
                return;
            }

            //          ||  <- this
            //     ||||     <- range
            if (this.start.compareTo(range.start) > 0 && this.end.compareTo(range.end) > 0 && this.start.compareTo(range.end) > 0) {
                System.out.println("2");

                return;
            }


            //  ||||||||||  <- this
            //     ||||     <- range
            if (this.start.compareTo(range.start) <= 0 && this.end.compareTo(range.end) >= 0) {
                System.out.println("3");

                range.destroy();
                return;
            }


            //       |||     <- this
            //   ||||||||||  <- range
            if (this.start.compareTo(range.start) >= 0 && this.end.compareTo(range.end) <= 0) {
                System.out.println("4");

                this.destroy();
                return;
            }

            //   ||||||||    <- this
            // |||||||       <- range
            if (this.start.compareTo(range.start) >= 0 && this.end.compareTo(range.end) >= 0) {

                range.setENd(this.start.subtract(BigDecimal.ONE));
                System.out.println("5=" + this + " -> " + range);

                return;
            }

            //   |||||||||   <- this
            //      |||||||||<- range
            if (this.start.compareTo(range.start) <= 0 && this.end.compareTo(range.end) <= 0) {
                range.setStart(this.end.add(BigDecimal.ONE));
                System.out.println("6=" + this + " -> " + range);

                return;
            }

        }

        public void destroy() {
            isDestroyed = true;
        }

        public void setStart(BigDecimal startToSet) {
            this.start = startToSet;
        }

        public void setENd(BigDecimal endToSet) {
            this.end = endToSet;
        }

        BigDecimal amountOfFresh() {
            if (!isDestroyed) {
                return end.subtract(start).add(BigDecimal.ONE);
            }
            return BigDecimal.ZERO;
        }


        @Override
        public String toString() {
            return start + "-" + end;
        }
    }


}
