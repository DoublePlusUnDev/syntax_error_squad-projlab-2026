public abstract class PlowHead extends Buyable{
    protected Inventory inventory; 

    public abstract void clean(Lane lane);

    @Override
    boolean buy(Inventory inventory) {
        if (!super.buy(inventory))
            return false;

        this.inventory = inventory;
        return true;
    }

    
}
