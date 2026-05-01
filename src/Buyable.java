/**
 * An item that can be bought if the buyer's inventory has enough money to purchase it.
 */
public abstract class Buyable implements Inspectable {
    String id;
    protected int price;

    public Buyable(String id) {
        this.id = id;
    }

    boolean buy(Inventory inventory, MoneyBank bank){
        return bank.payMoney(price);
    }
}
