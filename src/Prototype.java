import java.util.Scanner;

public class Prototype {
    public static void main(String[] args) {
        CommandInterpreter ci = new CommandInterpreter();
        
        Scanner scanner = new Scanner(System.in);
        TestRunner testRunner = new TestRunner();
        testRunner.runTest(scanner.nextLine());


        
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            ci.execute(input);
        }
    }
    
}
