/**
 * A buyable item that will add salt it it's buyer's inventory.
 */
public class DragonHead extends PlowHead {

    @Override
    public void clean(Lane lane) {
        TestUtil.enterFunction("DragonHead:clean(lane)");

        if (!inventory.tryConsumeKerosene()){
            TestUtil.exitFunction("failed");
            return;
        }

        lane.destroyIce();
        lane.destroySnow();
        TestUtil.exitFunction("road burned");
    }
    
}
