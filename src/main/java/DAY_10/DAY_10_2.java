package DAY_10;

import Reader.FileReader;
import Reader.InputType;
import Utils.ResultLogger;
import Utils.Timer;

import java.util.*;

public class DAY_10_2 {

    private static final String DAY = "10";
    private static final String PART = "_2";

    public static void main(String[] args) throws Exception {
        Timer.startTimer();
        String[] input = FileReader.readFileAsString(DAY, InputType.NORMAL).split("[\\r\\n]+");

        List<Machine> machines = new ArrayList<>();

        for (String str : input) {
            System.out.println("=======");
            machines.add(new Machine(str));
        }
        int result = 0;

        for (int i = 0; i < machines.size(); i++) {
            System.out.println(i + "/" + machines.size());
            Machine machine = machines.get(i);
            int minClicks = machine.findMinButtonClicks_2();
            System.out.println(minClicks);
            result += minClicks;

        }


        System.out.println("RESULT: " + result);
        Timer.printEndTime();

        // Log the result to file
        long executionTime = Timer.getExecutionTimeNanos();
        ResultLogger.logResult(PART, DAY, executionTime, String.valueOf(result)); //535
    }


    public static class Machine {
        //Integer lightDiagram;
        List<Button> buttons = new ArrayList<>();
        int[] joltageRequirements;

        public Machine(String str) {
            String[] line = str.split(" ");

            char[] charArray = line[0].replaceAll("\\[", "").replaceAll("]", "").toCharArray();
            int size = charArray.length;

            //this.lightDiagram = Integer.parseInt(sb.reverse().toString(), 2);

            for (int i = 1; i < line.length - 1; i++) {
                buttons.add(new Button(line[i], size));
            }
            String[] joltageReqStrings = line[line.length - 1].replaceAll("\\{", "").replaceAll("}", "").split(",");

            joltageRequirements = new int[size];
            for (int i = 0; i < size; i++) {
                joltageRequirements[i] = Integer.parseInt(joltageReqStrings[i]);
            }

        }

        public int findMinButtonClicks() {
            int joltageReqLength = joltageRequirements.length;

            PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(s -> s.clicks + heuristic(s.clicksCounter)));
            Set<Integer> visited = new HashSet<>();

            int[] start = new int[joltageReqLength];
            pq.add(new State(start, 0));


            while (!pq.isEmpty()) {
                State currentState = pq.poll();
                //System.out.println(currentState);
                // CHECK if we reached joltage req
                if (Arrays.equals(joltageRequirements, currentState.clicksCounter)) {
                    return currentState.clicks;
                }

                int key = Arrays.hashCode(currentState.clicksCounter);
                if (visited.contains(key)) {
                    //System.out.println("CONTINUTE");
                    continue;
                }
                visited.add(key);
                // TRY pressing each button
                for (Button button : buttons) {
                    int[] clonedCounter = currentState.clicksCounter.clone();
                    for (int wires : button.wires) {
                        clonedCounter[wires]++;
                    }
                    // System.out.println("!exceedsTarget");
                    int newKey = Arrays.hashCode(clonedCounter);
                    if (!visited.contains(newKey) && !exceedsTarget(clonedCounter, joltageRequirements)) {
                        // System.out.println("ADD");
                        pq.add(new State(clonedCounter, currentState.clicks + 1));
                    }

                }

            }

            return -1;
        }


        // SOLUTION MADE BASED ON https://www.reddit.com/r/adventofcode/comments/1pk87hl/2025_day_10_part_2_bifurcate_your_way_to_victory/
        public int findMinButtonClicks_2() {
            Map<String, Integer> memo = new HashMap<>();
            return solve(joltageRequirements.clone(), memo);
        }

        private int solve(int[] joltage, Map<String, Integer> memo) {
            String key = Arrays.toString(joltage);
            if (memo.containsKey(key)) {
                return memo.get(key);
            }

            // Base case
            boolean allZero = true;
            for (int v : joltage) {
                if (v != 0) {
                    allZero = false;
                    break;
                }
            }
            if (allZero) {
                return 0;
            }

            int buttonsSize = buttons.size();
            int best = Integer.MAX_VALUE / 4;

            // Enumerate all parity patterns (Phase 1)
            for (int mask = 0; mask < Math.pow(2, buttonsSize); mask++) {

                int[] remaining = joltage.clone();
                int presses = 0;
                boolean valid = true;

                // Apply Phase 1 presses
                for (int b = 0; b < buttonsSize; b++) {
                    if ((mask & (1 << b)) != 0) {
                        presses++;
                        for (int wire : buttons.get(b).wires) {
                            remaining[wire]--;
                            if (remaining[wire] < 0) {
                                valid = false;
                                break;
                            }
                        }
                    }
                    if (!valid) break;
                }

                if (!valid) continue;

                // Check all remaining joltages are even
                for (int v : remaining) {
                    if ((v % 2) != 0) {
                        valid = false;
                        break;
                    }
                }
                if (!valid) continue;

                // Divide by 2 (Phase 2)
                for (int i = 0; i < remaining.length; i++) {
                    remaining[i] /= 2;
                }

                int sub = solve(remaining, memo);
                if (sub < Integer.MAX_VALUE / 4) {
                    best = Math.min(best, presses + 2 * sub);
                }
            }

            memo.put(key, best);
            return best;
        }




        boolean exceedsTarget(int[] counters, int[] target) {
            for (int i = 0; i < counters.length; i++) {
                if (counters[i] > target[i]) {
                    return true;
                }
            }
            return false;
        }

        int heuristic(int[] current) {
            int maxRemaining = 0;
            for (int i = 0; i < current.length; i++) {
                maxRemaining = Math.max(maxRemaining, joltageRequirements[i] - current[i]);
            }
            return maxRemaining;
        }

        @Override
        public String toString() {
            return "Machine{" +
                    "buttons=" + buttons +
                    ", joltageRequirements=" + Arrays.toString(joltageRequirements) +
                    '}';
        }
    }

    public static class State {
        int clicksCounter[];
        int clicks;

        public State(int[] clicksCounter, int clicks) {
            this.clicksCounter = clicksCounter;
            this.clicks = clicks;
        }



        @Override
        public String toString() {
            return "State{" +
                    "clicksCounter=" + Arrays.toString(clicksCounter) +
                    ", clicks=" + clicks +
                    '}';
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof State)) return false;
            return Arrays.equals(clicksCounter, ((State) o).clicksCounter);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(clicksCounter);
        }
    }

    public static class Button {
        int[] wires;

        public Button(String str, int length) {
            char[] wiresArray = "0".repeat(length).toCharArray();
            String[] nums = str.replaceAll("\\(", "").replaceAll("\\)", "").split(",");
            wires = new int[nums.length];

            for (int i = 0; i < wires.length; i++) {
                wires[i] = Integer.parseInt(nums[i]);
            }
        }

        @Override
        public String toString() {
            return "Button{" +
                    "wires=" + Arrays.toString(wires) +
                    '}';
        }
    }
}