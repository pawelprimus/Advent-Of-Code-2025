package DAY_01;

import Reader.FileReader;
import Reader.InputType;
import Utils.Timer;

import java.util.ArrayList;
import java.util.List;

public class DAY_01_1 {

    private static final String DAY = "01";

    public static void main(String[] args) throws Exception {

        String[] input = FileReader.readFileAsString(DAY, InputType.TEST).split("[\\r\\n]+");


        System.out.println("RESULT: " + input);
    }
}
