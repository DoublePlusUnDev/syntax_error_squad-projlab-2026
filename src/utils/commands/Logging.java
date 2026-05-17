package utils.commands;

import java.util.Map;

import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;

public class Logging implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-enable")) {
            Logger.logError("Error: Missing -enable argument for logging command.");
            return;
        }

        boolean enable = Boolean.parseBoolean(args.get("-enable"));
        Logger.setHistoryEnabled(enable);
    }

    
}
