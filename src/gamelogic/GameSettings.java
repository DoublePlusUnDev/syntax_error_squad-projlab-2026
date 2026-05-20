package gamelogic;

import java.util.HashMap;
import java.util.Map;

public class GameSettings {
    private Map<String, Object> settings = new HashMap<>();
    public static final String SNOW_CHANCE_KEY = "snowChance";
    public static final String SNOW_NODES_KEY = "snowNodes";
    public static final String SNOW_PLOW_PLAYERS_KEY = "snowPlowPlayers";
    public static final String BUS_PLAYERS_KEY = "busPlayers";

    public void setSetting(String key, Object value) {
        settings.put(key, value);
    }

    public Object getSetting(String key) {
        return settings.get(key);
    }

    public static final GameSettings defaultSettings = new GameSettings() {{
        setSetting(SNOW_CHANCE_KEY, 30);
        setSetting(SNOW_NODES_KEY, 2);
        setSetting(SNOW_PLOW_PLAYERS_KEY, 2);
        setSetting(BUS_PLAYERS_KEY, 1);
     }};
}
