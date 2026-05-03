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
    boolean buy(Inventory inventory, MoneyBank bank) {
        if (!super.buy(inventory, bank))
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
