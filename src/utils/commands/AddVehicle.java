package utils.commands;

import gamelogic.Car;
import gamelogic.GameLogic;
import gamelogic.Lane;
import gamelogic.RoadNetwork;
import gamelogic.SnowPlow;
import gamelogic.SnowPlowPlayer;
import java.util.Map;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;
import utils.ObjectRegistry;

public class AddVehicle implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-id")) {
            Logger.logError("Error: Missing -id argument for addVehicle command.");
            return;
        }
        if (!args.containsKey("-lane")) {
            Logger.logError("Error: Missing -lane argument for addVehicle command.");
            return;
        }
        if (!args.containsKey("-type")) {
            Logger.logError("Error: Missing -type argument for addVehicle command.");
            return;
        }

        if (!args.containsKey("-net")) {
            Logger.logError("Error: Missing -net argument for addVehicle command.");
            return;
        }

        RoadNetwork net = ObjectRegistry.get(args.get("-net"), RoadNetwork.class);
        if (net == null) {
            Logger.logError("Error: Road network with id " + args.get("-net") + " does not exist.");
            return;
        }

        String vehicleType = args.get("-type");
        switch (vehicleType) {
            case "car":
                Car newCar = new Car(args.get("-id"), null, null);
                ObjectRegistry.register(args.get("-id"), newCar);
                GameLogic.getInstance().addCar(newCar, ObjectRegistry.get(args.get("-lane"), Lane.class)); 
                break;
            case "snowplow":
                if (!args.containsKey("-player")) {
                    Logger.logError("Error: Missing -player argument for adding snowplow. A snowplow must be assigned to a player.");
                    return;
                }
                
                
                SnowPlowPlayer player = ObjectRegistry.get(args.get("-player"), SnowPlowPlayer.class);
                SnowPlow newPlow = new SnowPlow(args.get("-id"), player);
                ObjectRegistry.register(args.get("-id"), newPlow);
                player.addSnowPlow(newPlow, ObjectRegistry.get(args.get("-lane"), Lane.class));

                break;
            default:
                Logger.logError("Error: Invalid vehicle type. Use snowplow, bus or car.");
                return;
        }
    }
    
}
