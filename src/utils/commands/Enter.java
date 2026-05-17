package utils.commands;

import java.util.Map;

import gamelogic.Node;
import gamelogic.Vehicle;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;
import utils.ObjectRegistry;

public class Enter implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-vehicle") || !args.containsKey("-target")) {
            Logger.logError("Error: Missing arguments for enter command. Required: -vehicle, -target.");
            return;
        }

        Vehicle vehicle = ObjectRegistry.get(args.get("-vehicle"), Vehicle.class);
        if (vehicle == null) {
            Logger.logError("Error: Vehicle with id " + args.get("-vehicle") + " does not exist.");
            return;
        }

        Node target = ObjectRegistry.get(args.get("-target"), Node.class);
        if (target == null) {
            Logger.logError("Error: Node with id " + args.get("-target") + " does not exist.");
            return;
        }

        target.accept(vehicle);
    }
    
}
