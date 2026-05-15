package gamelogic;

public class RoadGenerationParameters {
    public int nodeMin;
    public int nodeMax;
    public int mainLanes;
    public int smallNodesMin;
    public int smallNodesMax;
    public int smallNodeLanes;
    public int smallNodeExtraRoads;
    public int bigNodesMin;
    public int bigNodesMax;
    public int bigNodeLanes;
    public int bigNodeExtraRoads;
    public int busStopsMin;
    public int busStopsMax;
    public int workPlacesMin;
    public int workPlacesMax;
    public int apartsmentsMin;
    public int apartsmentsMax;

    public static RoadGenerationParameters testParams = new RoadGenerationParameters() {{
        nodeMin = 10;
        nodeMax = 20;
        mainLanes = 2;
        smallNodesMin = 2;
        smallNodesMax = 5;
        smallNodeLanes = 1;
        smallNodeExtraRoads = 1;
        bigNodesMin = 1;
        bigNodesMax = 3;
        bigNodeLanes = 3;
        bigNodeExtraRoads = 2;
        busStopsMin = 2;
        busStopsMax = 5;
        workPlacesMin = 2;
        workPlacesMax = 5;
        apartsmentsMin = 2;
        apartsmentsMax = 5;
     }};
}