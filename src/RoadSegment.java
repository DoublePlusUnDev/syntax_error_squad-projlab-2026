import java.util.ArrayList;
import java.util.List;

/**
 * A roadsegment that connects two nodes in the roadnetwork.
 * May consist of several lanes.
 * A vehicle may enter one if it's lanes.
 * Can be swept and blown.
 */
public class RoadSegment implements Inspectable {
    String id;
    protected List<Lane> lanes;    
    protected Node startPoint;
    protected Node endPoint;

    public RoadSegment(String id, int laneCount, Node startPoint, Node endPoint) {
        this.id = id;
        ObjectRegistry.register(id, this);

        lanes = new ArrayList<>();
        for (int i = 0; i < laneCount; i++) {
            lanes.add(new Lane( id + ".lane" + (i + 1), this, laneCount));
        }

        this.startPoint = startPoint;
        this.endPoint = endPoint;
        if (startPoint != null && endPoint != null) {
            startPoint.addNeighbour(endPoint, this);
            endPoint.addNeighbour(startPoint, this);
        }
    }

    private void addNodeNeighbours(){
        if (startPoint != null && endPoint != null) {
            startPoint.addNeighbour(endPoint, this);
            endPoint.addNeighbour(startPoint, this);
        }
    }

    private void removeNodeNeighbours(){
        if (startPoint != null && endPoint != null) {
            startPoint.removeNeighbour(endPoint, this);
            endPoint.removeNeighbour(startPoint, this);
        }
    }

    public void addSnow(int snowLevel) {
        for (Lane lane : lanes){
            lane.addSnow(snowLevel);
        }
    }

    public void enter(Vehicle vehicle, int lane) {
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
        removeNodeNeighbours();
        this.startPoint = startPoint;
        addNodeNeighbours();
    }

    public void setEndPoint(Node endPoint) {
        removeNodeNeighbours();
        this.endPoint = endPoint;
        addNodeNeighbours();
    }

    public int getLaneCount() {
        return lanes.size();
    }

    @Override
    public void inspect() {
        Logger.logLine("RoadSegment " + id + " details:");
        Logger.logLine("Start Point: " + startPoint.id);
        Logger.logLine("End Point: " + endPoint.id);
        Logger.logLine("Lanes:");
        for (int i = 0; i < lanes.size(); i++) {
            Logger.logLine("  Lane " + i + ": " + lanes.get(i).id);
        }
    }
}
