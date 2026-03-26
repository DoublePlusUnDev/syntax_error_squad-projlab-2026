public class SalterHead extends PlowHead {

    @Override
    public void clean(Lane lane) {
        TestUtil.enterFunction("SalterHead:clean(lane)");
        lane.salt();
        TestUtil.exitFunction("salted");
    }
        
}
