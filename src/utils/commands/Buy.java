package utils.commands;

import gamelogic.Buyable;
import gamelogic.Inventory;
import gamelogic.Player;
import java.util.Map;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;
import utils.ObjectRegistry;

public class Buy implements Command{

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-buyable") || !args.containsKey("-inventory") || !args.containsKey("-player")) {
            Logger.logError("Error: Missing arguments for buy command. Required: -buyable, -inventory, -player.");
            return;
        }

        Buyable buyable = ObjectRegistry.get(args.get("-buyable"), Buyable.class);
        if (buyable == null) {
            Logger.logError("Error: Buyable with id " + args.get("-buyable") + " does not exist.");
            return;
        }

        Inventory inventory = ObjectRegistry.get(args.get("-inventory"), Inventory.class);
        if (inventory == null) {
            Logger.logError("Error: Inventory with id " + args.get("-inventory") + " does not exist.");
            return;
        }

        Player player = ObjectRegistry.get(args.get("-player"), Player.class);
        if (player == null) {
            Logger.logError("Error: Player with id " + args.get("-player") + " does not exist.");
            return;
        }

        buyable.buy(inventory, player);
    }
    
}
