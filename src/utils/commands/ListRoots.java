package utils.commands;

import java.util.Map;

import gamelogic.Car;
import gamelogic.GameLogic;
import gamelogic.Player;
import utils.CommandInterpreter;
import utils.CommandInterpreter.Command;
import utils.Logger;

public class ListRoots implements Command {

    @Override
    public void execute(Map<String, String> args, CommandInterpreter interpreter) {
        Logger.logLine("Root objects details:");
        Logger.logLine("Road network:");
        if (GameLogic.getInstance().getRoads() != null) {
            Logger.logLine("- " + GameLogic.getInstance().getRoads().id);
        }
        else {
            Logger.logLine("- No road network found.");
        }

        Logger.logLine("Cars:");
        for (Car car : GameLogic.getInstance().getCars()) {
            Logger.logLine("- " + car.id);
        }

        Logger.logLine("Players:");
        for (Player player : GameLogic.getInstance().getPlayers()) {
            Logger.logLine("- " + player.id);
        }
    }
    
}
