/**
 * A salterhead for a snowplow.
 * Can be given a lane, which it will salt.
 * Requies snow in it's connecte inventory.
 */
public class SalterHead extends PlowHead {

    @Override
    public void clean(Lane lane) {
        if (!inventory.tryConsumeSalt())
            return;

        lane.salt();
    }
        
}
