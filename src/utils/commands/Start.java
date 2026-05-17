package utils.commands;

import java.util.Map;

import gamelogic.GameLogic;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;

public class Start implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        GameLogic.getInstance().startGame();
    }
    
}
