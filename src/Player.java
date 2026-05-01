/**
 * A base class for a player object.
 * Can be asked to take their turn.
 * They have an inventory, and can be paid money to.
 */
public abstract class Player implements Inspectable {
    String id;

    protected RoadNetwork roads;
    protected Inventory inventory;

    public Player(String id, RoadNetwork roads) {
        this.id = id;
        ObjectRegistry.register(id, this);
        this.roads = roads;
        inventory = new Inventory(id + ".inventory");
    }

    

    public void takeTurn() {
        
    }

    public void pay(int money) {
        
    }
    
}
