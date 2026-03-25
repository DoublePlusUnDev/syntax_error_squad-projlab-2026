import java.util.ArrayList;
import java.util.List;

public class RoadNetwork {
    private List<Node> nodes;
    private List<RoadSegment> roadSegments;

    public RoadNetwork(){
        nodes = new ArrayList<>();
        roadSegments = new ArrayList<>();
    }

    public boolean tryMoveTowardsNode(Vehicle vehicle, Node node) {
        System.out.println("Roadnetwork: tryMoveTowardsNode");
        return TestUtil.askUserYesNo("Can the vehicle move toward its destination?");
    }

    public void addSnow(){

    }

    public void generate() {

    }

    public void addNode(Node node){
        nodes.add(node);
    }

    public void addRoadSegment(RoadSegment roadSegment) {
        roadSegments.add(roadSegment);
    }

    public void placeBus(Bus bus){

    }

    public void placeBus(Bus bus, Lane lane){

    }

    public void placeSnowPlow(SnowPlow snowPlow) {
        
    }

    public void placeSnowPlow(SnowPlow snowPlow, Lane lane) {
        
    }
}
