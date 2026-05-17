package utils.commands;

import java.util.Map;

import gamelogic.Lane;
import gamelogic.RoadNetwork;
import gamelogic.Vehicle;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;
import utils.ObjectRegistry;

public class MoveVehicle implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-vehicle") || !args.containsKey("-lane") || !args.containsKey("-net")) {
            Logger.logError("Error: Missing arguments for movevehicle command. Required: -vehicle, -lane, -net.");
            return;
        }

        Vehicle vehicle = ObjectRegistry.get(args.get("-vehicle"), Vehicle.class);
        if (vehicle == null) {
            Logger.logError("Error: Vehicle with id " + args.get("-vehicle") + " does not exist.");
            return;
        }

        Lane target = ObjectRegistry.get(args.get("-lane"), Lane.class);
        if (target == null) {
            Logger.logError("Error: Lane with id " + args.get("-lane") + " does not exist.");
            return;
        }

        RoadNetwork net = ObjectRegistry.get(args.get("-net"), RoadNetwork.class);
        if (net == null) {
            Logger.logError("Error: Road network with id " + args.get("-net") + " does not exist.");
            return;
        }

        vehicle.setLocation(target);
    }
    
}
