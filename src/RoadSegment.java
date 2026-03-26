import java.util.ArrayList;
import java.util.List;

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
        TestUtil.enterFunction("RoadSegment: blow(lane)");
        lane.destroySnow();

        TestUtil.exitFunction("road blown");
    }

    public void blow(Lane lane) {
        
    }
}
