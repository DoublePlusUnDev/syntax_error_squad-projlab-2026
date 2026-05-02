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
