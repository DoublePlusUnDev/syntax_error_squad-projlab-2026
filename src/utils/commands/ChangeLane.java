package utils.commands;

import java.util.Map;

import gamelogic.GameLogic;
import gamelogic.RoadNetwork;
import gamelogic.Vehicle;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;
import utils.ObjectRegistry;

public class ChangeLane implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-vehicle") || !args.containsKey("-lane") || !args.containsKey("-net")) {
            Logger.logError("Error: Missing arguments for changelane command. Required: -vehicle, -lane, -net.");
            return;
        }

        Vehicle vehicle = ObjectRegistry.get(args.get("-vehicle"), Vehicle.class);
        if (vehicle == null) {
            Logger.logError("Error: Vehicle with id " + args.get("-vehicle") + " does not exist.");
            return;
        }

        int laneNumber = 0;
        try {
            laneNumber = Integer.parseInt(args.get("-lane"));
        } catch (NumberFormatException e) {
            Logger.logError("Error: Invalid number format for lane. Please provide a valid integer.");
            return;
        }

        RoadNetwork net = ObjectRegistry.get(args.get("-net"), RoadNetwork.class);
        if (net == null) {
            Logger.logError("Error: Road network with id " + args.get("-net") + " does not exist.");
            return;
        }

        GameLogic.getInstance().changeLane(vehicle, laneNumber);
    }
    
}
