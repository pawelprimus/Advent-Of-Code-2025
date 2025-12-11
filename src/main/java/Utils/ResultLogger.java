package Utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ResultLogger {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Logs the execution result to a text file named after the calling class
     *
     * @param part               0 or 1
     * @param day                The day number (e.g., "07")
     * @param executionTimeNanos Execution time in nanoseconds
     * @param result             The result string to log
     */
    public static void logResult(String part, String day, long executionTimeNanos, String result) {
        String filePath = "src/main/java/DAY_" + day + "/DAY_" + day + part + ".txt";

        String timestamp = LocalDateTime.now().format(DATE_FORMAT);

        long millis = executionTimeNanos / 1_000_000;
        long seconds = executionTimeNanos / 1_000_000_000;

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(timestamp + " | " + seconds + "s (" + millis + "ms, " + executionTimeNanos + "ns) | " + result);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}