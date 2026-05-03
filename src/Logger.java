import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Logger {
    static boolean enabled = true;
    static boolean outputEnabled = true;

    static List<String> logMessages = new ArrayList<>();
    static List<String> commands = new ArrayList<>();

    public static void clearLogs() {
        logMessages.clear();
        commands.clear();
    }

    public static void log(String message) {
        if (outputEnabled) {
            System.out.print(message);
        }
        if (enabled) {
            logMessages.add(message);
        }
    }

    public static void logLine(String message) {
        if (outputEnabled) {
            System.out.println(message);
        }
        
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

    public static void setOutputEnabled(boolean enabled) {
        outputEnabled = enabled;
    }

    public static void saveLog(Path filepath) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(filepath.toFile())) {
            for (String message : logMessages) {
                writer.println(message);
            }
        } catch (java.io.IOException e) {
            System.err.println("Error saving log: " + e.getMessage());
        }
    }

    public static void saveGameState(Path filepath) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(filepath.toFile())) {
            for (String command : commands) {
                writer.println(command);
            }
        } catch (java.io.IOException e) {
            System.err.println("Error saving game state: " + e.getMessage());
        }
    }
    
}
