import java.util.ArrayList;
import java.util.List;

public class Logger {
    static boolean enabled = true;

    static List<String> logMessages = new ArrayList<>();
    static List<String> commands = new ArrayList<>();

    public static void log(String message) {
        System.out.print(message);
        if (enabled) {
            logMessages.add(message);
        }
    }

    public static void logLine(String message) {
        System.out.println(message);
        if (enabled) {
            logMessages.add(message);
        }
    }

    public static void logError(String message) {
        System.err.println(message);
    }

    public static void logCommand(String command) {
        if (enabled) {
            commands.add(command);
        }
    }

    public static void setEnabled(boolean enabled) {
        Logger.enabled = enabled;
    }
    
}
