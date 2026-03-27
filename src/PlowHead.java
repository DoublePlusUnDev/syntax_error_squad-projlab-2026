/**
 * An abstract base for a plowhead.
 * Can be checked if it's equipped, can be equipped and unequipped.
 * When bough it'll place itself in the buyers inventory.
 */
public abstract class PlowHead extends Buyable{
    protected Inventory inventory; 
    private boolean equipped = false;

    /**
     * Clean a select lane using the plowhead.
     * @param lane 
     */
    public abstract void clean(Lane lane);

    @Override
    boolean buy(Inventory inventory) {
        TestUtil.enterFunction("plowHead:buy(inventory)");
        
        if (!super.buy(inventory)){
            TestUtil.exitFunction("could not buy");
            return false;
        }

        this.inventory = inventory;
        TestUtil.exitFunction("bought");
        return true;
    }

    /**
     * Equip the plowhead on a player, it'll mark it as equipped.
     */
    public void equip() {
        TestUtil.enterFunction("plowHead:equip()");
        equipped = true;
        TestUtil.exitFunction("equipped");
    }

    public void unequip() {
        TestUtil.enterFunction("plowHead:unequip()");
        equipped = false;
        TestUtil.exitFunction("unnequipped");
    }

    /**
     * Check if someone has already equipped the head.
     * @return If it's equipped.
     */
    public boolean isEquipped() {
        TestUtil.enterFunction("plowHead:isEquipped()");

        boolean input = TestUtil.askUserYesNo("Can the plowhead be equipped?");

        TestUtil.exitFunction(String.valueOf(input));
        return input;
    }
}
