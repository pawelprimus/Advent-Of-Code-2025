package DAY_01;

import Reader.FileReader;
import Reader.InputType;

// 2025-12-02 T:21:50:21 - 2025-12-02 T:22:14:17
public class DAY_01_2 {

    private static final String DAY = "01";

    public static void main(String[] args) throws Exception {

        String[] input = FileReader.readFileAsString(DAY, InputType.NORMAL).split("[\\r\\n]+");
        int result = 0;
        int rotatePoint = 50;
        for (String line : input) {
            char dir = line.charAt(0);
            int rotate = Integer.valueOf(line.substring(1, line.length()));
            System.out.println(dir + " -> " + rotate);
            if (rotate >= 100) {
                result += rotate / 100;
                rotate = rotate % 100;
            }
            if (dir == 'L') {
                // ROTATE TO LEFT
                rotatePoint -= rotate;

                if (rotatePoint < 0) {
                    if(Math.abs(rotatePoint) != rotate){
                        result++;
                    }

                    rotatePoint += 100;
                }

            } else {
                // ROTATE TO RIGHT
                rotatePoint += rotate;
                if (rotatePoint >= 100) {
                    if (rotatePoint != 100) {
                        result++;
                    }
                    rotatePoint -= 100;
                }
            }

            if (rotatePoint == 0) {
                result++;
            }

        }


        System.out.println("RESULT: " + result); // 6684
    }
}
