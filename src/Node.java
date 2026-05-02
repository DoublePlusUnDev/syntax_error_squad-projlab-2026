
import java.util.ArrayList;
import java.util.List;

/**
 * A node in the road network. When a vehicle arrives on a road segment, 
 * the vehicle will enter the two neighbouring nodes. 
 */
public class Node implements Inspectable{
    String id;

    private final List<Neighbour> neighbours;

    public Node(String id) {
        this.id = id;
        ObjectRegistry.register(id, this);
        this.neighbours = new ArrayList<>();
    }

    public void accept(Vehicle vehicle) {
        
    }

    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    public void addNeighbour(Node node, RoadSegment roadSegment) {
        Neighbour neighbour = new Neighbour(node, roadSegment);
        if (!neighbours.contains(neighbour)) {
            neighbours.add(neighbour);
        }
    }

    public void removeNeighbour(Node node, RoadSegment roadSegment) {
        Neighbour neighbour = new Neighbour(node, roadSegment);
        neighbours.remove(neighbour);
    }

    @Override
    public void inspect() {
        Logger.logLine("Node " + id + "details:");
        Logger.logLine("Neighbour count: " + neighbours.size());
        for (Neighbour neighbour : neighbours) {
            Logger.logLine("Neighbour: " + neighbour.getNode().id + " via " + neighbour.getRoadSegment().id);
        }
    }
}
