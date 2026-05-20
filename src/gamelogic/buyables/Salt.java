package gamelogic.buyables;

import gamelogic.Inventory;
import gamelogic.Player;
import utils.Logger;

/**
 * A buyable item that will add salt it it's buyer's inventory.
 */
public class Salt extends Buyable {
    private int amount;

    public Salt(String id, int amount, int price) {
        super(id, price);
        this.amount = amount;
    }

    /**
     * Attempts to purchase the salt item.
     *
     * @param inventory The player's inventory.
     * @param bank The player's money bank.
     * @return true if the purchase is successful, false otherwise.
     */
    @Override
    public boolean buy(Inventory inventory, Player player) {
        
        if (!super.buy(inventory, player))
            return false;
        

        inventory.addSalt(amount);

        return true;   
    }

    @Override
    public void inspect() {
        Logger.logLine("Salt " + id + " details:");
        Logger.logLine("Price: " + price);
        Logger.logLine("Amount: " + amount);
    }
    
}
