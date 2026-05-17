package utils.commands;

import java.nio.file.Paths;
import java.util.Map;

import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;

public class SaveLog implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-path")) {
            Logger.logError("Error: Missing -path argument for savelog command.");
            return;
        }
        Logger.saveLog(Paths.get(args.get("-path")));
    }
    
}
