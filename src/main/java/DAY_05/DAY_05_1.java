package DAY_05;

import Reader.FileReader;
import Reader.InputType;
import Utils.Timer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DAY_05_1 {

    private static final String DAY = "05";

    public static void main(String[] args) throws Exception {
        Timer.startTimer();
        String[] input = FileReader.readFileAsString(DAY, InputType.NORMAL).split("[\\r\\n]+");

        int result = 0;

        List<Range> ranges = new ArrayList<>();
        List<BigDecimal> nums = new ArrayList<>();
        for (String str : input) {
            if (str.contains("-")) {
                ranges.add(new Range(str));
            } else {
                nums.add(new BigDecimal(str));
            }

        }

        for (BigDecimal num : nums) {

            for (Range range : ranges) {
                if (range.isFresh(num)) {
                    result++;
                    break;
                }
            }

        }


        System.out.println("RESULT: " + result);
        Timer.printEndTime();
    }

    static class Range {
        BigDecimal start;
        BigDecimal end;

        public Range(String rangeStr) {
            this.start = new BigDecimal(rangeStr.split("-")[0]);
            this.end = new BigDecimal(rangeStr.split("-")[1]);
        }

        public boolean isFresh(BigDecimal num) {
            return num.compareTo(start) >= 0 && num.compareTo(end) <= 0;
        }

    }
}
