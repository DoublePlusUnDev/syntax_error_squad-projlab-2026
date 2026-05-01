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
    public String inspect() {
        StringBuilder output = new StringBuilder("Icebreakerhead " + id + " details:\n");
        output.append("Price: " + price + "\n");
        output.append("Equipped: " + equipped + "\n");
        output.append("Inventory: " + inventory.id + "\n");
        return output.toString();
    }
}
