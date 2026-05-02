public class GravelThrowerHead extends PlowHead {

    public GravelThrowerHead(String id, int price) {
        super(id, price);
    }

    @Override
    public void clean(Lane lane) {
        lane.throwGravel();
    }
    
    @Override
    public void inspect() {
        Logger.logLine("Gravel Thrower Head " + id + " details:");
        Logger.logLine("Price: " + price);
        Logger.logLine("Equipped: " + equipped);
        Logger.logLine("Inventory: " + inventory.id);
    }
}
