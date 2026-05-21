package utils.commands;

import gamelogic.GameLogic;
import gamelogic.Lane;
import gamelogic.Node;
import gamelogic.RoadNetwork;
import gamelogic.Vehicle;
import java.util.Map;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;
import utils.ObjectRegistry;

public class Move implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-vehicle") || (!args.containsKey("-target") && !args.containsKey("-lane")) || !args.containsKey("-net")) {
            Logger.logError("Error: Missing arguments for move command. Required: -vehicle, -target/-lane, -net.");
            return;
        }

        Vehicle vehicle = ObjectRegistry.get(args.get("-vehicle"), Vehicle.class);
        if (vehicle == null) {
            Logger.logError("Error: Vehicle with id " + args.get("-vehicle") + " does not exist.");
            return;
        }


        RoadNetwork net = ObjectRegistry.get(args.get("-net"), RoadNetwork.class);
        if (net == null) {
            Logger.logError("Error: Road network with id " + args.get("-net") + " does not exist.");
            return;
        }


        Node target = ObjectRegistry.get(args.get("-target"), Node.class);
        Lane lane = ObjectRegistry.get(args.get("-lane"), Lane.class);
        if (target != null) {
            GameLogic.getInstance().moveVehicle(vehicle, target);
        }
        else if (lane != null) {
            GameLogic.getInstance().moveVehicle(vehicle, lane);
        }
         else {
            Logger.logError("Error: Either target node or target lane must be specified for move command.");
            return;
        }
    }
    
}
