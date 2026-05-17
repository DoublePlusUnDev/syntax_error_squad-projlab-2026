package utils.commands;

import java.util.Map;

import gamelogic.Apartment;
import gamelogic.BusStop;
import gamelogic.Node;
import gamelogic.RoadNetwork;
import gamelogic.Workplace;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;
import utils.ObjectRegistry;

public class AddNode implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-id") || !args.containsKey("-net")) {
            Logger.logError("Error: Missing arguments for addnode command. Required: -id, -net.");
            return;
        }
        
        RoadNetwork net = ObjectRegistry.get(args.get("-net"), RoadNetwork.class);
        if (net == null) {
            Logger.logError("Error: Road network with id " + args.get("-net") + " does not exist.");
            return;
        }

        if (args.containsKey("-type")) {
            String type = args.get("-type");
            if (type.equals("busstop")) {
                net.addNode(new BusStop(args.get("-id")));
            }
            else if (type.equals("workplace")) {
                net.addNode(new Workplace(args.get("-id")));
            }
            else if (type.equals("apartment")) {
                net.addNode(new Apartment(args.get("-id")));
            }
            else {
                Logger.logError("Error: Invalid node type. Use busstop, workplace, or apartment. Using default type of busstop.");
                net.addNode(new BusStop(args.get("-id")));
            }
        }
        else {
            net.addNode(new Node(args.get("-id")));
        }
    }
    
}
