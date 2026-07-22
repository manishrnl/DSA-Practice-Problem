import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Console logging utility strictly adhering to Spring Boot's default ANSI color mapping.
 */
public class Colorful_Console_Logs {

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m"; // ERROR & FATAL
    private static final String YELLOW = "\u001B[33m"; // WARN
    private static final String GREEN = "\u001B[32m"; // INFO, DEBUG, TRACE, SUCCESS
    private static final String CYAN = "\u001B[36m"; // Optional accent color
    private static final String GRAY = "\u001B[90m"; // Timestamps & separators

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    private static String time() {
        return GRAY + LocalTime.now().format(TIME_FORMATTER) + RESET;
    }

    public void info(String message) {
        System.out.print("\n" + time() + " " + GREEN + "INFO  --- " + message + RESET);
    }

    public void warn(String message) {
        System.out.print("\n" + time() + " " + YELLOW + "WARN  --- " + message + RESET);
    }

    public void error(String message) {
        System.out.print("\n" + time() + " " + RED + "ERROR --- " + message + RESET);
    }

    public void debug(String message) {
        System.out.print("\n" + time() + " " + CYAN + "DEBUG --- " + message + RESET);
    }

    public void success(String message) {
        System.out.print("\n" + time() + " " + GREEN + "SUCCESS - " + message + RESET);
    }
}