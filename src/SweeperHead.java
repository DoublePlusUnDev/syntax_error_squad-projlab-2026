/**
 * A sweeperhead for a snowplow.
 * Can be given a lane, from which it will sweep off the snow.
 */
public class SweeperHead extends PlowHead {

    @Override
    public void clean(Lane lane) {
        lane.sweep();
    }
    
    public SweeperHead(String id) {
        super(id);
    }

    @Override
    public void inspect() {
        Logger.logLine("Sweeperhead " + id + " details:");
        Logger.logLine("Price: " + price);
        Logger.logLine("Equipped: " + equipped);
        Logger.logLine("Inventory: " + inventory.id);
    }

}
