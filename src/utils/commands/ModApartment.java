package utils.commands;

import java.util.Map;

import gamelogic.Apartment;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;
import utils.ObjectRegistry;

public class ModApartment implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-id")) {
            Logger.logError("Error: Missing -id argument for modapartment command.");
            return;
        }
        Apartment apartment = ObjectRegistry.get(args.get("-id"), Apartment.class);
        if (apartment == null) {
            Logger.logError("Error: Apartment with id " + args.get("-id") + " does not exist.");
            return;
        }
        if (args.containsKey("-spawn")) {
            int spawnTimer = Integer.parseInt(args.get("-spawn"));
            apartment.setSpawnTimer(spawnTimer);
        }
    }
    
}
