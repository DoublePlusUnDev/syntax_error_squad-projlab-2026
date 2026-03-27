import java.util.ArrayList;
import java.util.List;

/**
 * A roadsegment that connects two nodes in the roadnetwork.
 * May consist of several lanes.
 * A vehicle may enter one if it's lanes.
 * Can be swept and blown.
 */
public class RoadSegment {
    protected List<Lane> lanes;    
    private Node startPoint;
    private Node endPoint;

    public RoadSegment(int laneCount, Node startPoint, Node endPoint) {
        lanes = new ArrayList<>();
        for (int i = 0; i < laneCount; i++) {
            lanes.add(new Lane(this));
        }

        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public void addSnow(int snowLevel) {

    }

    public void enter(Vehicle vehicle, int lane) {
        TestUtil.enterFunction("RoadSegment:enter");
        Lane selectedLane = lanes.get(lane);
        selectedLane.driveOver();
        vehicle.enter(selectedLane);
    }

    public void sweep(Lane lane){
        TestUtil.enterFunction("RoadSegment:sweep(lane)");
        
        float snowLevel = lane.getSnow();
        lane.destroySnow();

        Lane laneToTheRight = new Lane(lane.getSegment());
        laneToTheRight.addSnow(snowLevel);

        TestUtil.exitFunction("road swept");
    }

    public void blow(Lane lane) {
        TestUtil.enterFunction("RoadSegment:blow(lane)");

        lane.destroySnow();

        TestUtil.exitFunction("lane blown");
    }
}
