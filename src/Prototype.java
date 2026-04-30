import java.util.Scanner;

public class Prototype {
    public static void main(String[] args) {
        CommandInterpreter ci = new CommandInterpreter();
        
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            ci.execute(input);
        }
    }
    
}
