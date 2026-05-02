/**
 * A buyable item that will add salt it it's buyer's inventory.
 */
public class DragonHead extends PlowHead {

    public DragonHead(String id, int price) {
        super(id, price);
    }

    @Override
    public void clean(Lane lane) {
        if (!inventory.tryConsumeKerosene())
            return;

        lane.destroyIce();
        lane.destroySnow();
    }

    @Override
    public void inspect() {
        Logger.logLine("Dragonhead " + id + " details:");
        Logger.logLine("Price: " + price);
        Logger.logLine("Equipped: " + equipped);
        Logger.logLine("Inventory: " + inventory.id);
    }
}
