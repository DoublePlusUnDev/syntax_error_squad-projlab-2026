public class Bridge extends RoadSegment {

    public Bridge(int laneCount, Node startPoint, Node endPoint) {
        super(laneCount, startPoint, endPoint);
    }

    @Override
    public void sweep(Lane lane) {
        

        

    }

    @Override
    public void blow(Lane lane) {
        TestUtil.enterFunction("Bridghe: blow(lane)");
        Lane rightMostLane = lanes.get(lanes.size() - 1);
        float snowLevel = lane.getSnow();
        lane.destroySnow();
        rightMostLane.addSnow(snowLevel);

        boolean sameLane = TestUtil.askUserYesNo("Is the blown lane the rightmost one?");
    
        TestUtil.exitFunction("bridge blown");
    }
}
