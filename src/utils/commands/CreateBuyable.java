package utils.commands;

import java.util.Map;

import gamelogic.BioKerosene;
import gamelogic.BlowerHead;
import gamelogic.Buyable;
import gamelogic.DragonHead;
import gamelogic.Gravel;
import gamelogic.GravelThrowerHead;
import gamelogic.IceBreakerHead;
import gamelogic.Salt;
import gamelogic.SalterHead;
import gamelogic.SweeperHead;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;

public class CreateBuyable implements Command {
    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-id") || !args.containsKey("-type")) {
            Logger.logError("Error: Missing -id argument for createbuyable command.");
            return;
        }

        int amount = 1;
        if (args.containsKey("-amount")) {
            try {
                amount = Integer.parseInt(args.get("-amount"));
            } catch (NumberFormatException e) {
                Logger.logError("Error: Invalid number format for amount. Using default value of 1.");
            }
        }

        int price = 0;
        if (args.containsKey("-price")) {
            try {
                price = Integer.parseInt(args.get("-price"));
            } catch (NumberFormatException e) {
                Logger.logError("Error: Invalid number format for price. Using default value of 0.");
            }
        }

        String type = args.get("-type");
        Buyable buyable;
        switch (type) {
            case "salt" -> buyable = new Salt(args.get("-id"), amount, price);
            case "kerosene" -> buyable = new BioKerosene(args.get("-id"), amount, price);
            case "gravel" -> buyable = new Gravel(args.get("-id"), amount, price);
            case "sweeperhead" -> buyable = new SweeperHead(args.get("-id"), price);
            case "blowerhead" -> buyable = new BlowerHead(args.get("-id"), price);
            case "salterhead" -> buyable = new SalterHead(args.get("-id"), price);
            case "dragonhead" -> buyable = new DragonHead(args.get("-id"), price);
            case "gravelhead" -> buyable = new GravelThrowerHead(args.get("-id"), price);
            case "icebreakerhead" -> buyable = new IceBreakerHead(args.get("-id"), price);
            default -> Logger.logError("Error: Invalid buyable type.");
        }
    }
    
}
