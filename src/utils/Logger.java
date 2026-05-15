package utils;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Logger {
    public interface NewLineListener {
        void onNewLine(String line);
    }
    
    static boolean historyEnabled = true;
    static boolean outputEnabled = true;

    public static List<String> logMessages = new ArrayList<>();
    static List<String> commands = new ArrayList<>();

    private static List<NewLineListener> newLineListeners = new ArrayList<>();

    public static void addNewLineListener(NewLineListener listener) {
        newLineListeners.add(listener);
    }

    public static void clearLogs() {
        logMessages.clear();
        commands.clear();
    }

    public static void log(String message) {
        if (outputEnabled) {
            System.out.print(message);
            notify(message);
        }
        if (historyEnabled) {
            logMessages.add(message);
        }
    }

    public static void logLine(String message) {
        if (outputEnabled) {
            System.out.println(message);
            notify(message);
        }
        
        if (historyEnabled) {
            logMessages.add(message);
        }
    }

    public static void logError(String message) {
        System.err.println(message);
        notify(message);
    }

    public static void logCommand(String command) {
        if (historyEnabled) {
            commands.add(command);
        }
    }

    public static void setHistoryEnabled(boolean enabled) {
        Logger.historyEnabled = enabled;
    }

    public static void setOutputEnabled(boolean enabled) {
        outputEnabled = enabled;
    }

    private static void notify(String message) {
        for (NewLineListener listener : newLineListeners) {
            listener.onNewLine(message);
        }
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
