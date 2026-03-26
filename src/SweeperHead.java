public class SweeperHead extends PlowHead {

    @Override
    public void clean(Lane lane) {
        TestUtil.enterFunction("SweeperHead:clean(lane)");
        lane.sweep();
        TestUtil.exitFunction("swept");
    }
    
}
