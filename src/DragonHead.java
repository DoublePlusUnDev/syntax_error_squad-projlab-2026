/**
 * A buyable item that will add salt it it's buyer's inventory.
 */
public class DragonHead extends PlowHead {

    public DragonHead() {
        super(null);
    }

    @Override
    public void clean(Lane lane) {
        if (!inventory.tryConsumeKerosene())
            return;

        lane.destroyIce();
        lane.destroySnow();
    }

    @Override
    public String inspect() {
        StringBuilder output = new StringBuilder("Dragonhead " + id + " details:\n");
        output.append("Price: " + price + "\n");
        output.append("Equipped: " + equipped + "\n");
        output.append("Inventory: " + inventory.id + "\n");
        return output.toString();
    }
}
