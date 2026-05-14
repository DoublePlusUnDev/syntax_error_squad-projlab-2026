import java.util.HashMap;
import java.util.Map;

public class Game {

    static Map<String, Object> settings;

    public static void main(String[] args) {
        CommandInterpreter commandInterpreter = new CommandInterpreter();
        
        GameUI gameUI = new GameUI(commandInterpreter);

        settings = new HashMap<>();
    }


    public static void setSetting(String key, Object value) {
        settings.put(key, value);
    }
}
