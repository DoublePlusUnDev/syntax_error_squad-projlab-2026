/**
 * A salterhead for a snowplow.
 * Can be given a lane, which it will salt.
 * Requies snow in it's connecte inventory.
 */
public class SalterHead extends PlowHead {

    public SalterHead(String id) {
        super(id);
    }

    @Override
    public void clean(Lane lane) {
        if (!inventory.tryConsumeSalt())
            return;

        lane.salt();
    }

    @Override
    public String inspect() {
        StringBuilder output = new StringBuilder("Salterhead " + id + " details:\n");
        output.append("Price: " + price + "\n");
        output.append("Equipped: " + equipped + "\n");
        output.append("Inventory: " + inventory.id + "\n");
        return output.toString();
    }
}
