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
        TestUtil.enterFunction("Roadnetwork: tryMoveTowardsNode(vehicle, node)");
 
        RoadSegment segment = new RoadSegment(1, null, null);
        if (!vehicle.canEnter(segment.lanes.get(0))){
            TestUtil.exitFunction("Failed to find suitable lane to enter");
            return false;
        }


        if  (!TestUtil.askUserYesNo("Can the vehicle find a way toward its destination?"))
        {
            TestUtil.exitFunction("Failed to pathfind");
            return false;
        }
        RoadSegment chosenSegment = new RoadSegment(1, null, null);

        chosenSegment.enter(vehicle, 0);

        TestUtil.exitFunction("Move towards target succesfully");
        return true;
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
