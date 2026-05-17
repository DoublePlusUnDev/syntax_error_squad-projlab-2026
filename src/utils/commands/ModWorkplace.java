package utils.commands;

import java.util.Map;

import gamelogic.Workplace;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;
import utils.ObjectRegistry;

public class ModWorkplace implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-id")) {
            Logger.logError("Error: Missing -id argument for modworkplace command.");
            return;
        }
        Workplace workplace = ObjectRegistry.get(args.get("-id"), Workplace.class);
        if (workplace == null) {
            Logger.logError("Error: Workplace with id " + args.get("-id") + " does not exist.");
            return;
        }

        if (args.containsKey("-spawn")) {
            int spawnTimer = Integer.parseInt(args.get("-spawn"));
            workplace.setSpawnTimer(spawnTimer);
        }
    }
    
}
