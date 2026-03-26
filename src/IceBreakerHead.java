public class IceBreakerHead extends PlowHead {

    @Override
    public void clean(Lane lane) {
        TestUtil.enterFunction("DragonHead:clean(lane)");
        lane.breakIce();
        TestUtil.exitFunction("ice broken");
    }
    
}
