package utils.commands;

import java.util.Map;

import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;
import utils.RandomGenerator;

public class Seed implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-seed")) {
            Logger.logError("Error: Missing -seed argument for seed command.");
            return;
        }

        String seed = args.get("-seed");
        RandomGenerator.setSeed(seed);
    }

    
}
