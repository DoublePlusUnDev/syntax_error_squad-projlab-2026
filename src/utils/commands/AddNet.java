package utils.commands;

import java.util.Map;

import gamelogic.GameLogic;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;

public class AddNet implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-id")) {
            Logger.logError("Error: Missing -id argument for addnet command.");
            return;
        }

        GameLogic.getInstance().makeRoads(args.get("-id"));
    }
    
}
