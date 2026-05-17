package ui;
import java.util.HashMap;
import java.util.Map;

import gamelogic.RoadGenerationParameters;

public class SettingsManager  {
    Map<String, Object> settings = new HashMap<>();

    public void setSetting(String key, Object value) {
        settings.put(key, value);
    }

    public String getRoadGenerator() {
        StringBuilder sb = new StringBuilder("/generate -id net");

        if (settings.containsKey(RoadGenerationParameters.NODE_MIN_KEY))
            sb.append(" -" + RoadGenerationParameters.NODE_MIN_KEY + " ").append(settings.get(RoadGenerationParameters.NODE_MIN_KEY));
        if (settings.containsKey(RoadGenerationParameters.NODE_MAX_KEY))
            sb.append(" -" + RoadGenerationParameters.NODE_MAX_KEY + " ").append(settings.get(RoadGenerationParameters.NODE_MAX_KEY));
        if (settings.containsKey(RoadGenerationParameters.MAIN_LANES_KEY))
            sb.append(" -" + RoadGenerationParameters.MAIN_LANES_KEY + " ").append(settings.get(RoadGenerationParameters.MAIN_LANES_KEY));
        if (settings.containsKey(RoadGenerationParameters.SMALL_NODES_MIN_KEY))
            sb.append(" -" + RoadGenerationParameters.SMALL_NODES_MIN_KEY + " ").append(settings.get(RoadGenerationParameters.SMALL_NODES_MIN_KEY));
        if (settings.containsKey(RoadGenerationParameters.SMALL_NODES_MAX_KEY))  
            sb.append(" -" + RoadGenerationParameters.SMALL_NODES_MAX_KEY + " ").append(settings.get(RoadGenerationParameters.SMALL_NODES_MAX_KEY));
        if (settings.containsKey(RoadGenerationParameters.SMALL_NODE_LANES_KEY))
            sb.append(" -" + RoadGenerationParameters.SMALL_NODE_LANES_KEY + " ").append(settings.get(RoadGenerationParameters.SMALL_NODE_LANES_KEY));
        if (settings.containsKey(RoadGenerationParameters.SMALL_NODE_EXTRA_ROADS_KEY))
            sb.append(" -" + RoadGenerationParameters.SMALL_NODE_EXTRA_ROADS_KEY + " ").append(settings.get(RoadGenerationParameters.SMALL_NODE_EXTRA_ROADS_KEY));
        if (settings.containsKey(RoadGenerationParameters.BIG_NODES_MIN_KEY))
            sb.append(" -" + RoadGenerationParameters.BIG_NODES_MIN_KEY + " ").append(settings.get(RoadGenerationParameters.BIG_NODES_MIN_KEY));
        if (settings.containsKey(RoadGenerationParameters.BIG_NODES_MAX_KEY))
            sb.append(" -" + RoadGenerationParameters.BIG_NODES_MAX_KEY + " ").append(settings.get(RoadGenerationParameters.BIG_NODES_MAX_KEY));
        if (settings.containsKey(RoadGenerationParameters.BIG_NODE_LANES_KEY))
            sb.append(" -" + RoadGenerationParameters.BIG_NODE_LANES_KEY + " ").append(settings.get(RoadGenerationParameters.BIG_NODE_LANES_KEY));
        if (settings.containsKey(RoadGenerationParameters.BIG_NODE_EXTRA_ROADS_KEY))
            sb.append(" -" + RoadGenerationParameters.BIG_NODE_EXTRA_ROADS_KEY + " ").append(settings.get(RoadGenerationParameters.BIG_NODE_EXTRA_ROADS_KEY));
        if (settings.containsKey(RoadGenerationParameters.BUS_STOPS_MIN_KEY))
            sb.append(" -" + RoadGenerationParameters.BUS_STOPS_MIN_KEY + " ").append(settings.get(RoadGenerationParameters.BUS_STOPS_MIN_KEY));
        if (settings.containsKey(RoadGenerationParameters.BUS_STOPS_MAX_KEY))
            sb.append(" -" + RoadGenerationParameters.BUS_STOPS_MAX_KEY + " ").append(settings.get(RoadGenerationParameters.BUS_STOPS_MAX_KEY));

        return sb.toString();
    }
}
