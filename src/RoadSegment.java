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
            lanes.add(new Lane( id + ".lane" + (i + 1), this, i));
        }

        this.startPoint = startPoint;
        this.endPoint = endPoint;
        addNodeNeighbours();
    }

    // Getters and Setters

    /**
     * Gets the number of lanes in this road segment.
     *
     * @return the number of lanes
     */
    public int getLaneCount() {
        return lanes.size();
    }

    /**
     * Gets the list of lanes in this road segment.
     * @return the list of lanes
     */
    public List<Lane> getLanes() {
        return lanes;
    }

     /**
     * Gets the lane at the specified index.
     *
     * @param index the lane index
     * @return the lane at the specified index
     */

    /**
     * Gets the start node of this road segment.
     *
     * @return the start node
     */
    public Node getStartPoint() {
        return startPoint;
    }

    /**
     * Gets the end node of this road segment.
     *
     * @return the end node
     */
    public Node getEndPoint() {
        return endPoint;
    }

    /**
     * Sets the start point of this road segment.
     *
     * @param startPoint the new start node
     */
    public void setStartPoint(Node startPoint) {
        removeNodeNeighbours();
        this.startPoint = startPoint;
        addNodeNeighbours();
    }

    /**
     * Sets the end point of this road segment.
     *
     * @param endPoint the new end node
     */
    public void setEndPoint(Node endPoint) {
        removeNodeNeighbours();
        this.endPoint = endPoint;
        addNodeNeighbours();
    }

    // Public Operations

    /**
     * Adds snow to all lanes in this road segment.
     *
     * @param snowLevel the amount of snow to add
     */
    public void addSnow(float snowLevel) {
        for (Lane lane : lanes){
            lane.addSnow(snowLevel);
        }
    }

    /**
     * Allows a vehicle to enter this road segment on a specific lane.
     *
     * @param vehicle the vehicle entering
     * @param lane the lane index to enter
     */
    public void enter(Vehicle vehicle, int lane) {
        Lane selectedLane = lanes.get(lane);
        selectedLane.driveOver();
        vehicle.enter(selectedLane);
    }

    /**
     * Sweeps a lane, removing snow and pushing it to the right lane if not the rightmost.
     *
     * @param lane the lane to sweep
     */
    public void sweep(Lane lane){
        float snowLevel = lane.getSnow();
        lane.destroySnow();
        float gravelLevel = lane.getGravel();
        lane.destroyGravel();

        if (!isRightLane(lane)) {
            Lane laneToTheRight = lanes.get(lanes.indexOf(lane) + 1);
            laneToTheRight.addSnow(snowLevel);
            laneToTheRight.addGravel(gravelLevel);
        }
    }

    /**
     * Removes all snow from a lane by blowing it away.
     *
     * @param lane the lane to blow
     */
    public void blow(Lane lane) {
        lane.destroySnow();
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

    // Private Helpers

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

    protected boolean isRightLane(Lane lane){
        return lanes.get(lanes.size() - 1) == lane;
    }
}
