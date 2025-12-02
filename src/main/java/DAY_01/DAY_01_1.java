package DAY_01;

import Reader.FileReader;
import Reader.InputType;
import Utils.Timer;

import java.util.ArrayList;
import java.util.List;

// 2025-12-02 T:21:24:12 -> 2025-12-02 T:21:47:12
public class DAY_01_1 {

    private static final String DAY = "01";

    public static void main(String[] args) throws Exception {

        String[] input = FileReader.readFileAsString(DAY, InputType.NORMAL).split("[\\r\\n]+");
        int result = 0;
        int roatetePoint = 50;
        for (String line : input) {
            char dir = line.charAt(0);
            int rotate = Integer.valueOf(line.substring(1, line.length()));
            System.out.println(dir + " -> " + rotate);
            rotate = rotate%100;
            if (dir == 'L') {
                // ROTATE TO LEFT
                roatetePoint -= rotate;

                if(roatetePoint <0) {
                    roatetePoint += 100;
                }

            } else {
                // ROTATE TO RIGHT
                roatetePoint += rotate;
                if(roatetePoint >= 100) {
                    roatetePoint -= 100;
                }
            }

            if(roatetePoint == 0){
                result++;
            }


            System.out.println(roatetePoint);
        }


        System.out.println("RESULT: " + result); // 1139
    }
}
