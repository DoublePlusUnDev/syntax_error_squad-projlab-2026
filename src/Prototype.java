import java.util.Scanner;
import utils.CommandInterpreter;

public class Prototype {
    public static void main(String[] args) {
        CommandInterpreter ci = new CommandInterpreter();
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Welcome to the Traffic Simulator Prototype!");
        System.out.println("Made by Syntax Error Squad for BME Projlab");
        System.out.println("Enter commands to interact with the game. Type 'help' for a list of commands.");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            ci.execute(input);
        }
    }
    
}
