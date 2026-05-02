/**
 * A buyable item that will add salt it it's buyer's inventory.
 */
public class Salt extends Buyable {
    private int amount;

    public Salt(String id, int amount, int price) {
        super(id, price);
        this.amount = amount;
    }

    @Override
    boolean buy(Inventory inventory, MoneyBank bank) {
        
        if (!super.buy(inventory, bank))
            return false;
        

        inventory.addSalt(amount);

        return true;   
    }

    @Override
    public void inspect() {
        Logger.logLine("Salt " + id + " details:");
        Logger.logLine("Price: " + price);
        Logger.logLine("Amount: " + amount);
    }
    
}
