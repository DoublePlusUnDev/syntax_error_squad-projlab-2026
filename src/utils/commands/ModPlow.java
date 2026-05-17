package utils.commands;

import java.util.Map;

import gamelogic.PlowHead;
import gamelogic.SnowPlow;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;
import utils.ObjectRegistry;

public class ModPlow implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-id")) {
            Logger.logError("Error: Missing -id argument for modplow command.");
            return;
        }

        SnowPlow plow = ObjectRegistry.get(args.get("-id"), SnowPlow.class);
        if (plow == null) {
            Logger.logError("Error: Snow plow with id " + args.get("-id") + " does not exist.");
            return;
        }

        if (args.containsKey("-head")) {
            String head = args.get("-head");
            PlowHead plowHead = ObjectRegistry.get(head, PlowHead.class);
            plow.equip(plowHead);
        }
    }
    
}
