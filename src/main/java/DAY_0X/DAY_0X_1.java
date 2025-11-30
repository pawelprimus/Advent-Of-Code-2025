package DAY_0X;

import Reader.FileReader;
import Reader.InputType;
import Utils.Timer;

public class DAY_0X_1 {

    private static final String DAY = "0X";

    public static void main(String[] args) throws Exception {
        Timer.startTimer();
        String[] input = FileReader.readFileAsString(DAY, InputType.TEST).split("[\\r\\n]+");
        String result = "";

        System.out.println("RESULT: " + result);
        Timer.printEndTime();
    }
}
