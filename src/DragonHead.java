public class DragonHead extends PlowHead {

    @Override
    public void clean(Lane lane) {
        TestUtil.enterFunction("DragonHead:clean(lane)");
        lane.destroyIce();
        lane.destroySnow();
        TestUtil.exitFunction("road burned");
    }
    
}
