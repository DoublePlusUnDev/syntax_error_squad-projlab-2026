package gamelogic;
/**
 * An abstract base for a plowhead.
 * Can be checked if it's equipped, can be equipped and unequipped.
 * When bough it'll place itself in the buyers inventory.
 */
public abstract class PlowHead extends Buyable{
    protected Inventory inventory; 
    protected boolean equipped = false;

    public PlowHead(String id, int price) {
        super(id, price);
    }

    /**
     * Clean a select lane using the plowhead.
     * @param lane 
     */
    public abstract void clean(Lane lane);

    @Override
    public boolean buy(Inventory inventory, Player player) {
        if (!super.buy(inventory, player))
            return false;

        this.inventory = inventory;
        inventory.addHead(this);
        return true;
    }

    /**
     * Equip the plowhead on a player, it'll mark it as equipped.
     */
    public void equip() {
        equipped = true;
    }

    public void unequip() {
        equipped = false;
    }

    /**
     * Check if someone has already equipped the head.
     * @return If it's equipped.
     */
    public boolean isEquipped() {
        return equipped;
    }
}
