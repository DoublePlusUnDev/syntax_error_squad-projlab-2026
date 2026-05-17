package utils.commands;

import java.util.Map;

import gamelogic.Lane;
import gamelogic.RoadNetwork;
import gamelogic.Vehicle;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;
import utils.ObjectRegistry;

public class Slip implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-vehicle") || !args.containsKey("-target") || !args.containsKey("-net")) {
            Logger.logError("Error: Missing arguments for slip command. Required: -vehicle, -target, -net.");
            return;
        }

        Vehicle vehicle = ObjectRegistry.get(args.get("-vehicle"), Vehicle.class);
        if (vehicle == null) {
            Logger.logError("Error: Vehicle with id " + args.get("-vehicle") + " does not exist.");
            return;
        }

        Lane target = ObjectRegistry.get(args.get("-target"), Lane.class);
        if (target == null) {
            Logger.logError("Error: Lane with id " + args.get("-target") + " does not exist.");
            return;
        }

        RoadNetwork net = ObjectRegistry.get(args.get("-net"), RoadNetwork.class);
        if (net == null) {
            Logger.logError("Error: Road network with id " + args.get("-net") + " does not exist.");
            return;
        }

        net.slip(vehicle, target);
    }
    
}
