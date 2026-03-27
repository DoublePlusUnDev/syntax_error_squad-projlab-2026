/**
 * A buyable item that will add salt it it's buyer's inventory.
 */
public class Salt extends Buyable {
    private int amount;

    @Override
    boolean buy(Inventory inventory) {

        TestUtil.enterFunction("Salt:buy(inventory)");
        
        if (!super.buy(inventory)){
            TestUtil.exitFunction("could not buy");
            return false;
        }

        inventory.addSalt(amount);
        TestUtil.exitFunction("bought");
        return true;
        
    }
    
}
