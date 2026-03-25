public class Bridge extends RoadSegment {

    public Bridge(int laneCount, Node startPoint, Node endPoint) {
        super(laneCount, startPoint, endPoint);
    }

    @Override
    public void sweep(Lane lane) {
        super.sweep(lane);
    }

    @Override
    public void blow(Lane lane) {
        super.blow(lane);
    }
}
