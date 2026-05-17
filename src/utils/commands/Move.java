package utils.commands;

import java.util.Map;

import gamelogic.GameLogic;
import gamelogic.Node;
import gamelogic.RoadNetwork;
import gamelogic.Vehicle;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;
import utils.ObjectRegistry;

public class Move implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-vehicle") || !args.containsKey("-target") || !args.containsKey("-net")) {
            Logger.logError("Error: Missing arguments for move command. Required: -vehicle, -target, -net.");
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

        RoadNetwork net = ObjectRegistry.get(args.get("-net"), RoadNetwork.class);
        if (net == null) {
            Logger.logError("Error: Road network with id " + args.get("-net") + " does not exist.");
            return;
        }
        
        GameLogic.getInstance().moveVehicle(vehicle, target);
    }
    
}
