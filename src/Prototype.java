import java.util.Scanner;

public class Prototype {
    public static void main(String[] args) {
        CommandInterpreter ci = new CommandInterpreter();
        
        TestRunner testRunner = new TestRunner();
        testRunner.runTest("test17");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            ci.execute(input);
        }
    }
    
}
