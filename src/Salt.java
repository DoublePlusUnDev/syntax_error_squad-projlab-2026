/**
 * A buyable item that will add salt it it's buyer's inventory.
 */
public class Salt extends Buyable {
    private int amount;

    public Salt(String id, int amount) {
        super(id);
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
    public String inspect() {
        StringBuilder output = new StringBuilder("Salt " + id + " details:\n");
        output.append("Price: " + price + "\n");
        output.append("Amount: " + amount);
        return output.toString();
    }
    
}
