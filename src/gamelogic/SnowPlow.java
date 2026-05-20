package gamelogic;

import utils.Logger;

/**
 * A snowplow vehicle iontended to be controlled by the player.
 * When it enters a lane it will use it's plowhead to clean it.
 * Cannot slip on ice, cannot be damaged in a crash.
 * Can enter debris and snow filled lanes, unable to enter blocked lanes however. 
 */
public class SnowPlow extends Vehicle{
    private PlowHead plowHead;
    private Inventory inventory;

    public SnowPlow(String id){
        super(id);
        this.inventory = new Inventory(id + ".inventory");
    }

    @Override
    public boolean canEnter(Lane lane) {

        if (lane.isOccupied() || lane.isBlocked()){
            return false;
        }

        return true;
    }

    @Override
    public void enter(Lane lane) {
        setLocation(lane);

        if (plowHead != null) 
            plowHead.clean(lane);
        
    }
    
    public void equip(PlowHead head) {
        if (plowHead != null)
            plowHead.unequip();
        this.plowHead = head;
        Logger.logLine("SNOWPLOW [" + id + "] EQUIPPED [" + head.id + "]");
        plowHead.equip();
    }

    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public boolean canSlip() {
        return false;
    }

    @Override
    public void inspect() {
        Logger.logLine("SnowPlow " + id + " details:");
        Logger.logLine("Location: " + (location != null ? location.id : "none"));
        Logger.logLine("PlowHead: " + (plowHead != null ? plowHead.id : "none"));
        Logger.logLine("Inventory: " + inventory.id);
    }
}
