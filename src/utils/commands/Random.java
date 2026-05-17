package utils.commands;

import java.util.Map;

import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;
import utils.RandomGenerator;
import utils.RandomGeneratorMode;

public class Random implements Command{

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-mode")) {
            Logger.logError("Error: Missing -mode argument for random command.");
            return;
        }
        String mode = args.get("-mode");
        switch (mode) {
            case "always" -> RandomGenerator.setMode(RandomGeneratorMode.ALWAYS);
            case "never" -> RandomGenerator.setMode(RandomGeneratorMode.NEVER);
            case "random" -> RandomGenerator.setMode(RandomGeneratorMode.RANDOM);
            default -> Logger.logError("Error: Invalid mode for random command. Use always, never, or random.");
        }
    }
    
}
