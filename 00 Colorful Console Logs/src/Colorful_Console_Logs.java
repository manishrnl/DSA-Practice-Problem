import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Colorful_Console_Logs {

    /**
     * Lightweight console logging utility featuring ANSI colors applied to full messages.
     */
    // ANSI Color Escape Codes
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";
    private static final String PURPLE = "\u001B[35m";
    private static final String GRAY = "\u001B[90m";

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    private static String time() {
        return GRAY + LocalTime.now().format(TIME_FORMATTER) + RESET;
    }

    /**
     * Log general information in full GREEN
     */
    public  void info(String message) {
        System.out.println(time() + " " + GREEN + "INFO  --- " + message + RESET);
    }

    /**
     * Log warnings in full YELLOW
     */
    public  void warn(String message) {
        System.out.println(time() + " " + YELLOW + "WARN  --- " + message + RESET);
    }

    /**
     * Log errors in full RED
     */
    public  void error(String message) {
        System.out.println(time() + " " + RED + "ERROR --- " + message + RESET);
    }

    /**
     * Log debug information in full CYAN
     */
    public  void debug(String message) {
        System.out.println(time() + " " + CYAN + "DEBUG --- " + message + RESET);
    }

    /**
     * Log success results in full PURPLE
     */
    public  void success(String message) {
        System.out.println(time() + " " + PURPLE + "SUCCESS - " + message + RESET);
    }

}
