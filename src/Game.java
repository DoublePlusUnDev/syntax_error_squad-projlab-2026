import gamelogic.GameLogic;
import ui.GameWindow;
import ui.SettingsManager;
import utils.CommandInterpreter;

public class Game {
    
    public static void main(String[] args) {
        CommandInterpreter commandInterpreter = new CommandInterpreter();
        SettingsManager settingsManager = new SettingsManager();

        GameWindow gameWindow = new GameWindow(commandInterpreter, GameLogic.getInstance(), settingsManager);
    }
}
