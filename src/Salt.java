/**
 * A buyable item that will add salt it it's buyer's inventory.
 */
public class Salt extends Buyable {
    private int amount;

    @Override
    boolean buy(Inventory inventory, MoneyBank bank) {
        
        if (!super.buy(inventory, bank))
            return false;
        

        inventory.addSalt(amount);

        return true;
        
    }
    
}
