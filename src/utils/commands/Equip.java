package utils.commands;

import java.util.Map;

import gamelogic.PlowHead;
import gamelogic.SnowPlow;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;
import utils.ObjectRegistry;

public class Equip implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-vehicle") || !args.containsKey("-head")) {
            Logger.logError("Error: Missing arguments for equip command. Required: -vehicle, -head.");
            return;
        }

        SnowPlow plow = ObjectRegistry.get(args.get("-vehicle"), SnowPlow.class);
        if (plow == null) {
            Logger.logError("Error: Snow plow with id " + args.get("-vehicle") + " does not exist.");
            return;
        }

        PlowHead head = ObjectRegistry.get(args.get("-head"), PlowHead.class);
        if (head == null) {
            Logger.logError("Error: Plow head with id " + args.get("-head") + " does not exist.");
            return;
        }

        plow.equip(head);
    }
    
}
