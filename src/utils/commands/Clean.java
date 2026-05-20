package utils.commands;

import gamelogic.Lane;
import gamelogic.buyables.PlowHead;
import java.util.Map;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;
import utils.ObjectRegistry;

public class Clean implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-head") || !args.containsKey("-target")) {
            Logger.logError("Error: Missing arguments for clean command. Required: -head, -target.");
            return;
        }
        
        PlowHead head = ObjectRegistry.get(args.get("-head"), PlowHead.class);
        if (head == null) {
            Logger.logError("Error: Plow head with id " + args.get("-head") + " does not exist.");
            return;
        }

        Lane target = ObjectRegistry.get(args.get("-target"), Lane.class);
        if (target == null) {
            Logger.logError("Error: Lane with id " + args.get("-target") + " does not exist.");
            return;
        }

        head.clean(target);
    }
    
}
