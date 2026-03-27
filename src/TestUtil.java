
import java.util.Scanner;

public class TestUtil {
    public static final Scanner scanner = new Scanner(System.in);

    private static int chainDepth;
    private static boolean logging = true;

    public static void enterFunction(String functionName){
        if (!logging)
            return;

        tabulatePrintln("->" + functionName);
        chainDepth++;
    }

    public static void exitFunction(String exitMessage){
        if (!logging)
            return;
        
        chainDepth--;
        tabulatePrintln("<-" + exitMessage);
    }

    public static boolean askUserYesNo(String question){
        tabulatePrintln("? " + question + " (yes/no)");
        tabulatePrint("> ");

        String input = scanner.nextLine();

        return !input.isEmpty() && input.toLowerCase().charAt(0) == 'y';        
    }

    public static int askUserNumberedOptions(String question, String[] options) {
        tabulatePrintln(": " + question);
        for (int i = 0; i < options.length; i++) {
            tabulatePrintln(i + 1 + " " + options[i]);
        }
        tabulatePrint("> ");

        String input = scanner.nextLine();
        return Integer.parseInt(input);
    }
 
    private static void tabulatePrint(String message) {
        System.out.print("\t".repeat(chainDepth) + message);
    }

    private static void tabulatePrintln(String message) {
        System.out.print("\t".repeat(chainDepth) + message + "\n");
    }

    public static void turnOnLogging() {
        logging = true;
    }

    public static void turnOffLogging() {
        logging = false;
    }
}
