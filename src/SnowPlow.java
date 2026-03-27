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

        super.enter(lane);

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
}
