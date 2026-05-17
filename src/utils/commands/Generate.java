package utils.commands;

import java.util.Map;

import gamelogic.GameLogic;
import gamelogic.RoadGenerationParameters;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;

public class Generate implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        RoadGenerationParameters params = RoadGenerationParameters.defaultParams;
        // initialize defaults from the static testParams map if present
        try {
            // Command line args are stored with their leading dash (e.g. "-nodeMin"),
            // SettingsManager builds flags with a leading dash, so read that form here.
            if (args.containsKey("-" + RoadGenerationParameters.NODE_MIN_KEY))
                params.setParameter(RoadGenerationParameters.NODE_MIN_KEY, Integer.parseInt(args.get("-" + RoadGenerationParameters.NODE_MIN_KEY)));
            if (args.containsKey("-" + RoadGenerationParameters.NODE_MAX_KEY))
                params.setParameter(RoadGenerationParameters.NODE_MAX_KEY, Integer.parseInt(args.get("-" + RoadGenerationParameters.NODE_MAX_KEY)));
            if (args.containsKey("-" + RoadGenerationParameters.MAIN_LANES_KEY))
                params.setParameter(RoadGenerationParameters.MAIN_LANES_KEY, Integer.parseInt(args.get("-" + RoadGenerationParameters.MAIN_LANES_KEY)));
            if (args.containsKey("-" + RoadGenerationParameters.SMALL_NODES_MIN_KEY))
                params.setParameter(RoadGenerationParameters.SMALL_NODES_MIN_KEY, Integer.parseInt(args.get("-" + RoadGenerationParameters.SMALL_NODES_MIN_KEY)));
            if (args.containsKey("-" + RoadGenerationParameters.SMALL_NODES_MAX_KEY))
                params.setParameter(RoadGenerationParameters.SMALL_NODES_MAX_KEY, Integer.parseInt(args.get("-" + RoadGenerationParameters.SMALL_NODES_MAX_KEY)));
            if (args.containsKey("-" + RoadGenerationParameters.SMALL_NODE_LANES_KEY))
                params.setParameter(RoadGenerationParameters.SMALL_NODE_LANES_KEY, Integer.parseInt(args.get("-" + RoadGenerationParameters.SMALL_NODE_LANES_KEY)));
            if (args.containsKey("-" + RoadGenerationParameters.SMALL_NODE_EXTRA_ROADS_KEY))
                params.setParameter(RoadGenerationParameters.SMALL_NODE_EXTRA_ROADS_KEY, Integer.parseInt(args.get("-" + RoadGenerationParameters.SMALL_NODE_EXTRA_ROADS_KEY)));
            if (args.containsKey("-" + RoadGenerationParameters.BIG_NODES_MIN_KEY))
                params.setParameter(RoadGenerationParameters.BIG_NODES_MIN_KEY, Integer.parseInt(args.get("-" + RoadGenerationParameters.BIG_NODES_MIN_KEY)));
            if (args.containsKey("-" + RoadGenerationParameters.BIG_NODES_MAX_KEY))
                params.setParameter(RoadGenerationParameters.BIG_NODES_MAX_KEY, Integer.parseInt(args.get("-" + RoadGenerationParameters.BIG_NODES_MAX_KEY)));
            if (args.containsKey("-" + RoadGenerationParameters.BIG_NODE_LANES_KEY))
                params.setParameter(RoadGenerationParameters.BIG_NODE_LANES_KEY, Integer.parseInt(args.get("-" + RoadGenerationParameters.BIG_NODE_LANES_KEY)));
            if (args.containsKey("-" + RoadGenerationParameters.BIG_NODE_EXTRA_ROADS_KEY))
                params.setParameter(RoadGenerationParameters.BIG_NODE_EXTRA_ROADS_KEY, Integer.parseInt(args.get("-" + RoadGenerationParameters.BIG_NODE_EXTRA_ROADS_KEY)));
            if (args.containsKey("-" + RoadGenerationParameters.BUS_STOPS_MIN_KEY))
                params.setParameter(RoadGenerationParameters.BUS_STOPS_MIN_KEY, Integer.parseInt(args.get("-" + RoadGenerationParameters.BUS_STOPS_MIN_KEY)));
            if (args.containsKey("-" + RoadGenerationParameters.BUS_STOPS_MAX_KEY))
                params.setParameter(RoadGenerationParameters.BUS_STOPS_MAX_KEY, Integer.parseInt(args.get("-" + RoadGenerationParameters.BUS_STOPS_MAX_KEY)));
            if (args.containsKey("-" + RoadGenerationParameters.WORK_PLACES_MIN_KEY))
                params.setParameter(RoadGenerationParameters.WORK_PLACES_MIN_KEY, Integer.parseInt(args.get("-" + RoadGenerationParameters.WORK_PLACES_MIN_KEY)));
            if (args.containsKey("-" + RoadGenerationParameters.WORK_PLACES_MAX_KEY))
                params.setParameter(RoadGenerationParameters.WORK_PLACES_MAX_KEY, Integer.parseInt(args.get("-" + RoadGenerationParameters.WORK_PLACES_MAX_KEY)));
            if (args.containsKey("-" + RoadGenerationParameters.APARTMENTS_MIN_KEY))
                params.setParameter(RoadGenerationParameters.APARTMENTS_MIN_KEY, Integer.parseInt(args.get("-" + RoadGenerationParameters.APARTMENTS_MIN_KEY)));
            if (args.containsKey("-" + RoadGenerationParameters.APARTMENTS_MAX_KEY))
                params.setParameter(RoadGenerationParameters.APARTMENTS_MAX_KEY, Integer.parseInt(args.get("-" + RoadGenerationParameters.APARTMENTS_MAX_KEY)));
        }
        catch (NumberFormatException e) {
            Logger.logError("Error: Invalid number format in generate command arguments. " + e.getMessage());
            return;
        }

        GameLogic.getInstance().makeRoads(args.get("-id"));
        GameLogic.getInstance().getRoads().setGenerationParameters(params);
        GameLogic.getInstance().getRoads().generate();
    }
    
}
