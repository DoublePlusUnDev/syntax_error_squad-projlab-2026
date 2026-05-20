package gamelogic;

import java.util.ArrayList;
import java.util.List;
import utils.Logger;
import utils.RandomGenerator;

public final class RoadGenerator {
    private static final String ROAD_TYPE_ROAD = "road";
    private static final String ROAD_TYPE_BRIDGE = "bridge";
    private static final String ROAD_TYPE_TUNNEL = "tunnel";

    private RoadGenerator() {
    }

    public static void generate(RoadNetwork roadNetwork, RoadGenerationParameters generationParameters) {
        if (roadNetwork == null || generationParameters == null) {
            Logger.logError("Error: Road generation failed because the road network or generation parameters were null.");
            return;
        }

        GenerationCounts counts = readCounts(generationParameters);
        if (counts == null) {
            return;
        }

        if (!validateCounts(counts)) {
            return;
        }

        roadNetwork.clearGeneratedData();

        Logger.logLine("Generating " + counts.numberOfAllNodes + " nodes in the road network.");
        List<String> nodeTypes = buildNodeTypes(counts);
        List<Node> nodes = createNodes(roadNetwork, nodeTypes);
        RoadTypeCounts roadTypeCounts = readRoadTypeCounts(generationParameters);

        if (roadTypeCounts == null) {
            return;
        }

        connectMainRoads(roadNetwork, generationParameters, nodes, roadTypeCounts);
        connectExtraRoads(roadNetwork, generationParameters, nodes, counts, roadTypeCounts);

        roadNetwork.fireTopologyChanged();
    }

    private static GenerationCounts readCounts(RoadGenerationParameters generationParameters) {
        try {
            return new GenerationCounts(
                RandomGenerator.getRandomInt((Integer) generationParameters.getParameter(RoadGenerationParameters.NODE_MIN_KEY), (Integer) generationParameters.getParameter(RoadGenerationParameters.NODE_MAX_KEY)),
                RandomGenerator.getRandomInt((Integer) generationParameters.getParameter(RoadGenerationParameters.APARTMENTS_MIN_KEY), (Integer) generationParameters.getParameter(RoadGenerationParameters.APARTMENTS_MAX_KEY)),
                RandomGenerator.getRandomInt((Integer) generationParameters.getParameter(RoadGenerationParameters.WORK_PLACES_MIN_KEY), (Integer) generationParameters.getParameter(RoadGenerationParameters.WORK_PLACES_MAX_KEY)),
                RandomGenerator.getRandomInt((Integer) generationParameters.getParameter(RoadGenerationParameters.BUS_STOPS_MIN_KEY), (Integer) generationParameters.getParameter(RoadGenerationParameters.BUS_STOPS_MAX_KEY)),
                RandomGenerator.getRandomInt((Integer) generationParameters.getParameter(RoadGenerationParameters.BIG_NODES_MIN_KEY), (Integer) generationParameters.getParameter(RoadGenerationParameters.BIG_NODES_MAX_KEY)),
                RandomGenerator.getRandomInt((Integer) generationParameters.getParameter(RoadGenerationParameters.SMALL_NODES_MIN_KEY), (Integer) generationParameters.getParameter(RoadGenerationParameters.SMALL_NODES_MAX_KEY))
            );
        } catch (RuntimeException exception) {
            Logger.logError("Error: Road generation failed because one of the generation parameters is invalid: " + exception.getMessage());
            return null;
        }
    }

    private static boolean validateCounts(GenerationCounts counts) {
        if (counts.numberOfAllNodes < 2) {
            Logger.logError("Error: Road generation requires at least 2 nodes.");
            return false;
        }

        counts.numberOfNormalNodes = counts.numberOfAllNodes - counts.numberOfApartments - counts.numberOfWorkplaces - counts.numberOfBusStops;

        if (counts.numberOfNormalNodes < 0) {
            Logger.logError("Error: Too many special nodes for the total number of nodes. Please adjust the parameters.");
            return false;
        }

        if (counts.numberOfBigNodes + counts.numberOfSmallNodes > counts.numberOfAllNodes) {
            Logger.logError("Error: Too many big and small nodes for the total number of nodes. Please adjust the parameters.");
            return false;
        }

        return true;
    }

