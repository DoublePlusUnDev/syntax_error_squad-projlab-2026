package utils.commands;

import gamelogic.GameLogic;
import java.util.Map;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;

public class EndTurn implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        GameLogic.getInstance().endTurn();
    }
    
}
