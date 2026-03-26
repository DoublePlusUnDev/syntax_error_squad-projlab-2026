public class SnowPlow extends Vehicle{

    private PlowHead plowHead;

    @Override
    public boolean canEnter(Lane lane) {
        TestUtil.enterFunction("SnowPlow:canEnter(lane)");

        if (lane.isBlocked()){
            TestUtil.exitFunction("cant enter lane is blocked");
            return false;
        }

        TestUtil.exitFunction("lane can be entered");
        return true;
    }

    @Override
    public void enter(Lane lane) {
        TestUtil.enterFunction("SnowPlow:enter(lane)");
        plowHead.clean(lane);
        TestUtil.exitFunction("lane entered and cleaned");
    }
    
    public void equip(PlowHead head) {
        TestUtil.enterFunction("SnowPlow:equip()");
        this.plowHead = head;
        TestUtil.exitFunction("equipped");
    }
}
