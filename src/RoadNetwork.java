import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

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
    private List<Vehicle> vehicles;

    public RoadNetwork(String id){
        this.id = id;
        ObjectRegistry.register(id, this);

        nodes = new ArrayList<>();
        roadSegments = new ArrayList<>();
        vehicles = new ArrayList<>();
    }

    public boolean tryMoveTowardsNode(Vehicle vehicle, Node node) {
        Lane nextLane = findNextLaneInPath(vehicle, node); //figure out later

        if (nextLane == null){
            Logger.logLine("VEHICLE [ " + vehicle.id + " ] FAILED TO MOVE TOWARDS NODE [" + node.id + "] - NO PATH FOUND");
            return false;
        }

        if (!vehicle.canEnter(nextLane))
            return false;
        

        nextLane.getSegment().enter(vehicle, nextLane.getCount());

        if (vehicle.canSlip() && nextLane.willSlip()){
            List<RoadSegment> potentialDestinations = new ArrayList<>();
            for (Neighbour neighbour : nextLane.getSegment().startPoint.getNeighbours()){
                if (neighbour.getRoadSegment() != nextLane.getSegment())
                    potentialDestinations.add(neighbour.getRoadSegment());
            }
            for (Neighbour neighbour : nextLane.getSegment().endPoint.getNeighbours()){
                if (neighbour.getRoadSegment() != nextLane.getSegment())
                    potentialDestinations.add(neighbour.getRoadSegment());
            }

            RoadSegment newDestination = potentialDestinations.get(RandomGenerator.getRandomInt(0, potentialDestinations.size() - 1));
            Lane destinationLane = newDestination.lanes.get(RandomGenerator.getRandomInt(0, newDestination.getLaneCount() - 1)); 
            newDestination.enter(vehicle, destinationLane.getCount()); // Slip into the first lane of the new road

            slip(vehicle, destinationLane);
            
            nextLane = destinationLane;

        }
    
        // Visit the nodes next to our final place
        nextLane.getSegment().startPoint.accept(vehicle);
        nextLane.getSegment().endPoint.accept(vehicle);
        Logger.logLine("VEHICLE [ " + vehicle.id + " ] SUCCESSFULLY MOVED TOWARDS NODE [" + node.id + "] THROUGH LANE [" + nextLane.id + "]");
        return true;
    } 

    public void slip(Vehicle vehicle, Lane lane) {
        Logger.logLine("VEHICLE [" + vehicle.id + "] SLIPPED ONTO LANE [" + lane.id + "]");
        boolean crashedIntoVehicle = false;
        for (Vehicle other : vehicles){
            if (other.location.getSegment() == lane.getSegment()){
                other.crash(lane);
                crashedIntoVehicle = true;
            }
        }

        if (crashedIntoVehicle){
            vehicle.crash(lane);
        }
    }

    /**
     * Does a Dijkstra search to find the best path towards the destination node through the road network 
     * And returns the next lane the vehicle should move to
     * @param vehicle
     * @param destination
     * @return
     */
    Lane findNextLaneInPath(Vehicle vehicle, Node destination){
        Node startNode = vehicle.location.getSegment().startPoint;
        Node endNode = vehicle.location.getSegment().endPoint;
        // Standard Dijkstra with a visited set and safe path reconstruction
        final float INF = Float.MAX_VALUE;
        Map<Node, Float> distances = new HashMap<>();
        Map<Node, Node> previousNodes = new HashMap<>();
        Set<Node> visited = new HashSet<>();

        // initialize
        distances.put(startNode, 0f);
        distances.put(endNode, 0f);
        previousNodes.put(startNode, null);
        previousNodes.put(endNode, null);

        PriorityQueue<Node> queue = new PriorityQueue<>((a, b) -> Float.compare(distances.getOrDefault(a, INF), distances.getOrDefault(b, INF)));
        queue.add(startNode);
        queue.add(endNode);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current == null) break;
            // skip already finalized nodes
            if (visited.contains(current)) continue;
            visited.add(current);

            if (current == destination) break; // reached target

            for (Neighbour neighbour : current.getNeighbours()){
                Node neighNode = neighbour.getNode();
                if (visited.contains(neighNode)) continue;

                Lane clearest = getClearestLane(neighbour.getRoadSegment(), vehicle);
                float edgeWeight = calculateLaneWeight(clearest, vehicle);
                if (edgeWeight == INF) continue; // cannot enter this road

                float alt = distances.getOrDefault(current, INF) + edgeWeight;
                if (alt < distances.getOrDefault(neighNode, INF)) {
                    distances.put(neighNode, alt);
                    previousNodes.put(neighNode, current);
                    queue.add(neighNode);
                }
            }
        }

        if (!distances.containsKey(destination) || distances.get(destination) == INF){
            return null; // No path found
        }

        // Reconstruct path from destination back to one of the start nodes
        List<Node> revPath = new ArrayList<>();
        Node cur = destination;
        while (cur != null){
            revPath.add(cur);
            cur = previousNodes.get(cur);
        }

        if (revPath.size() < 2) return null; // destination is the same as start

        // reverse to get forward path: first element will be a start node (startNode or endNode)
        Collections.reverse(revPath);
        Node pathStart = revPath.get(0);
        Node nextNode = revPath.get(1);

        // find the road segment connecting the start used to the next node
        for (Neighbour neighbour : pathStart.getNeighbours()){
            if (neighbour.getNode() == nextNode){
                return getClearestLane(neighbour.getRoadSegment(), vehicle);
            }
        }
        return null;
    }

    private Lane getClearestLane(RoadSegment segment, Vehicle vehicle) {
        float clearest = Float.MAX_VALUE;
        Lane bestLane = null;

        for (Lane lane : segment.lanes) {
            float weight = calculateLaneWeight(lane, vehicle);
            if (weight < clearest || bestLane == null) {
                clearest = weight;
                bestLane = lane;
            }
        }
        return bestLane;
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

    

    public boolean changeLane(Vehicle vehicle, int laneNum) {
        if (laneNum < 1 || laneNum > vehicle.location.getSegment().lanes.size()) {
            Logger.logError("Error: Lane number " + laneNum + " is out of bounds for this road.");
            return false;
        }
        Lane lane = vehicle.location.getSegment().lanes.get(laneNum - 1);

        boolean result = vehicle.canEnter(lane);

        if (!result){
            return false;
        }


        Logger.logLine("VEHICLE [ " + vehicle.id + " ] CHANGED LANE FROM [" + vehicle.location.id + "] TO [" + lane.id + "]");
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
        vehicles.add(car);
    }

    public void placeBus(Bus bus){
        vehicles.add(bus);
    }

    public void placeSnowPlow(SnowPlow snowPlow) {
        vehicles.add(snowPlow);
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

        Logger.logLine("Contains the following " + vehicles.size() + " vehicles:");
        for (Vehicle vehicle : vehicles) {
            Logger.logLine("-" + vehicle.id + " at lane " + vehicle.location.id);
        }
    }
}
