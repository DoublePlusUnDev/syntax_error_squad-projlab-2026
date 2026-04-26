/**
 * A sweeperhead for a snowplow.
 * Can be given a lane, from which it will sweep off the snow.
 */
public class SweeperHead extends PlowHead {

    @Override
    public void clean(Lane lane) {
        lane.sweep();
    }
    
}
