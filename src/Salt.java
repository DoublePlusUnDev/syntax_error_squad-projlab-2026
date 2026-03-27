/**
 * A buyable item that will add salt it it's buyer's inventory.
 */
public class Salt extends Buyable {
    private int amount;

    @Override
    boolean buy(Inventory inventory) {
        inventory.addSalt(amount);
        return super.buy(inventory);
    }
    
}
