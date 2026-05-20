package gamelogic;

import utils.Logger;

/**
 * A buyable item that will add gravel to it's buyer's inventory.
 */
public class Gravel extends Buyable {
    private int amount;

    public Gravel(String id, int amount, int price) {
        super(id, price);
        this.amount = amount;
    }

    @Override
    public boolean buy(Inventory inventory, Player player) {
        if (!super.buy(inventory, player))
            return false;

        inventory.addGravel(amount);
        return true;
    }

    @Override
    public void inspect() {
        Logger.logLine("Gravel " + id + " details:");
        Logger.logLine("Amount: " + amount);
    }
    
}
