package utils.commands;

import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;

import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;

public class LoadGame implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-path")) {
            Logger.logError("Error: Missing -path argument for loadgame command.");
            return;
        }
        
        try (Scanner fileScanner = new Scanner(Paths.get(args.get("-path")).toFile())) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                
                if (line.isBlank() || line.startsWith("#")) {
                    continue; // Skip empty lines and comments
                }
                //System.out.println(line);

                interpreter.execute(line);
            }
        } catch (Exception e) {
            Logger.logError("Error loading game: " + e.getMessage());
        }
    }
    
}
