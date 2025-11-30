package Utils;

import java.util.concurrent.TimeUnit;

public class Timer {

    private static long startTime;

    public static void startTimer(){
        startTime = System.nanoTime();
    }


    public static void printEndTime() {
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        long millis = TimeUnit.NANOSECONDS.toMillis(totalTime);
        long seconds = TimeUnit.NANOSECONDS.toSeconds(totalTime);
        System.out.println("SECONDS[" + seconds + "] MILIS[" + millis + "] NANOS [" + totalTime + "]");
    }
}
