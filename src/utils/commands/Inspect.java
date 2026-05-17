package utils.commands;

import java.util.Map;

import gamelogic.Inspectable;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;
import utils.ObjectRegistry;

public class Inspect implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-id")) {
            Logger.logError("Error: Missing -id argument for inspect command.");
            return;
        }

        Inspectable inspectable = ObjectRegistry.get(args.get("-id"), Inspectable.class);
        if (inspectable != null) {
            inspectable.inspect();
        } else {
            Logger.logError("Object with id " + args.get("-id") + " is not inspectable or does not exist.");
        }
    }
    
}
