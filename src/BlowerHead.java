/**
 * A blowerhead for a snowplow.
 * Can be given a lane, from which it will blow off the snow.
 */
public class BlowerHead extends PlowHead {

    @Override
    public void clean(Lane lane) {
        lane.blow();
    }
    
}
