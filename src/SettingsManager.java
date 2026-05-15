import java.util.HashMap;
import java.util.Map;

public class SettingsManager  {
    Map<String, Object> settings = new HashMap<>();

    public void setSetting(String key, Object value) {
        settings.put(key, value);
    }
}