    private static List<String> buildNodeTypes(GenerationCounts counts) {
        List<String> nodeTypes = new ArrayList<>();

        for (int i = 0; i < counts.numberOfApartments; i++) {
            nodeTypes.add("Apartment");
        }
        for (int i = 0; i < counts.numberOfWorkplaces; i++) {
            nodeTypes.add("Workplace");
        }
        for (int i = 0; i < counts.numberOfBusStops; i++) {
            nodeTypes.add("BusStop");
        }
        for (int i = 0; i < counts.numberOfNormalNodes; i++) {
            nodeTypes.add("Node");
        }

        return nodeTypes;
    }

    private static List<Node> createNodes(RoadNetwork roadNetwork, List<String> nodeTypes) {
        RandomGenerator.shuffleList(nodeTypes);

        List<Node> createdNodes = new ArrayList<>();

        for (int i = 0; i < nodeTypes.size(); i++) {
            String nodeType = nodeTypes.get(i);
            Node node = switch (nodeType) {
                case "Apartment" -> new Apartment(roadNetwork.id + "." + "apartment" + i);
                case "Workplace" -> new Workplace(roadNetwork.id + "." + "workPlace" + i);
                case "BusStop" -> new BusStop(roadNetwork.id + "." + "busStop" + i);
                default -> new Node(roadNetwork.id + "." + "node" + i);
            };

            roadNetwork.addNode(node);
            createdNodes.add(node);
        }

        return createdNodes;
    }

    private static RoadTypeCounts readRoadTypeCounts(RoadGenerationParameters generationParameters) {
        try {
            return new RoadTypeCounts(
                RandomGenerator.getRandomInt((Integer) generationParameters.getParameter(RoadGenerationParameters.BRIDGES_MIN_KEY), (Integer) generationParameters.getParameter(RoadGenerationParameters.BRIDGES_MAX_KEY)),
                RandomGenerator.getRandomInt((Integer) generationParameters.getParameter(RoadGenerationParameters.TUNNELS_MIN_KEY), (Integer) generationParameters.getParameter(RoadGenerationParameters.TUNNELS_MAX_KEY))
            );
        } catch (RuntimeException exception) {
            Logger.logError("Error: Road generation failed because one of the road type parameters is invalid: " + exception.getMessage());
            return null;
        }
    }

    private static void connectMainRoads(RoadNetwork roadNetwork, RoadGenerationParameters generationParameters, List<Node> nodes, RoadTypeCounts roadTypeCounts) {
        for (int i = 1; i < nodes.size(); i++) {
            Node a = nodes.get(i - 1);
            Node b = nodes.get(i);
            if (roadNetwork.canAddRoadBetween(a, b)) {
                addRoadSegment(roadNetwork, roadTypeCounts, (Integer) generationParameters.getParameter(RoadGenerationParameters.MAIN_LANES_KEY), a, b);
            }
        }

        if (nodes.size() >= 2) {
            Node last = nodes.get(nodes.size() - 1);
            Node first = nodes.get(0);
            if (roadNetwork.canAddRoadBetween(last, first)) {
                addRoadSegment(roadNetwork, roadTypeCounts, (Integer) generationParameters.getParameter(RoadGenerationParameters.MAIN_LANES_KEY), last, first);
            }
        }
    }

    private static void connectExtraRoads(RoadNetwork roadNetwork, RoadGenerationParameters generationParameters, List<Node> nodes, GenerationCounts counts, RoadTypeCounts roadTypeCounts) {
        List<Node> shuffleNodes = new ArrayList<>(nodes);
        RandomGenerator.shuffleList(shuffleNodes);

        addExtraConnections(roadNetwork, generationParameters, shuffleNodes.subList(0, counts.numberOfBigNodes), RoadGenerationParameters.BIG_NODE_LANES_KEY, RoadGenerationParameters.BIG_NODE_EXTRA_ROADS_KEY, roadTypeCounts);
        addExtraConnections(roadNetwork, generationParameters, shuffleNodes.subList(counts.numberOfBigNodes, counts.numberOfBigNodes + counts.numberOfSmallNodes), RoadGenerationParameters.SMALL_NODE_LANES_KEY, RoadGenerationParameters.SMALL_NODE_EXTRA_ROADS_KEY, roadTypeCounts);
    }

