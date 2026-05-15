package gamelogic;

import utils.ObjectRegistry;

/**
 * A base class for a player object.
 * Can be asked to take their turn.
 * They have an inventory, and can be paid money to.
 */
public abstract class Player implements Inspectable {
    public String id;

    protected RoadNetwork roads;
    protected MoneyBank moneyBank;

    public Player(String id, RoadNetwork roads) {
        this.id = id;
        ObjectRegistry.register(id, this);
        this.roads = roads;
        moneyBank = new MoneyBank(id + ".bank", 0);
    }

    

    public void takeTurn() {
        
    }

    public void pay(int money) {
        
    }
    
}
