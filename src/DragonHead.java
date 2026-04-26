/**
 * A buyable item that will add salt it it's buyer's inventory.
 */
public class DragonHead extends PlowHead {

    @Override
    public void clean(Lane lane) {
        if (!inventory.tryConsumeKerosene())
            return;

        lane.destroyIce();
        lane.destroySnow();
    }
    
}
