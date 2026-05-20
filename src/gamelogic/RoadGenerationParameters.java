package gamelogic;

import java.util.HashMap;
import java.util.Map;

public class RoadGenerationParameters {
    private Map<String, Object> settings = new HashMap<>();
    public static final String NODE_MIN_KEY = "nodeMin";
    public static final String NODE_MAX_KEY = "nodeMax";
    public static final String MAIN_LANES_KEY = "mainLanes";
    public static final String SMALL_NODES_MIN_KEY = "smallNodesMin";
    public static final String SMALL_NODES_MAX_KEY = "smallNodesMax";
    public static final String SMALL_NODE_LANES_KEY = "smallNodeLanes";
    public static final String SMALL_NODE_EXTRA_ROADS_KEY = "smallNodeExtraRoads";
    public static final String BIG_NODES_MIN_KEY = "bigNodesMin";
    public static final String BIG_NODES_MAX_KEY = "bigNodesMax";
    public static final String BIG_NODE_LANES_KEY = "bigNodeLanes";
    public static final String BIG_NODE_EXTRA_ROADS_KEY = "bigNodeExtraRoads";
    public static final String BUS_STOPS_MIN_KEY = "busStopsMin";
    public static final String BUS_STOPS_MAX_KEY = "busStopsMax";
    public static final String WORK_PLACES_MIN_KEY = "workPlacesMin";
    public static final String WORK_PLACES_MAX_KEY = "workPlacesMax";
    public static final String APARTMENTS_MIN_KEY = "apartmentsMin";
    public static final String APARTMENTS_MAX_KEY = "apartmentsMax";
    public static final String BRIDGES_MIN_KEY = "bridgesMin";
    public static final String BRIDGES_MAX_KEY = "bridgesMax";
    public static final String TUNNELS_MIN_KEY = "tunnelsMin";
    public static final String TUNNELS_MAX_KEY = "tunnelsMax";

    public void setParameter(String key, Object value) {
        settings.put(key, value);
    }

    public Object getParameter(String key) {
        return settings.get(key);
    }

    public static final RoadGenerationParameters defaultParams = new RoadGenerationParameters() {{
        setParameter(NODE_MIN_KEY, 10);
        setParameter(NODE_MAX_KEY, 15);
        setParameter(MAIN_LANES_KEY, 2);
        setParameter(SMALL_NODES_MIN_KEY, 2);
        setParameter(SMALL_NODES_MAX_KEY, 5);
        setParameter(SMALL_NODE_LANES_KEY, 1);
        setParameter(SMALL_NODE_EXTRA_ROADS_KEY, 1);
        setParameter(BIG_NODES_MIN_KEY, 1);
        setParameter(BIG_NODES_MAX_KEY, 3);
        setParameter(BIG_NODE_LANES_KEY, 3);
        setParameter(BIG_NODE_EXTRA_ROADS_KEY, 2);
        setParameter(BUS_STOPS_MIN_KEY, 2);
        setParameter(BUS_STOPS_MAX_KEY, 3);
        setParameter(WORK_PLACES_MIN_KEY, 2);
        setParameter(WORK_PLACES_MAX_KEY, 3);
        setParameter(APARTMENTS_MIN_KEY, 2);
        setParameter(APARTMENTS_MAX_KEY, 3);
        setParameter(BRIDGES_MIN_KEY, 2);
        setParameter(BRIDGES_MAX_KEY, 3);
        setParameter(TUNNELS_MIN_KEY, 2);
        setParameter(TUNNELS_MAX_KEY, 3);
     }};
}