package utils.commands;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;

import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;

public class Help implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        Path helpPath = Paths.get("resources", "help.txt");

        try (Scanner scanner = new Scanner(helpPath.toFile())) {
            while (scanner.hasNextLine()) {
                Logger.logLine(scanner.nextLine());
            }
        } catch (Exception e) {
            Logger.logError("Error reading help file: " + e.getMessage());
        }
    }
    
}
