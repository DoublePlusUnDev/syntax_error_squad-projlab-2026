package utils.commands;

import java.util.Map;

import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;

public class TestAll implements Command {
    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        boolean output = false;
        if (args.containsKey("-output")) {
            output = Boolean.parseBoolean(args.get("-output"));
        }

        interpreter.getTestRunner().runAllTests(output);
    }
    
}
