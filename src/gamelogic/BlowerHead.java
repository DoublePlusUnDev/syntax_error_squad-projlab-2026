package gamelogic;

import utils.Logger;

/**
 * A blowerhead for a snowplow.
 * Can be given a lane, from which it will blow off the snow.
 */
public class BlowerHead extends PlowHead {

    public BlowerHead(String id, int price) {
        super(id, price);
    }

    @Override
    public void clean(Lane lane) {
        lane.blow();
    }

    @Override
    public void inspect() {
        Logger.logLine("Blower head " + id + " details:");
        Logger.logLine("Price: " + price);
        Logger.logLine("Equipped: " + equipped);
        Logger.logLine("Inventory: " + inventory.id);
    }
    
}
