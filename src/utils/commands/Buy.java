package utils.commands;

import java.util.Map;

import gamelogic.Buyable;
import gamelogic.Inventory;
import gamelogic.MoneyBank;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;
import utils.ObjectRegistry;

public class Buy implements Command{

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-buyable") || !args.containsKey("-inventory") || !args.containsKey("-bank")) {
            Logger.logError("Error: Missing arguments for buy command. Required: -buyable, -inventory, -bank.");
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

        MoneyBank moneyBank = ObjectRegistry.get(args.get("-bank"), MoneyBank.class);
        if (moneyBank == null) {
            Logger.logError("Error: MoneyBank with id " + args.get("-bank") + " does not exist.");
            return;
        }

        buyable.buy(inventory, moneyBank);
    }
    
}
