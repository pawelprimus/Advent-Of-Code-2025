package DAY_06;

import Reader.FileReader;
import Reader.InputType;
import Utils.Timer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DAY_06_2 {

    private static final String DAY = "06";


    public static void main(String[] args) throws Exception {
        Timer.startTimer();
        String[] input = FileReader.readFileAsString(DAY, InputType.NORMAL).split("[\\r\\n]+");
        BigDecimal result = BigDecimal.ZERO;

        int maxLength = Arrays.stream(input)
                .mapToInt(String::length)
                .max()
                .orElse(0);


        // NORMALIZE
        char[][] charArray = new char[input.length][maxLength];

        for (int i = 0; i < input.length; i++) {
            char[] lineChars = input[i].toCharArray();
            for (int j = 0; j < maxLength; j++) {
                if (j < lineChars.length) {
                    charArray[i][j] = lineChars[j];
                } else {
                    charArray[i][j] = ' ';
                }
            }
        }

        for (int i = 0; i < charArray[0].length; i++) {
            boolean isEmptyLine = true;
            for (int j = 0; j < charArray.length - 1; j++) {

                if (charArray[j][i] != ' ') {
                    isEmptyLine = false;
                }
            }
            if (!isEmptyLine) {
                for (int j = 0; j < charArray.length - 1; j++) {

                    if (charArray[j][i] == ' ') {
                        charArray[j][i] = 'X';
                    }
                }
            }
        }

        // PREPARE OPERATIONS
        List<OperationToMake> operations = new ArrayList<>();

        String[] operationAsString = new String(charArray[charArray.length - 1]).split("\\s+");

        for (String str : operationAsString) {
            operations.add(new OperationToMake(str));
        }

        for (int i = 0; i < charArray.length - 1; i++) {
            String[] line = new String(charArray[i]).split(" ");
            for (int j = 0; j < line.length; j++) {
                operations.get(j).add(line[j]);
            }
            System.out.println();
        }

        // CALCULATE
        for (OperationToMake op : operations) {
            result = result.add(op.calculate());
        }

        System.out.println("RESULT: " + result); // 10756006415204
        Timer.printEndTime();
    }

    static class OperationToMake {
        List<String> arr = new ArrayList<>();

        String operation;

        public OperationToMake(String operation) {
            this.operation = operation;
        }

        public void add(String str) {
            arr.add(str);
        }


        public void normalize() {
            int maxLength = arr.stream()
                    .mapToInt(String::length)
                    .max()
                    .getAsInt();

            String operation = arr.getLast();

            for (int i = 0; i < arr.size() - 1; i++) {
                arr.set(i, arr.get(i) + "X".repeat(maxLength - arr.get(i).length()));
            }

//            if (operation.equals("+")) {
//                for (int i = 0; i < arr.size() - 1; i++) {
//                    arr.set(i, arr.get(i) + "0".repeat(maxLength - arr.get(i).length()));
//                }
//            } else {
//                for (int i = 0; i < arr.size() - 1; i++) {
//                    arr.set(i, arr.get(i) + "1".repeat(maxLength - arr.get(i).length()));
//                }
//            }

        }


        public BigDecimal calculate() {
            BigDecimal result = BigDecimal.ZERO;

            int size = arr.get(0).length();

            List<BigDecimal> bg = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < arr.size(); j++) {
                    char c = arr.get(j).charAt(i);
                    if (c != 'X') {
                        sb.append(c);
                    }
                }
                System.out.println(sb);
                bg.add(new BigDecimal(sb.toString()));
            }

            if (operation.equals("+")) {

                for (BigDecimal b : bg) {
                    result = result.add(b);
                }

            } else {
                result = BigDecimal.ONE;
                for (BigDecimal b : bg) {
                    result = result.multiply(b);
                }
            }


            System.out.println("CALCULATE: " + result);
            return result;
        }

        @Override
        public String toString() {
            return "OperationToMake{" +
                    "op=" + operation +
                    "arr=" + arr +
                    '}';
        }
    }

}
