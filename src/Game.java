import java.util.HashMap;
import java.util.Map;

public class Game {

    static Map<String, Object> settings;

    public static void main(String[] args) {
        CommandInterpreter commandInterpreter = new CommandInterpreter();
        
        GameUI.getInstance().showMainMenu();

        settings = new HashMap<>();
    }


    public static void setSetting(String key, Object value) {
        settings.put(key, value);
    }
}
