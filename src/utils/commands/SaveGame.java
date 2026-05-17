package utils.commands;

import java.nio.file.Paths;
import java.util.Map;

import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;

public class SaveGame implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-path")) {
            Logger.logError("Error: Missing -path argument for savelog command.");
            return;
        }

        Logger.saveGameState(Paths.get(args.get("-path")));
    }
    
}
