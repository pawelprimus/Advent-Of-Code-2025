package DAY_02;

import Reader.FileReader;
import Reader.InputType;
import Utils.Timer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// 2025-12-02 T:22:44:34 - 2025-12-02 T:23:33:13
public class DAY_02_2 {

    private static final String DAY = "02";

    public static void main(String[] args) throws Exception {
        Timer.startTimer();
        String[] input = Arrays.stream(FileReader.readFileAsString(DAY, InputType.NORMAL).split("[\\r\\n]+")).collect(Collectors.joining()).split(",");

        BigInteger result = BigInteger.ZERO;
        for (String str : input) {
            BigInteger startBig = BigInteger.valueOf(Long.parseLong(str.split("-")[0]));
            BigInteger endBig = BigInteger.valueOf(Long.parseLong(str.split("-")[1]));

            for (BigInteger i = startBig; i.compareTo(endBig) <= 0; i = i.add(BigInteger.ONE)) {
                //System.out.println(i);
                result = result.add(isIDInvalid(i));
            }
        }

        System.out.println("RESULT: " + result); //  38731915928
        Timer.printEndTime();
    }

    private static BigInteger isIDInvalid(BigInteger id) {
        String strID = String.valueOf(id);
        int length = strID.length();
        if (length < 2) {
            return BigInteger.ZERO;
        }
        int[] dividors = getDivisors(length);
        //System.out.println(Arrays.toString(dividors));

        boolean areAllCharsSame = strID.chars().distinct().count() <= 1;
        if (areAllCharsSame) {
            return id;
        }

        for (int i = 1; i < dividors.length - 1; i++) {
            int divisors = dividors[i];
            List<String> subs = new ArrayList<>();
            for (int j = 0; j < length; j = j + divisors) {
                subs.add(strID.substring(j, j + divisors));
            }
            boolean allStringsSame = subs.stream()
                    .distinct()
                    .count() <= 1;
            if (allStringsSame) {
                return id;
            }
            // System.out.println("SUBS:" + subs);
        }

        return BigInteger.ZERO;
    }

    private static int[] getDivisors(int num) {
        if (num == 0) {
            return new int[0];
        }

        int amountOfDivisors = 0;
        for (int i = 1; i <= num; i++) {
            if (num % i == 0) {
                amountOfDivisors++;
            }
        }
        int[] divisors = new int[amountOfDivisors];

        int index = 0;
        for (int i = 1; i <= num; i++) {
            if (num % i == 0) {
                divisors[index] = i;
                index++;
            }
        }
        return divisors;
    }

}
