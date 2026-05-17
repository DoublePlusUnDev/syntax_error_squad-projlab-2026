package utils.commands;

import java.util.Map;

import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;

public class Test implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        if (!args.containsKey("-name")) {
            Logger.logError("Error: Missing -name argument for test command.");
            return;
        }

        boolean output = false;
        if (args.containsKey("-output")) {
            output = Boolean.parseBoolean(args.get("-output"));
        }

        interpreter.getTestRunner().runTest(args.get("-name"), output);
    }
    
}
