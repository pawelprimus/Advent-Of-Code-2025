package DAY_10;

import Reader.FileReader;
import Reader.InputType;
import Utils.ResultLogger;
import Utils.Timer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DAY_10_1 {

    private static final String DAY = "10";
    private static final String PART = "_1";

    public static void main(String[] args) throws Exception {
        Timer.startTimer();
        String[] input = FileReader.readFileAsString(DAY, InputType.NORMAL).split("[\\r\\n]+");

        List<Machine> machines = new ArrayList<>();

        for (String str : input) {
            System.out.println("=======");
            machines.add(new Machine(str));
        }
        int result = 0;
        for (Machine machine : machines) {
            result += machine.findMinButtonClicks();
            System.out.println(machine);
        }


        System.out.println("RESULT: " + result);
        Timer.printEndTime();

        // Log the result to file
        long executionTime = Timer.getExecutionTimeNanos();
        ResultLogger.logResult(PART, DAY, executionTime, String.valueOf(result)); //535
    }


    public static class Machine {
        Integer lightDiagram;
        List<Button> buttons = new ArrayList<>();
        String joltageRequirements;

        public Machine(String str) {
            String[] line = str.split(" ");

            char[] charArray = line[0].replaceAll("\\[", "").replaceAll("]", "").toCharArray();
            StringBuilder sb = new StringBuilder();
            for (char c : charArray) {
                if (c == '.') {
                    sb.append("0");
                } else { // #
                    sb.append("1");
                }
            }
            this.lightDiagram = Integer.parseInt(sb.reverse().toString(), 2);

            for (int i = 1; i < line.length - 1; i++) {
                buttons.add(new Button(line[i], charArray.length));
            }

            this.joltageRequirements = line[line.length - 1];
        }

        public int findMinButtonClicks() {
            int numButtons = buttons.size();
            int minPresses = Integer.MAX_VALUE;

            // 1 << numButtons == Math.pow(2,numButtons) == 2^numButtons
            for (int mask = 0; mask < (1 << numButtons); mask++) {
                int state = 0;

                for (int i = 0; i < numButtons; i++) {
                    if (isBitSet(mask, i)) {
                        System.out.println(Integer.toBinaryString(mask) + " " + Integer.toBinaryString(i));
                        state = state ^ buttons.get(i).wires;  // ^ ->  XOR operation
                    }
                }

                if (state == lightDiagram) {
                    int presses = Integer.bitCount(mask);
                    minPresses = Math.min(minPresses, presses);
                }

            }


            return minPresses;
        }

        private boolean isBitSet(int number, int bitPosition) {
            return (number & (1 << bitPosition)) != 0;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Machine:\n");
            sb.append("  Lights:   ").append(lightDiagram)
                    .append(" (binary: ").append(Integer.toBinaryString(lightDiagram)).append(")\n");
            sb.append("  Buttons (").append(buttons.size()).append("):\n");
            for (int i = 0; i < buttons.size(); i++) {
                sb.append("    [").append(i).append("] ").append(buttons.get(i)).append("\n");
            }
            sb.append("  Joltage:  ").append(joltageRequirements);
            return sb.toString();
        }

    }

    public static class Button {
        Integer wires;

        public Button(String str, int length) {
            char[] wiresArray = "0".repeat(length).toCharArray();

            String[] nums = str.replaceAll("\\(", "").replaceAll("\\)", "").split(",");
            for (String num : nums) {
                wiresArray[Integer.parseInt(num)] = '1';
            }

            String binaryStr = new String(wiresArray);
            this.wires = Integer.parseInt(new StringBuilder(binaryStr).reverse().toString(), 2);  // ← Reverse!
        }

        @Override
        public String toString() {
            return wires + " (binary: " + Integer.toBinaryString(wires) + ")";
        }

    }
}