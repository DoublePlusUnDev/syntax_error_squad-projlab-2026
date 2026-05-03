/**
 * A buyable item that will add salt it it's buyer's inventory.
 */
public class DragonHead extends PlowHead {

    public DragonHead(String id, int price) {
        super(id, price);
    }

    /**
     * Cleans the given lane by destroying ice and snow.
     * @param lane the lane to clean
     * 
     * If the inventory does not have enough kerosene, the lane will not be cleaned.
     */
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
