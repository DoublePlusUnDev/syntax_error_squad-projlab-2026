public class Logger {
    static boolean enabled = true;

    public static void log(String message) {
        if (enabled) {
            System.out.print(message);
        }
    }

    public static void logLine(String message) {
        if (enabled) {
            System.out.println(message);
        }
    }

    public static void setEnabled(boolean enabled) {
        Logger.enabled = enabled;
    }
    
}
