import java.util.HashMap;
import java.util.Map;

public class Game {

    static Map<String, Object> settings = new HashMap<>();

    public static void main(String[] args) {
        CommandInterpreter commandInterpreter = new CommandInterpreter();
        
        GameUI gameUI = new GameUI(commandInterpreter);
    }


    public static void setSetting(String key, Object value) {
        settings.put(key, value);
    }
}
