/**
 * A snowplow vehicle iontended to be controlled by the player.
 * When it enters a lane it will use it's plowhead to clean it.
 * Cannot slip on ice, cannot be damaged in a crash.
 * Can enter debris and snow filled lanes, unable to enter blocked lanes however. 
 */
public class SnowPlow extends Vehicle{
    private PlowHead plowHead;

    public SnowPlow(String id){
        super(id);
    }

    @Override
    public boolean canEnter(Lane lane) {

        if (lane.isBlocked()){
            return false;
        }

        return true;
    }

    @Override
    public void enter(Lane lane) {
        TestUtil.enterFunction("SnowPlow:enter(lane)");

        location = lane;

        if (plowHead != null) {
            plowHead.clean(lane);
        }
        TestUtil.exitFunction("lane entered and cleaned");
    }
    
    public void equip(PlowHead head) {
        TestUtil.enterFunction("SnowPlow:equip()");
        if (plowHead != null)
            plowHead.unequip();
        this.plowHead = head;
        plowHead.equip();
        TestUtil.exitFunction("equipped");
    }

    @Override
    public void inspect() {
        Logger.logLine("SnowPlow " + id + " details:");
        Logger.logLine("Location: " + (location != null ? location.id : "none"));
        Logger.logLine("PlowHead: " + (plowHead != null ? plowHead.id : "none"));
    }
}
