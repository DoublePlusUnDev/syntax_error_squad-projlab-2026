package utils.commands;

import java.util.Map;

import gamelogic.MoneyBank;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;
import utils.ObjectRegistry;

public class SetMoney implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-bank") || !args.containsKey("-amount")) {
            Logger.logError("Error: Missing arguments for setmoney command. Required: -bank, -amount.");
            return;
        }

        MoneyBank bank = ObjectRegistry.get(args.get("-bank"), MoneyBank.class);
        if (bank == null) {
            Logger.logError("Error: Money bank with id " + args.get("-bank") + " does not exist.");
            return;
        }

        int amount = 0;
        try {
            amount = Integer.parseInt(args.get("-amount"));
        } catch (NumberFormatException e) {
            Logger.logError("Error: Invalid number format for amount. Please provide a valid integer.");
            return;
        }

        bank.setMoney(amount);
    }
    
}
