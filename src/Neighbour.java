/**
 * Represents a neighboring node and the road segment connecting to it.
 * This class is used to store information about the neighbors of a node in the graph.
 */
public class Neighbour {
    private Node node;
    private RoadSegment roadSegment;

    public Neighbour(Node node, RoadSegment roadSegment) {
        this.node = node;
        this.roadSegment = roadSegment;
    }

    public Node getNode() {
        return node;
    }

    public RoadSegment getRoadSegment() {
        return roadSegment;
    }
    
}
