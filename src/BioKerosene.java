/**
 * A buyable item that will add biokerosene it it's buyer's inventory.
 */
public class BioKerosene extends Buyable {
    private int amount;

    @Override
    boolean buy(Inventory inventory, MoneyBank bank) {        
        if (!super.buy(inventory, bank))
            return false;
        

        inventory.addKerosene(amount);
        return true;

    }
    
}
