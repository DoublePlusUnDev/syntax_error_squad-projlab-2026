
import java.util.Scanner;

public class TestUtil {
    public static final Scanner scanner = new Scanner(System.in);

    static int chainDepth;

    public static void enterFunction(String functionName){
        tabulatePrintln("->" + functionName);
        chainDepth++;
    }

    public static void exitFunction(String exitMessage){
        chainDepth--;
        tabulatePrintln("<-" + exitMessage);
    }

    public static boolean askUserYesNo(String question){
        tabulatePrint(question);

        String input = scanner.nextLine();

        return !input.isEmpty() && input.toLowerCase().charAt(0) == 'y';        
    }
 
    static void tabulatePrint(String message) {
        System.out.print("\t".repeat(chainDepth) + message);
    }

    static void tabulatePrintln(String message) {
        System.out.print("\t".repeat(chainDepth) + message + "\n");
    }
}
