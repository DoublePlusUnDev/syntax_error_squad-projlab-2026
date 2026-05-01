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
    public String inspect() {
        StringBuilder output = new StringBuilder("Sweeperhead " + id + " details:\n");
        output.append("Price: " + price + "\n");
        output.append("Equipped: " + equipped + "\n");
        output.append("Inventory: " + inventory.id + "\n");
        return output.toString();
    }

    
}
