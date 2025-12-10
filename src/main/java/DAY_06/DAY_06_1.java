package DAY_06;

import Reader.FileReader;
import Reader.InputType;
import Utils.Timer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DAY_06_1 {

    private static final String DAY = "06";


    public static void main(String[] args) throws Exception {
        Timer.startTimer();
        String[] input = FileReader.readFileAsString(DAY, InputType.NORMAL).split("[\\r\\n]+");
        BigDecimal result = BigDecimal.ZERO;


        List<OperationToMake> operations = new ArrayList<>();
        for (int i = 0; i < input[0].trim().split("\\s+").length; i++) {
            operations.add(new OperationToMake());
        }

        for (int i = 0; i < input.length; i++) {
            String[] lineChars = input[i].trim().split("\\s+");
            for (int j = 0; j < lineChars.length; j++) {
                operations.get(j).add(lineChars[j]);
            }
        }

        for (OperationToMake op : operations) {
            result = result.add(op.calculate());
        }


        System.out.println("RESULT: " + result); // 7326876294741
        Timer.printEndTime();
    }

    static class OperationToMake {
        List<String> arr = new ArrayList<>();

        public void add(String str) {
            arr.add(str);
        }

        public BigDecimal calculate() {
            String operation = arr.getLast();
            BigDecimal result = BigDecimal.ZERO;
            if (operation.equals("+")) {
                for (int i = 0; i < arr.size() - 1; i++) {
                    result = result.add(new BigDecimal(arr.get(i)));
                }
            } else {
                result = new BigDecimal(arr.get(0));
                for (int i = 1; i < arr.size() - 1; i++) {
                    result = result.multiply(new BigDecimal(arr.get(i)));
                }
            }
            System.out.println("CALCULATE: " + result);
            return result;
        }

        @Override
        public String toString() {
            return "OperationToMake{" +
                    "arr=" + arr +
                    '}';
        }
    }

}
