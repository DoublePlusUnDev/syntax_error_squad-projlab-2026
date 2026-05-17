package utils.commands;

import java.util.Map;

import gamelogic.Lane;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;
import utils.ObjectRegistry;

public class ModLane implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-id")) {
            Logger.logError("Error: Missing -id argument for modlane command.");
            return;
        }

        Lane lane = ObjectRegistry.get(args.get("-id"), Lane.class);
        if (lane == null) {
            Logger.logError("Error: Lane with id " + args.get("-id") + " does not exist.");
            return;
        }

        if (args.containsKey("-snow")) {
            float snow = Float.parseFloat(args.get("-snow"));
            lane.setSnowHeight(snow);
        }

        if (args.containsKey("-iced")) {
            boolean iced = Boolean.parseBoolean(args.get("-iced"));
            lane.setIced(iced);
        }

        if (args.containsKey("-icing")){
            int icing = Integer.parseInt(args.get("-icing"));
            lane.setIcingProgress(icing);
        }

        if (args.containsKey("-block")) {
            boolean block = Boolean.parseBoolean(args.get("-block"));
            lane.setVehicleBlock(block);
        }

        if (args.containsKey("-debris")) {
            boolean debris = Boolean.parseBoolean(args.get("-debris"));
            lane.setIceDebris(debris);
        }

        if (args.containsKey("-salted")) {
            int salted = Integer.parseInt(args.get("-salted"));
            lane.setSaltedTimer(salted);
        }

        if (args.containsKey("-gravel")) {
            float gravel = Float.parseFloat(args.get("-gravel"));
            lane.setGravelHeight(gravel);
        }
    }
    
}
