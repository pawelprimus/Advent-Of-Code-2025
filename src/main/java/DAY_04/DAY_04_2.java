package DAY_04;

import Reader.FileReader;
import Reader.InputType;
import Utils.Colours;
import Utils.Timer;

public class DAY_04_2 {

    private static final String DAY = "04";

    private static int MAX_I;
    private static int MAX_J;
    private static final char PAPER_ROLL = '@';

    public static void main(String[] args) throws Exception {
        Timer.startTimer();
        String[] input = FileReader.readFileAsString(DAY, InputType.NORMAL).split("[\\r\\n]+");
        int result = 0;
        MAX_I = input.length;
        MAX_J = input[0].length();

        char[][] arr = new char[MAX_I][MAX_J];

        for (int i = 0; i < input.length; i++) {
            char[] lineChars = input[i].toCharArray();
            for (int j = 0; j < lineChars.length; j++) {
                arr[i][j] = lineChars[j];
            }
        }

        int initRolls = countRolls(arr);
        int rolls = 0;

        while (true) {

            for (int i = 0; i < MAX_I; i++) {
                for (int j = 0; j < MAX_J; j++) {
                    if (check(arr, i, j)) {
                        arr[i][j] = '.';
                        // System.out.print(Colours.ANSI_RED + arr[i][j] + Colours.ANSI_RESET);
                    } else {
                        //System.out.print(arr[i][j]);
                    }

                }
            }
            int rollsAfterClean = countRolls(arr);

            if (rolls != rollsAfterClean) {
                rolls = rollsAfterClean;
            } else {
                break;
            }
        }

        result = initRolls - rolls;

        System.out.println("RESULT: " + result); // 9280
        Timer.printEndTime();
    }

    private static int countRolls(char[][] arr) {
        int paperRolls = 0;
        for (int i = 0; i < MAX_I; i++) {
            for (int j = 0; j < MAX_J; j++) {
                if (arr[i][j] == PAPER_ROLL) {
                    paperRolls++;
                }
            }
        }
        return paperRolls;
    }


    private static boolean check(char arr[][], int i, int j) {
        int paperRolls = 0;

        if (arr[i][j] != PAPER_ROLL) {
            return false;
        }

        // UP LEFT
        if (isCorrectI(i - 1) && isCorrectJ(j - 1)) {
            if (arr[i - 1][j - 1] == PAPER_ROLL) {
                paperRolls++;
            }
        }

        // UP
        if (isCorrectI(i - 1) && isCorrectJ(j)) {
            if (arr[i - 1][j] == PAPER_ROLL) {
                paperRolls++;
            }
        }

        // UP RIGHT
        if (isCorrectI(i - 1) && isCorrectJ(j + 1)) {
            if (arr[i - 1][j + 1] == PAPER_ROLL) {
                paperRolls++;
            }
        }

        // RIGHT
        if (isCorrectI(i) && isCorrectJ(j + 1)) {
            if (arr[i][j + 1] == PAPER_ROLL) {
                paperRolls++;
            }
        }

        // RIGHT DOWN
        if (isCorrectI(i + 1) && isCorrectJ(j + 1)) {
            if (arr[i + 1][j + 1] == PAPER_ROLL) {
                paperRolls++;
            }
        }

        // DOWN
        if (isCorrectI(i + 1) && isCorrectJ(j)) {
            if (arr[i + 1][j] == PAPER_ROLL) {
                paperRolls++;
            }
        }

        // DOWN LEFT
        if (isCorrectI(i + 1) && isCorrectJ(j - 1)) {
            if (arr[i + 1][j - 1] == PAPER_ROLL) {
                paperRolls++;
            }
        }

        // LEFT
        if (isCorrectI(i) && isCorrectJ(j - 1)) {
            if (arr[i][j - 1] == PAPER_ROLL) {
                paperRolls++;
            }
        }

        return paperRolls < 4;
    }


    private static boolean isCorrectI(int num) {
        return num >= 0 && num <= MAX_I - 1;
    }

    private static boolean isCorrectJ(int num) {
        return num >= 0 && num <= MAX_J - 1;
    }

}
