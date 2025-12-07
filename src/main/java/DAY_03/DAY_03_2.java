package DAY_03;

import Reader.FileReader;
import Reader.InputType;
import Utils.Timer;

import java.math.BigDecimal;
// 2025-12-03 T:23:44:00 - 2025-12-04 T:00:08:37

public class DAY_03_2 {

    private static final String DAY = "03";

    public static void main(String[] args) throws Exception {
        Timer.startTimer();
        String[] input = FileReader.readFileAsString(DAY, InputType.NORMAL).split("[\\r\\n]+");

        BigDecimal result = BigDecimal.ZERO;

        for (String str : input) {
            int[] allNums = getNumsFromLine(str);

            BigDecimal lineMax = getLineTopJoltage(allNums);

            result = result.add(lineMax);
        }


        System.out.println("RESULT: " + result); // 172787336861064
        Timer.printEndTime();
    }

    private static BigDecimal getLineTopJoltage(int[] allNums) {
        int AMOUNT_OF_NUMS = 12;
        int indexOfCurrentMax = -1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < AMOUNT_OF_NUMS; i++) {

            int currentMax = 0;
            for (int j = indexOfCurrentMax + 1; j <= allNums.length - AMOUNT_OF_NUMS + i; j++) {
                int currentNum = allNums[j];
                if (currentNum > currentMax) {
                    indexOfCurrentMax = j;
                    currentMax = currentNum;
                }
            }
            sb.append(currentMax);
        }

        return BigDecimal.valueOf(Long.parseLong(sb.toString()));
    }

    private static int[] getNumsFromLine(String str) {
        int[] nums = new int[str.length()];
        int i = 0;
        for (char c : str.toCharArray()) {
            nums[i] = Integer.parseInt(c + "");
            i++;
        }
        return nums;
    }

}
