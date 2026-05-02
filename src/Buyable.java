/**
 * An item that can be bought if the buyer's inventory has enough money to purchase it.
 */
public abstract class Buyable implements Inspectable {
    String id;
    protected int price;

    public Buyable(String id, int price) {
        this.id = id;
        this.price = price;
        ObjectRegistry.register(id, this);
    }

    boolean buy(Inventory inventory, MoneyBank bank){
        return bank.payMoney(price);
    }
}
