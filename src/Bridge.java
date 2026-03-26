public class Bridge extends RoadSegment {

    public Bridge(int laneCount, Node startPoint, Node endPoint) {
        super(laneCount, startPoint, endPoint);
    }

    @Override
    public void sweep(Lane lane) {
        TestUtil.enterFunction("Bridge: sweep(lane)");

        boolean rightLane = TestUtil.askUserYesNo("Is the swept lane the rightmost one?");

        if (rightLane){
            TestUtil.exitFunction("bridge no effect");
            return;
        }

        Lane nextLane = lanes.get(0);
        float snowLevel = lane.getSnow();
        lane.destroySnow();
        nextLane.addSnow(snowLevel);

        TestUtil.exitFunction("bridge swept");
    }

    @Override
    public void blow(Lane lane) {
        TestUtil.enterFunction("Bridge: blow(lane)");

        boolean rightLane = TestUtil.askUserYesNo("Is the blown lane the rightmost one?");

        if (rightLane){
            TestUtil.exitFunction("bridge no effect");
            return;
        }

        Lane rightMostLane = lanes.get(lanes.size() - 1);
        float snowLevel = lane.getSnow();
        lane.destroySnow();
        rightMostLane.addSnow(snowLevel);

        
    
        TestUtil.exitFunction("bridge blown");
    }
}
