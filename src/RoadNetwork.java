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

        int roadType = TestUtil.askUserNumberedOptions("What type of road the vehicle enters:", new String[]{"Road", "Bridge", "Tunnel"});
        
        RoadSegment chosenSegment;
        if (roadType == 1)
            chosenSegment = new RoadSegment(1, null, null);
        else if (roadType == 2)
            chosenSegment = new Bridge(1, null, null);
        else
            chosenSegment = new Tunnel(1, null, null);

        chosenSegment.enter(vehicle, 0);

        if (vehicle.canSlip() && chosenSegment.lanes.get(0).willSlip()){
            Lane newDestination = chosenSegment.lanes.get(0);

            boolean crash = TestUtil.askUserYesNo("Has the vehicle crashed into another vehicle as the result of the slip?");

            if (crash){
                vehicle.crash(newDestination);
                int crashedVehicle = TestUtil.askUserNumberedOptions("What type of vehicle it's crashed into?", new String[]{"SnowPlow", "Bus", "Car"});
            
                if (crashedVehicle == 1)
                    new SnowPlow().crash(newDestination);
                else if (crashedVehicle == 2)
                    new Bus().crash(newDestination);
                else
                    new Car().crash(newDestination);
            }

            vehicle.crash(newDestination);
        }
    
        //try to enter both neighbours
        for (int i = 0; i < 2; i++)
        {
            int neighboursNodes = TestUtil.askUserNumberedOptions("What kind of node is the lane next to?", new String[]{"Node", "Apartment", "Workplace", "Bus stop"});
            if (neighboursNodes == 1)
                new Node().accept(vehicle);
            else if (neighboursNodes == 2)
                new Apartment().accept(vehicle);
            else if (neighboursNodes == 3)
                new Workplace().accept(vehicle);
            else 
                new BusStop().accept(vehicle);
        }
        
        TestUtil.exitFunction("Move towards target succesfully");
        return true;
    }

    public boolean changeLane(Vehicle vehicle, Lane lane) {
        TestUtil.enterFunction("changeLane(vehicle, lane)");
        boolean result = vehicle.canEnter(lane);

        if (!result){
            TestUtil.exitFunction("false");
            return false;
        }

        vehicle.enter(lane);
        TestUtil.exitFunction("true");
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
