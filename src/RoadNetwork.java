import java.util.ArrayList;
import java.util.List;

import jdk.jshell.spi.ExecutionControl.NotImplementedException;

public class RoadNetwork {
    private List<Node> nodes;
    private List<RoadSegment> roadSegments;

    public RoadNetwork(){
        nodes = new ArrayList<>();
        roadSegments = new ArrayList<>();
    }

    public boolean tryMoveTowardsNode(Vehicle vehicle) throws NotImplementedException {
        throw new NotImplementedException("");
    }

    public boolean canMoveVehicle(Vehicle vehicle) {
        System.out.println("Roadnetwork: canMoveVehicle");
        return TestUtil.askUserYesNo("Can the vehicle move?");
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

    public void addBus(Bus bus){

    }

    public void addSnowPlow(SnowPlow snowPlow) {
        
    }
}
