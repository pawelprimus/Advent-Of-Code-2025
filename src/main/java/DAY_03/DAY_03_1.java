package DAY_03;

import Reader.FileReader;
import Reader.InputType;
import Utils.Timer;
// 2025-12-03 T:23:44:00 - 2025-12-04 T:00:08:37

public class DAY_03_1 {

    private static final String DAY = "03";

    public static void main(String[] args) throws Exception {
        Timer.startTimer();
        String[] input = FileReader.readFileAsString(DAY, InputType.NORMAL).split("[\\r\\n]+");

        int result = 0;

        for (String str : input) {
            int[] allNums = getNumsFromLine(str);
            int[] indexedNums = getIndexedArray(allNums);
            int lineMax = 0;

            for (int i = 0; i < allNums.length - 1; i++) {
                int currentNum = allNums[i];

                indexedNums[currentNum]--;
                int max = getMax(indexedNums);
                String strNum = "" + currentNum + max;
                int loopMax = Integer.parseInt(strNum);
                if (loopMax > lineMax) {
                    lineMax = loopMax;
                }

            }
            result += lineMax;

            System.out.println(str + " -> " + lineMax);
        }


        System.out.println("RESULT: " + result); // 17359
        Timer.printEndTime();
    }

    private static int getMax(int[] nums) {
        for (int i = 9; i > 0; i--) {
            if (nums[i] > 0) {
                return i;
            }
        }
        return 0;
    }

    private static int[] getIndexedArray(int[] nums) {
        int[] indexedNums = new int[10];
        for (int i : nums) {
            indexedNums[i]++;
        }
        return indexedNums;
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
