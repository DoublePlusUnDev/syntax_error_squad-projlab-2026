import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * A collections of different types of nodes, connected by road segments, which may have several lanes.
 * A vehicle can be given with a destination node, the network will attempt to find a way to move them towards it.
 * Lane change may be requested by vehicles.
 * Snow can be added to in a radius of a node.
 */
public class RoadNetwork implements Inspectable {
    String id;
    private RoadGenerationParameters generationParameters;

    private List<Node> nodes;
    private List<RoadSegment> roadSegments;

    public RoadNetwork(String id){
        this.id = id;
        ObjectRegistry.register(id, this);

        nodes = new ArrayList<>();
        roadSegments = new ArrayList<>();
    }

    /**
     * Does a Dijkstra search to find the best path towards the destination node through the road network 
     * And returns the next lane the vehicle should move to
     * @param vehicle
     * @param destination
     * @return
     */
    Lane findNextLaneInPath(Vehicle vehicle, Node destination){
        Map<Node, Float> distances = new HashMap<>();
        distances.put(vehicle.location.getSegment().startPoint, 0f);
        distances.put(vehicle.location.getSegment().endPoint, 0f);
        Map<Node, Node> previousNodes = new HashMap<>();
        previousNodes.put(vehicle.location.getSegment().startPoint, null);
        previousNodes.put(vehicle.location.getSegment().endPoint, null);

        PriorityQueue<Node> queue = new PriorityQueue<>((a, b) -> Float.compare(distances.get(a), distances.get(b)));
        queue.add(vehicle.location.getSegment().startPoint);
        queue.add(vehicle.location.getSegment().endPoint);

        while (!distances.containsKey(destination)){
            Node current = queue.poll();
            for (Neighbour neighbour : current.getNeighbours()){
                float alt = distances.get(current) + calculateLaneWeight(neighbour.getRoadSegment().lanes.get(0), vehicle);
                if (alt < distances.getOrDefault(neighbour.getNode(), Float.MAX_VALUE)) {
                    distances.put(neighbour.getNode(), alt);
                    previousNodes.put(neighbour.getNode(), current);
                    queue.add(neighbour.getNode());
                }
            }   
        }

        return null;
    }

    private float getClearestLane(RoadSegment segment, Vehicle vehicle) {
        float clearest = Float.MAX_VALUE;
        Lane bestLane = null;

        for (Lane lane : segment.lanes) {
            float weight = calculateLaneWeight(lane, vehicle);
            if (weight < clearest || bestLane == null) {
                clearest = weight;
                bestLane = lane;
            }
        }
        return clearest;
    }

    private float calculateLaneWeight(Lane lane, Vehicle vehicle) {
        if (!vehicle.canEnter(lane))
            return Float.MAX_VALUE; // Avoid lanes that the vehicle cannot enter

        float base = 1.0f;
        float snow = lane.getSnow() * 5f;
        float ice = lane.isIced() ? 2f : 0f;
        float icingProgress = lane.getIcingProgress() * 0.1f;
        float gravel = lane.getGravelHeight() * -4f;

        return base + snow + ice + icingProgress + gravel;
    }

    public boolean tryMoveTowardsNode(Vehicle vehicle, Node node) {
        Lane nextLane; //figure out later
 

        RoadSegment segment = new RoadSegment("segment1", 1, null, null);
        if (!vehicle.canEnter(segment.lanes.get(0))){

            return false;
        }


        if  (!TestUtil.askUserYesNo("Can the vehicle find a way toward its destination?"))
        {
            TestUtil.exitFunction("Failed to pathfind");
            return false;
        }

        int roadType = TestUtil.askUserNumberedOptions("What type of road the vehicle enters:", new String[]{"Road", "Bridge", "Tunnel"});
        
        RoadSegment chosenSegment;
        if (roadType == 1)
            chosenSegment = new RoadSegment("segment1", 1, null, null);
        else if (roadType == 2)
            chosenSegment = new Bridge("bridge1", 1, null, null);
        else
            chosenSegment = new Tunnel("tunnel1", 1, null, null);

        chosenSegment.enter(vehicle, 0);

        if (vehicle.canSlip() && chosenSegment.lanes.get(0).willSlip()){
            Lane newDestination = chosenSegment.lanes.get(0);

            boolean crash = TestUtil.askUserYesNo("Has the vehicle crashed into another vehicle as the result of the slip?");

            if (crash){
                vehicle.crash(newDestination);
                int crashedVehicle = TestUtil.askUserNumberedOptions("What type of vehicle it's crashed into?", new String[]{"SnowPlow", "Bus", "Car"});
            
                if (crashedVehicle == 1)
                    new SnowPlow("").crash(newDestination);
                else if (crashedVehicle == 2)
                    new Bus("").crash(newDestination);
                else
                    new Car("").crash(newDestination);
            }
            
        }
    
        //try to enter both neighbours
        for (int i = 0; i < 2; i++)
        {
            int neighboursNodes = TestUtil.askUserNumberedOptions("What kind of node is the lane next to?", new String[]{"Node", "Apartment", "Workplace", "Bus stop"});
            if (neighboursNodes == 1)
                new Node("").accept(vehicle);
            else if (neighboursNodes == 2)
                new Apartment("").accept(vehicle);
            else if (neighboursNodes == 3)
                new Workplace("").accept(vehicle);
            else 
                new BusStop("").accept(vehicle);
        }
        
        TestUtil.exitFunction("Move towards target succesfully");
        return true;
    }

