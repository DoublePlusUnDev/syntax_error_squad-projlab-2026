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
        for (Lane lane : lanes){
            lane.addSnow(snowLevel);
        }
    }

    public void enter(Vehicle vehicle, int lane) {
        TestUtil.enterFunction("RoadSegment:enter");
        Lane selectedLane = lanes.get(lane);
        selectedLane.driveOver();
        vehicle.enter(selectedLane);
    }

    public void sweep(Lane lane){
        float snowLevel = lane.getSnow();
        lane.destroySnow();

        if (!isRightLane(lane)) {
            Lane laneToTheRight = lanes.get(lanes.indexOf(lane) + 1);
                laneToTheRight.addSnow(snowLevel);
        }

    }

    public void blow(Lane lane) {
        lane.destroySnow();
    }

    private boolean isRightLane(Lane lane){
        return lanes.getLast() == lane;
    }

    public void setStartPoint(Node startPoint) {
        this.startPoint = startPoint;
    }

    public void setEndPoint(Node endPoint) {
        this.endPoint = endPoint;
    }
}
