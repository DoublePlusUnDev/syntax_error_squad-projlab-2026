package gamelogic;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import utils.Logger;
import utils.ObjectRegistry;
import utils.RandomGenerator;

/**
 * A collections of different types of nodes, connected by road segments, which may have several lanes.
 * A vehicle can be given with a destination node, the network will attempt to find a way to move them towards it.
 * Lane change may be requested by vehicles.
 * Snow can be added to in a radius of a node.
 */
public class RoadNetwork implements Inspectable {
    private List<Runnable> topologyChangedCallback = new ArrayList<>();
    private List<Runnable> stateChangeCallback = new ArrayList<>();

    public String id;
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
            Logger.logLine("VEHICLE [" + vehicle.id + "] FAILED TO MOVE TOWARDS NODE [" + node.id + "] - NO PATH FOUND");
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
        Logger.logLine("VEHICLE [" + vehicle.id + "] SUCCESSFULLY MOVED TOWARDS NODE [" + node.id + "] THROUGH LANE [" + nextLane.id + "]");
        return true;
    } 

    public void slip(Vehicle vehicle, Lane lane) {
        Logger.logLine("VEHICLE [" + vehicle.id + "] SLIPPED ONTO LANE [" + lane.id + "]");
        Vehicle crashedVehicle = null;
        for (Vehicle other : vehicles){
            if (other.location.getSegment() == lane.getSegment()){
                crashedVehicle = other;
                break;
            }
        }

        if (crashedVehicle != null){
            crashedVehicle.crash(lane);
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

    public List<Node> getNodes() {
        return nodes;
    }

    public List<RoadSegment> getRoadSegments() {
        return roadSegments;
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


        Logger.logLine("VEHICLE [" + vehicle.id + "] CHANGED LANE FROM [" + vehicle.location.id + "] TO [" + lane.id + "]");
        vehicle.enter(lane);
        return true;
    }

    public void addSnow(){
        List<Node> selectedNodes = new ArrayList<>();
        for (Node node : nodes){
            if (RandomGenerator.decide(0.1f)) {
                selectedNodes.add(node);
            }
        }

        List<RoadSegment> affectedSegments = new ArrayList<>();
        for (Node node : selectedNodes){
            for (Neighbour neighbour : node.getNeighbours()){
                RoadSegment segment = neighbour.getRoadSegment();
                if (!affectedSegments.contains(segment)){
                    affectedSegments.add(segment);
                    segment.addSnow(0.02f);
                }
            }
        }
    }

    public void snow(int nodes){
        if (nodes <= 0 || this.nodes.isEmpty()) {
            return;
        }

        List<Node> availableNodes = new ArrayList<>(this.nodes);
        RandomGenerator.shuffleList(availableNodes);

        List<Node> selectedNodes = availableNodes.subList(0, Math.min(nodes, availableNodes.size()));
        Set<RoadSegment> affectedSegments = new HashSet<>();

        for (Node node : selectedNodes) {
            for (Neighbour neighbour : node.getNeighbours()) {
                affectedSegments.add(neighbour.getRoadSegment());
            }
        }

        for (RoadSegment segment : affectedSegments) {
            segment.addSnow(0.05f);
        }
    }

    public void addNode(Node node){
        nodes.add(node);
        topologyChangedCallback.forEach(Runnable::run);
    }

    public void addRoadSegment(RoadSegment roadSegment) {
        if (roadSegment == null) {
            Logger.logError("Error: Cannot add a null road segment.");
            return;
        }

        if (!canAddRoadBetween(roadSegment.getStartPoint(), roadSegment.getEndPoint())) {
            Logger.logError("Error: Road segment [" + roadSegment.id + "] duplicates an existing connection or has invalid endpoints.");
            return;
        }

        roadSegments.add(roadSegment);
        topologyChangedCallback.forEach(Runnable::run);
        roadSegment.addOnChangeListener(() -> stateChangeCallback.forEach(Runnable::run));
    }

    public boolean canAddRoadBetween(Node startPoint, Node endPoint) {
        if (startPoint == null || endPoint == null || startPoint == endPoint) {
            return false;
        }

        for (RoadSegment roadSegment : roadSegments) {
            if ((roadSegment.getStartPoint() == startPoint && roadSegment.getEndPoint() == endPoint)
                    || (roadSegment.getStartPoint() == endPoint && roadSegment.getEndPoint() == startPoint)) {
                return false;
            }
        }

        return true;
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

    public void removeCar(Car car){
        vehicles.remove(car);
    }

    public void generate() {
        int numberOfAllNodes;
        int numberOfApartments;
        int numberOfWorkplaces;
        int numberOfBusStops;
        int numberOfNormalNodes;
        int numberOfBigNodes;
        int numberOfSmallNodes;

        try {
            numberOfAllNodes = RandomGenerator.getRandomInt((Integer) generationParameters.getParameter(RoadGenerationParameters.NODE_MIN_KEY), (Integer) generationParameters.getParameter(RoadGenerationParameters.NODE_MAX_KEY));
            numberOfApartments = RandomGenerator.getRandomInt((Integer) generationParameters.getParameter(RoadGenerationParameters.APARTMENTS_MIN_KEY), (Integer) generationParameters.getParameter(RoadGenerationParameters.APARTMENTS_MAX_KEY));
            numberOfWorkplaces = RandomGenerator.getRandomInt((Integer) generationParameters.getParameter(RoadGenerationParameters.WORK_PLACES_MIN_KEY), (Integer) generationParameters.getParameter(RoadGenerationParameters.WORK_PLACES_MAX_KEY));
            numberOfBusStops = RandomGenerator.getRandomInt((Integer) generationParameters.getParameter(RoadGenerationParameters.BUS_STOPS_MIN_KEY), (Integer) generationParameters.getParameter(RoadGenerationParameters.BUS_STOPS_MAX_KEY));
            numberOfBigNodes = RandomGenerator.getRandomInt((Integer) generationParameters.getParameter(RoadGenerationParameters.BIG_NODES_MIN_KEY), (Integer) generationParameters.getParameter(RoadGenerationParameters.BIG_NODES_MAX_KEY));
            numberOfSmallNodes = RandomGenerator.getRandomInt((Integer) generationParameters.getParameter(RoadGenerationParameters.SMALL_NODES_MIN_KEY), (Integer) generationParameters.getParameter(RoadGenerationParameters.SMALL_NODES_MAX_KEY));
        } catch (RuntimeException exception) {
            Logger.logError("Error: Road generation failed because one of the generation parameters is invalid: " + exception.getMessage());
            return;
        }

        if (numberOfAllNodes < 2) {
            Logger.logError("Error: Road generation requires at least 2 nodes.");
            return;
        }

        numberOfNormalNodes = numberOfAllNodes - numberOfApartments - numberOfWorkplaces - numberOfBusStops;

        if (numberOfNormalNodes < 0){
            Logger.logError("Error: Too many special nodes for the total number of nodes. Please adjust the parameters.");
            return;
        }

        if (numberOfBigNodes + numberOfSmallNodes > numberOfAllNodes){
            Logger.logError("Error: Too many big and small nodes for the total number of nodes. Please adjust the parameters.");
            return;
        }

        nodes.clear();
        roadSegments.clear();
        vehicles.clear();

        //prevemt duplicate connections
        Map<Node, Set<Node>> nodeConnections = new HashMap<>();

        //Phase 1
        //calculate the number of nodes for each type and create a shuffled list of their types
        List<String> nodeTypes = new ArrayList<String>();
        Logger.logLine("Generating " + numberOfAllNodes + " nodes in the road network.");

        for (int i = 0; i < numberOfApartments; i++){
            nodeTypes.add("Apartment");
        }
        for (int i = 0; i < numberOfWorkplaces; i++){
            nodeTypes.add("Workplace");
        }
        for (int i = 0; i < numberOfBusStops; i++){
            nodeTypes.add("BusStop");
        }
        for (int i = 0; i < numberOfNormalNodes; i++){
            nodeTypes.add("Node");
        }

        RandomGenerator.shuffleList(nodeTypes);
        
        // Phase 2
        // Instantiate all nodes first (so addNode/addRoadSegment can be used safely), then connect them in a circle
        for (int i = 0; i < numberOfAllNodes; i++){
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

            addNode(node);
        }

        // connect consecutive nodes
        for (int i = 1; i < nodes.size(); i++) {
            Node a = nodes.get(i - 1);
            Node b = nodes.get(i);
            if (canAddRoadBetween(a, b)){
                RoadSegment segment = new RoadSegment("Mainroad" + i, (Integer) generationParameters.getParameter(RoadGenerationParameters.MAIN_LANES_KEY), a, b);
                addRoadSegment(segment);
                nodeConnections.computeIfAbsent(a, k -> new HashSet<>()).add(b);
                nodeConnections.computeIfAbsent(b, k -> new HashSet<>()).add(a);
            }
        }

        // connect last -> first to close the ring
        if (nodes.size() >= 2) {
            Node last = nodes.get(nodes.size() - 1);
            Node first = nodes.get(0);
            if (canAddRoadBetween(last, first)){
                RoadSegment segment0 = new RoadSegment("Mainroad0", (Integer) generationParameters.getParameter(RoadGenerationParameters.MAIN_LANES_KEY), last, first);
                addRoadSegment(segment0);
                nodeConnections.computeIfAbsent(last, k -> new HashSet<>()).add(first);
                nodeConnections.computeIfAbsent(first, k -> new HashSet<>()).add(last);
            }
        }

        //phase 3
        //Add extra connections to big nodes and small nodes
        List<Node> shuffleNodes = new ArrayList<>(nodes);
        RandomGenerator.shuffleList(shuffleNodes);

        List<Node> bigNodes = shuffleNodes.subList(0, numberOfBigNodes);
        
        for (Node currentBigNode : bigNodes){
            List<Node> potentialConnections = new ArrayList<>(nodes);
            RandomGenerator.shuffleList(potentialConnections);

            int connectionsAdded = 0;
            for (Node potential : potentialConnections){
                if (potential == currentBigNode) continue;

                if (!canAddRoadBetween(currentBigNode, potential)) continue;

                RoadSegment segment = new RoadSegment("BigNodeExtraRoad" + currentBigNode.id + "_" + potential.id, (Integer) generationParameters.getParameter(RoadGenerationParameters.BIG_NODE_LANES_KEY), currentBigNode, potential);
                addRoadSegment(segment);
                nodeConnections.computeIfAbsent(currentBigNode, k -> new HashSet<>()).add(potential);
                nodeConnections.computeIfAbsent(potential, k -> new HashSet<>()).add(currentBigNode);
                connectionsAdded++;

                if (connectionsAdded >= (Integer) generationParameters.getParameter(RoadGenerationParameters.BIG_NODE_EXTRA_ROADS_KEY)) break;
            }
            
        }

        
        List<Node> smallNodes = shuffleNodes.subList(numberOfBigNodes, numberOfBigNodes + numberOfSmallNodes);
        
        for (Node currentSmallNode : smallNodes){
            List<Node> potentialConnections = new ArrayList<>(nodes);
            RandomGenerator.shuffleList(potentialConnections);

            int connectionsAdded = 0;
            for (Node potential : potentialConnections){
                if (potential == currentSmallNode) continue;

                if (!canAddRoadBetween(currentSmallNode, potential)) continue;

                RoadSegment segment = new RoadSegment("SmallNodeExtraRoad" + currentSmallNode.id + "_" + potential.id, (Integer) generationParameters.getParameter(RoadGenerationParameters.SMALL_NODE_LANES_KEY), currentSmallNode, potential);
                addRoadSegment(segment);
                nodeConnections.computeIfAbsent(currentSmallNode, k -> new HashSet<>()).add(potential);
                nodeConnections.computeIfAbsent(potential, k -> new HashSet<>()).add(currentSmallNode);
                connectionsAdded++;

                if (connectionsAdded >= (Integer) generationParameters.getParameter(RoadGenerationParameters.SMALL_NODE_EXTRA_ROADS_KEY)) break;
            }
            
        }

        topologyChangedCallback.forEach(Runnable::run);
        
    }

    public void setGenerationParameters(RoadGenerationParameters generationParameters){
        this.generationParameters = generationParameters;
    }

    public void addTopologyChangedListener(Runnable listener) {
        if (topologyChangedCallback.contains(listener)) 
            return;
        
        topologyChangedCallback.add(listener);
    }

    public void addStateChangeListener(Runnable listener) {
        if (stateChangeCallback.contains(listener)) 
            return;
        
        stateChangeCallback.add(listener);
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
