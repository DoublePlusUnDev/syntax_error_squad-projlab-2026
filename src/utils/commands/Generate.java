package utils.commands;

import gamelogic.GameLogic;
import gamelogic.RoadGenerationParameters;
import java.util.Map;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;

public class Generate implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        RoadGenerationParameters params = RoadGenerationParameters.defaultParams;
        try {
            String[] keys = {
                RoadGenerationParameters.NODE_MIN_KEY,
                RoadGenerationParameters.NODE_MAX_KEY,
                RoadGenerationParameters.MAIN_LANES_KEY,
                RoadGenerationParameters.SMALL_NODES_MIN_KEY,
                RoadGenerationParameters.SMALL_NODES_MAX_KEY,
                RoadGenerationParameters.SMALL_NODE_LANES_KEY,
                RoadGenerationParameters.SMALL_NODE_EXTRA_ROADS_KEY,
                RoadGenerationParameters.BIG_NODES_MIN_KEY,
                RoadGenerationParameters.BIG_NODES_MAX_KEY,
                RoadGenerationParameters.BIG_NODE_LANES_KEY,
                RoadGenerationParameters.BIG_NODE_EXTRA_ROADS_KEY,
                RoadGenerationParameters.BUS_STOPS_MIN_KEY,
                RoadGenerationParameters.BUS_STOPS_MAX_KEY,
                RoadGenerationParameters.WORK_PLACES_MIN_KEY,
                RoadGenerationParameters.WORK_PLACES_MAX_KEY,
                RoadGenerationParameters.APARTMENTS_MIN_KEY,
                RoadGenerationParameters.APARTMENTS_MAX_KEY,
                RoadGenerationParameters.BRIDGES_MIN_KEY,
                RoadGenerationParameters.BRIDGES_MAX_KEY,
                RoadGenerationParameters.TUNNELS_MIN_KEY,
                RoadGenerationParameters.TUNNELS_MAX_KEY
            };

            for (String key : keys) {
                applyIntParameter(args, params, key);
            }
        }
        catch (NumberFormatException e) {
            Logger.logError("Error: Invalid number format in generate command arguments. " + e.getMessage());
            return;
        }

        GameLogic.getInstance().makeRoads(args.get("-id"));
        gamelogic.RoadGenerator.generate(GameLogic.getInstance().getRoads(), params);
    }

    private static void applyIntParameter(Map<String, String> args, RoadGenerationParameters params, String key) {
        String argumentKey = "-" + key;
        if (args.containsKey(argumentKey)) {
            params.setParameter(key, Integer.valueOf(args.get(argumentKey)));
        }
    }
    
}
