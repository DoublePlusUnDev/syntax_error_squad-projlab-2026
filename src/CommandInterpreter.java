import java.io.File;
import java.util.Map;
import java.util.Scanner;

public class CommandInterpreter {
    public interface Command {
        void execute(Map<String, String> args);
        
    }

    Map<String, Command> commands = Map.of(
        "help", this::help
    );

    public CommandInterpreter() {

    }

    public void execute(String command) {
            String[] parts = command.split(" ");
            String commandName = parts[0];
            
            Map<String, String> args = Map.of(); 
            for (int i = 1; i < parts.length; i+=2) {
                String argName = parts[i];
                String argValue = parts[i+1];
                args.put(argName, argValue);
            }

            Command cmd = commands.get(commandName);
            if (cmd != null) {
                cmd.execute(args);
            } else {
                System.out.println("Unknown command: " + commandName);
                System.out.println("Run help for list of available commands.");
            }
    }
    
    public void help(Map<String, String> args) {
        File helpFile = new File("resources/help.txt");
        
        try (Scanner scanner = new Scanner(helpFile)) {
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (Exception e) {
            System.out.println("Error reading help file: " + e.getMessage());
        }
    }
}
