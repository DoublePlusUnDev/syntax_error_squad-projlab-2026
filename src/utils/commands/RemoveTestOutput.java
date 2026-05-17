package utils.commands;

import java.util.Map;

import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;

public class RemoveTestOutput implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        interpreter.getTestRunner().removeTestOutputs();
    }
    
}
