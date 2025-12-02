package DAY_02;

import Reader.FileReader;
import Reader.InputType;
import Utils.Timer;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.Collectors;

// 2025-12-02 T:22:18:23  -> 2025-12-02 T:22:43:27
public class DAY_02_1 {

    private static final String DAY = "02";

    public static void main(String[] args) throws Exception {
        Timer.startTimer();
        String[] input = Arrays.stream(FileReader.readFileAsString(DAY, InputType.NORMAL).split("[\\r\\n]+")).collect(Collectors.joining()).split(",");

        BigInteger result = BigInteger.ZERO;
        for (String str : input) {
            // int start = Integer.valueOf(str.split("-")[0]);
            BigInteger startBig = BigInteger.valueOf(Long.parseLong(str.split("-")[0]));
            // int end = Integer.valueOf(str.split("-")[1]);
            BigInteger endBig = BigInteger.valueOf(Long.parseLong(str.split("-")[1]));

            for (BigInteger i = startBig; i.compareTo(endBig) <= 0; i = i.add(BigInteger.ONE)) {
                //System.out.println(i);
                result = result.add(isIDInvalid(i));
            }
        }


        System.out.println("RESULT: " + result); // 21139440284
        Timer.printEndTime();
    }

    private static BigInteger isIDInvalid(BigInteger id) {
        String strID = String.valueOf(id);
        if (strID.length() % 2 != 0) {
            return BigInteger.ZERO;
        }
        String leftPart = strID.substring(0, strID.length() / 2);
        String rightPart = strID.substring(strID.length() / 2, strID.length());
        //System.out.println(leftPart + "" + rightPart);
        if (leftPart.equals(rightPart)) {
            return id;
        }
        return BigInteger.ZERO;

    }

}
