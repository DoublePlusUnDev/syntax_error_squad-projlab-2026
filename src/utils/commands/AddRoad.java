package utils.commands;

import java.util.Map;

import gamelogic.Bridge;
import gamelogic.Node;
import gamelogic.RoadNetwork;
import gamelogic.RoadSegment;
import gamelogic.Tunnel;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;
import utils.ObjectRegistry;

public class AddRoad implements Command{

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-id") || !args.containsKey("-net") || !args.containsKey("-start") || !args.containsKey("-end")) {
            Logger.logError("Error: Missing arguments for addroad command. Required: -id, -net, -start, -end.");
            return;
        }
        
        RoadNetwork net = ObjectRegistry.get(args.get("-net"), RoadNetwork.class);
        if (net == null) {
            Logger.logError("Error: Road network with id " + args.get("-net") + " does not exist.");
            return;
        }

        Node start = ObjectRegistry.get(args.get("-start"), Node.class);
        if (start == null) {
            Logger.logError("Error: Start node with id " + args.get("-start") + " does not exist.");
            return;
        }

        Node end = ObjectRegistry.get(args.get("-end"), Node.class);
        if (end == null) {
            Logger.logError("Error: End node with id " + args.get("-end") + " does not exist.");
            return;
        }

        int lanes = 1;
        if (args.containsKey("-lanes")) {
            try {
                lanes = Integer.parseInt(args.get("-lanes"));
            } catch (NumberFormatException e) {
                Logger.logError("Error: Invalid number format for lanes. Using default value of 1.");
            }
        }

        RoadSegment road;
        if (args.containsKey("-type")) {
            String type = args.get("-type");
            if (type.equals("road")) {
                road = new RoadSegment(args.get("-id"), lanes, start, end);
            }
            else if (type.equals("bridge")) {
                road = new Bridge(args.get("-id"), lanes, start, end);
            }
            else if (type.equals("tunnel")) {
                road = new Tunnel(args.get("-id"), lanes, start, end);
            }
            else {
                Logger.logError("Error: Invalid road type. Use road, bridge, or tunnel. Using default type of road.");
                road = new RoadSegment(args.get("-id"), lanes, start, end);
            }
        }
        else {
            road = new RoadSegment(args.get("-id"), lanes, start, end);
        }

        net.addRoadSegment(road);
    }
    
}
