package gamelogic.buyables;

import gamelogic.Inventory;
import gamelogic.Player;
import utils.Logger;

/**
 * A buyable item that will add biokerosene it it's buyer's inventory.
 */
public class BioKerosene extends Buyable{
    private final int amount;

    public BioKerosene(String id, int amount, int price) {
        super(id, price);
        this.amount = amount;
    }

    @Override
    public boolean buy(Inventory inventory, Player player) {        
        if (!super.buy(inventory, player))
            return false;
        

        inventory.addKerosene(amount);
        return true;

    }

    @Override
    public void inspect() {
        Logger.logLine("Kerosene " + id + " details:");
        Logger.logLine("Amount: " + amount);
    }
    
}
