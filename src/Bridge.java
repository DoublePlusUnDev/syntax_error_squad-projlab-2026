/**
 * A bridge, a type of road which has railing so snow cannot be blown and swept from it.
 * You cannot sweep the snow off of the bridge from the rightmost lane, it will just stay there.
 * When a bridge is blown, the snow will move to the rightmost lane, if it's blown from the rightmost lane
 * it will stay unaffected.
 * 
 */
public class Bridge extends RoadSegment {

    public Bridge(int laneCount, Node startPoint, Node endPoint) {
        super(laneCount, startPoint, endPoint);
    }

    @Override
    public void sweep(Lane lane) {
        if (isRightLane(lane))
            return;
        

        Lane nextLane = lanes.getFirst();
        float snowLevel = lane.getSnow();
        lane.destroySnow();
        nextLane.addSnow(snowLevel);
    }

    @Override
    public void blow(Lane lane) {
        if (isRightLane(lane))
            return;
        

        Lane rightMostLane = lanes.getLast();
        float snowLevel = lane.getSnow();
        lane.destroySnow();
        rightMostLane.addSnow(snowLevel);
    }

    boolean isRightLane(Lane lane){
        return lanes.getLast() == lane;
    }
}