    private static void addExtraConnections(RoadNetwork roadNetwork, RoadGenerationParameters generationParameters, List<Node> sourceNodes, String laneCountKey, String maximumRoadsKey, RoadTypeCounts roadTypeCounts) {
        for (Node currentNode : sourceNodes) {
            List<Node> potentialConnections = new ArrayList<>(roadNetwork.getNodes());
            RandomGenerator.shuffleList(potentialConnections);

            int connectionsAdded = 0;
            for (Node potential : potentialConnections) {
                if (potential != currentNode && roadNetwork.canAddRoadBetween(currentNode, potential)) {
                    addRoadSegment(roadNetwork, roadTypeCounts, (Integer) generationParameters.getParameter(laneCountKey), currentNode, potential);
                    connectionsAdded++;

                    if (connectionsAdded >= (Integer) generationParameters.getParameter(maximumRoadsKey)) {
                        break;
                    }
                }
            }
        }
    }

    private static String buildRoadId(RoadNetwork roadNetwork, String type, Node startNode, Node endNode) {
        return roadNetwork.id + "." + type + "_" + trimNetworkPrefix(roadNetwork.id, startNode.id) + "_" + trimNetworkPrefix(roadNetwork.id, endNode.id);
    }

    private static void addRoadSegment(RoadNetwork roadNetwork, RoadTypeCounts roadTypeCounts, int laneCount, Node startNode, Node endNode) {
        String roadType = chooseRoadType(roadTypeCounts);
        String roadId = buildRoadId(roadNetwork, roadType, startNode, endNode);
        RoadSegment segment = createRoadSegment(roadType, roadId, laneCount, startNode, endNode);
        roadNetwork.addRoadSegment(segment);
    }

    private static String chooseRoadType(RoadTypeCounts roadTypeCounts) {
        List<String> availableTypes = new ArrayList<>();

        if (roadTypeCounts.bridgeCount > 0) {
            availableTypes.add(ROAD_TYPE_BRIDGE);
        }
        if (roadTypeCounts.tunnelCount > 0) {
            availableTypes.add(ROAD_TYPE_TUNNEL);
        }

        if (availableTypes.isEmpty()) {
            return ROAD_TYPE_ROAD;
        }

        String selectedType = availableTypes.get(RandomGenerator.getRandomInt(0, availableTypes.size() - 1));
        if (ROAD_TYPE_BRIDGE.equals(selectedType)) {
            roadTypeCounts.bridgeCount--;
        } else if (ROAD_TYPE_TUNNEL.equals(selectedType)) {
            roadTypeCounts.tunnelCount--;
        }

        return selectedType;
    }

    private static RoadSegment createRoadSegment(String roadType, String roadId, int laneCount, Node startNode, Node endNode) {
        return switch (roadType) {
            case ROAD_TYPE_BRIDGE -> new Bridge(roadId, laneCount, startNode, endNode);
            case ROAD_TYPE_TUNNEL -> new Tunnel(roadId, laneCount, startNode, endNode);
            default -> new RoadSegment(roadId, laneCount, startNode, endNode);
        };
    }

    private static String trimNetworkPrefix(String networkId, String objectId) {
        String prefix = networkId + ".";
        if (objectId.startsWith(prefix)) {
            return objectId.substring(prefix.length());
        }

        return objectId;
    }

    private static final class GenerationCounts {
        private final int numberOfAllNodes;
        private final int numberOfApartments;
        private final int numberOfWorkplaces;
        private final int numberOfBusStops;
        private final int numberOfBigNodes;
        private final int numberOfSmallNodes;
        private int numberOfNormalNodes;

        private GenerationCounts(int numberOfAllNodes, int numberOfApartments, int numberOfWorkplaces, int numberOfBusStops, int numberOfBigNodes, int numberOfSmallNodes) {
            this.numberOfAllNodes = numberOfAllNodes;
            this.numberOfApartments = numberOfApartments;
            this.numberOfWorkplaces = numberOfWorkplaces;
            this.numberOfBusStops = numberOfBusStops;
            this.numberOfBigNodes = numberOfBigNodes;
            this.numberOfSmallNodes = numberOfSmallNodes;
        }
    }

    private static final class RoadTypeCounts {
        private int bridgeCount;
        private int tunnelCount;

        private RoadTypeCounts(int bridgeCount, int tunnelCount) {
            this.bridgeCount = bridgeCount;
            this.tunnelCount = tunnelCount;
        }
    }
}