/**
 * A base class for a player object.
 * Can be asked to take their turn.
 * They have an inventory, and can be paid money to.
 */
public abstract  class Player {

    protected RoadNetwork roads;
    protected Inventory inventory;

    public Player(RoadNetwork roads) {
        this.roads = roads;
        inventory = new Inventory();
    }

    

    public void takeTurn() {
        
    }

    public void pay(int money) {
        
    }
    
}
