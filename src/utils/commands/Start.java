package utils.commands;

import gamelogic.GameLogic;
import gamelogic.GameSettings;
import java.util.Map;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;

public class Start implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        GameSettings settings = GameSettings.defaultSettings;
        // initialize defaults from the static testParams map if present
        try {
            // Command line args are stored with their leading dash (e.g. "-nodeMin"),
            // SettingsManager builds flags with a leading dash, so read that form here.
            if (args.containsKey("-" + GameSettings.SNOW_CHANCE_KEY))
                settings.setSetting(GameSettings.SNOW_CHANCE_KEY, Integer.parseInt(args.get("-" + GameSettings.SNOW_CHANCE_KEY)));
            if (args.containsKey("-" + GameSettings.SNOW_NODES_KEY))
                settings.setSetting(GameSettings.SNOW_NODES_KEY, Integer.parseInt(args.get("-" + GameSettings.SNOW_NODES_KEY)));
            if (args.containsKey("-" + GameSettings.SNOW_PLOW_PLAYERS_KEY))
                settings.setSetting(GameSettings.SNOW_PLOW_PLAYERS_KEY, Integer.parseInt(args.get("-" + GameSettings.SNOW_PLOW_PLAYERS_KEY)));
            if (args.containsKey("-" + GameSettings.BUS_PLAYERS_KEY))
                settings.setSetting(GameSettings.BUS_PLAYERS_KEY, Integer.parseInt(args.get("-" + GameSettings.BUS_PLAYERS_KEY)));  
        }
        catch (NumberFormatException e) {
            Logger.logError("Error: Invalid number format in generate command arguments. " + e.getMessage());
            return;
        }

        GameLogic.getInstance().startGame(settings);
    }
    
}
