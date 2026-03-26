public class IceBreakerHead extends PlowHead {

    @Override
    public void clean(Lane lane) {
        TestUtil.enterFunction("DragonHead:clean(lane)");

        if (!inventory.tryConsumeKerosene()){
            TestUtil.exitFunction("failed");
            return;
        }

        lane.breakIce();
        TestUtil.exitFunction("ice broken");
    }
    
}
