/**
 * An icebreaker head for a snowplow.
 * Can be given a lane, where it will brake the ice.
 */
public class IceBreakerHead extends PlowHead {

    public IceBreakerHead(String id) {
        super(id);
    }

    @Override
    public void clean(Lane lane) {
        lane.breakIce();
    }

    @Override
    public void inspect() {
        Logger.logLine("Icebreakerhead " + id + " details:");
        Logger.logLine("Price: " + price);
        Logger.logLine("Equipped: " + equipped);
        Logger.logLine("Inventory: " + inventory.id);
    }
}
