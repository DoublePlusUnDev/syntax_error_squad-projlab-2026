public class SalterHead extends PlowHead {

    @Override
    public void clean(Lane lane) {
        TestUtil.enterFunction("SalterHead:clean(lane)");

        if (!inventory.tryConsumeSalt()){
            TestUtil.exitFunction("failed");
            return;
        }

        TestUtil.exitFunction("salted");
    }
        
}
