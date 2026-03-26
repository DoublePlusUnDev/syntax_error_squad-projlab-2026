public class BlowerHead extends PlowHead {

    @Override
    public void clean(Lane lane) {
        TestUtil.enterFunction("BlowerHead::clean(lane)");
    
        lane.blow();

        TestUtil.exitFunction("cleaned");
    }
    
}
