/**
 * A blowerhead for a snowplow.
 * Can be given a lane, from which it will blow off the snow.
 */
public class BlowerHead extends PlowHead {

    public BlowerHead(String id) {
        super(id);
    }

    @Override
    public void clean(Lane lane) {
        lane.blow();
    }

    @Override
    public void inspect() {
        Logger.logLine("Blowerhead " + id + " details:");
        Logger.logLine("Price: " + price);
        Logger.logLine("Equipped: " + equipped);
        Logger.logLine("Inventory: " + inventory.id);
    }
    
}
