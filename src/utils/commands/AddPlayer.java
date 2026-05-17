package utils.commands;

import java.util.Map;

import gamelogic.BusPlayer;
import gamelogic.GameLogic;
import gamelogic.Lane;
import gamelogic.RoadNetwork;
import gamelogic.SnowPlowPlayer;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;
import utils.ObjectRegistry;

public class AddPlayer implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-id") || !args.containsKey("-net") || !args.containsKey("-lane")) {
            Logger.logError("Error: Missing -id -net -lane arguments for addplayer command.");
            return;
        }

        RoadNetwork net = ObjectRegistry.get(args.get("-net"), RoadNetwork.class);
        if (net == null) {
            Logger.logError("Error: Road network with id " + args.get("-net") + " does not exist.");
            return;
        }

        Lane lane = ObjectRegistry.get(args.get("-lane"), Lane.class);
        if (lane == null) {
            Logger.logError("Error: Lane with id " + args.get("-lane") + " does not exist.");
            return;
        }

        if (args.containsKey("-type")) {
            String type = args.get("-type");
            if (type.equals("snowplow")) {
                GameLogic.getInstance().addPlayer(new SnowPlowPlayer(args.get("-id"), net, lane));
            }
            else if (type.equals("bus")){
                GameLogic.getInstance().addPlayer(new BusPlayer(args.get("-id"), net, lane));
            }
            else {
                Logger.logError("Error: Invalid player type. Use snowplow or bus. Using default type of snowplow.");
                GameLogic.getInstance().addPlayer(new SnowPlowPlayer(args.get("-id"), net, lane));
            }
        }else{
            Logger.logError("Error: Missing -type argument for addplayer command. Using default type of snowplow.");
        }
    }
    
}
