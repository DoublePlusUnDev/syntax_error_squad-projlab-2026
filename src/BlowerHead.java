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
    public String inspect() {
        StringBuilder output = new StringBuilder("Blowerhead " + id + " details:\n");
        output.append("Price: " + price + "\n");
        output.append("Equipped: " + equipped + "\n");
        output.append("Inventory: " + inventory.id + "\n");
        return output.toString();
    }
    
}
