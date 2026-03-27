/**
 * A buyable item that will add biokerosene it it's buyer's inventory.
 */
public class BioKerosene extends Buyable {
    private int amount;

    @Override
    boolean buy(Inventory inventory) {
        TestUtil.enterFunction("BioKerosene:buy(inventory)");
        
        if (!super.buy(inventory)){
            TestUtil.exitFunction("could not buy");
            return false;
        }

        inventory.addKerosene(amount);
        TestUtil.exitFunction("bought");
        return true;

    }
    
}
