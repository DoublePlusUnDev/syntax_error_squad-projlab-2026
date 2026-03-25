
import java.util.Scanner;

public class TestUtil {
    public static final Scanner scanner = new Scanner(System.in);

    public static boolean askUserYesNo(String question){
        System.out.println(question);

        String input = scanner.nextLine();

        return !input.isEmpty() && input.toLowerCase().charAt(0) == 'y';        
    }
    
}