    public boolean changeLane(Vehicle vehicle, Lane lane) {
        boolean result = vehicle.canEnter(lane);

        if (!result){
            return false;
        }

        vehicle.enter(lane);
        return true;
    }

    public void addSnow(){

    }

    

    public void addNode(Node node){
        nodes.add(node);
    }

    public void addRoadSegment(RoadSegment roadSegment) {
        roadSegments.add(roadSegment);
    }

    public void placeCar(Car car){

    }

    public void placeBus(Bus bus){

    }

    public void placeSnowPlow(SnowPlow snowPlow) {
        
    }

    public void generate() {
        nodes.clear();
        roadSegments.clear();

        //prevemt duplicate connections
        HashMap<Integer, Integer> nodeConnections = new HashMap<>();

        //Phase 1
        //generate a circle of interconnected nodes
        //make sure to generate the special nodes as well, but in random order, so they are not always in the same place
        //this is done by first creating a list of node types, then shuffling it, and then creating the nodes in the order of the shuffled list
        List<String> nodeTypes = new ArrayList<String>();
        int numberOfAllNodes = RandomGenerator.getRandomInt(generationParameters.nodeMin, generationParameters.nodeMax);
        
        int numberOfApartments = RandomGenerator.getRandomInt(generationParameters.apartsmentsMin, generationParameters.apartsmentsMax);
        int numberOfWorkplaces = RandomGenerator.getRandomInt(generationParameters.workPlacesMin, generationParameters.workPlacesMax);
        int numberOfBusStops = RandomGenerator.getRandomInt(generationParameters.busStopsMin, generationParameters.busStopsMax);
        int numberOfNodes = numberOfAllNodes - numberOfApartments - numberOfWorkplaces - numberOfBusStops;

        if (numberOfNodes < 0){
            System.out.println("Error: Too many special nodes for the total number of nodes. Please adjust the parameters.");
            return;
        }

        for (int i = 0; i < numberOfApartments; i++){
            nodeTypes.add("Apartment");
        }
        for (int i = 0; i < numberOfWorkplaces; i++){
            nodeTypes.add("Workplace");
        }
        for (int i = 0; i < numberOfBusStops; i++){
            nodeTypes.add("BusStop");
        }
        for (int i = 0; i < numberOfNodes; i++){
            nodeTypes.add("Node");
        }

        RandomGenerator.shuffleList(nodeTypes);
        
        for (int i = 0; i < numberOfNodes; i++){
            Node node;

            String nodeType = nodeTypes.get(i);
            if (nodeType.equals("Apartment"))
                node = new Apartment(id + "." + "apartment" + i);
            else if (nodeType.equals("Workplace"))
                node = new Workplace(id + "." + "workPlace" + i);
            else if (nodeType.equals("BusStop"))
                node = new BusStop(id + "." + "busStop" + i);
            else
                node = new Node(id + "." + "node" + i);

            nodes.add(node); 
            Node prevNode = i > 0 ? nodes.get(i-1) : null;

            RoadSegment segment = new RoadSegment("Mainroad" + i, generationParameters.mainLanes, prevNode, node);
            roadSegments.add(segment);
            if (prevNode != null){
                nodeConnections.put(i, i-1);
                nodeConnections.put(i-1, i);
            }
        }
        roadSegments.get(0).setStartPoint(nodes.getLast());
        nodeConnections.put(0, numberOfNodes - 1);
        nodeConnections.put(numberOfNodes - 1, 0);

        //phase 2
        /*List<Node> shuffleNodes = new ArrayList<>(nodes);
        RandomGenerator.shuffleList(shuffleNodes);
        
        int numberOfBigNodes = RandomGenerator.getRandomInt(generationParameters.bigNodesMin, generationParameters.bigNodesMax);
        List<Node> bigNodes = shuffleNodes.subList(0, numberOfBigNodes);
        
        for (Node bigNode : bigNodes){
            for (int i = 0; i < generationParameters.bigNodeExtraRoads; i++){
            }
        }

        int numberOfSmallNodes = RandomGenerator.getRandomInt(generationParameters.smallNodesMin, generationParameters.smallNodesMax);
        List<Node> smallNodes = shuffleNodes.subList(numberOfBigNodes, numberOfBigNodes + numberOfSmallNodes);
        */
        
    }

    public void setGenerationParameters(RoadGenerationParameters generationParameters){
        this.generationParameters = generationParameters;
    }

    @Override
    public void  inspect() {
        Logger.logLine("RoadNetwork " + id + "details:");
        Logger.logLine("Contains the following " + nodes.size() + " nodes:");
        for (Node node : nodes) {
            Logger.logLine("-" + node.id);
        }

        Logger.logLine("Contains the following " + roadSegments.size() + " road segments:");
        for (RoadSegment segment : roadSegments) {
            Logger.logLine("-" + segment.id);
        }
    }
}
