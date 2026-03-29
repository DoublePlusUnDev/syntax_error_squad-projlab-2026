/**
 * An icebreaker head for a snowplow.
 * Can be given a lane, where it will brake the ice.
 */
public class IceBreakerHead extends PlowHead {

    @Override
    public void clean(Lane lane) {
        TestUtil.enterFunction("IceBreakerHead:clean(lane)");

        lane.breakIce();
        
        TestUtil.exitFunction("ice breaker head used");
    }
    
}
