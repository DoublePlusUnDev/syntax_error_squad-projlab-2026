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

    /**
     * Attempts to buy this item for the given inventory and money bank.
     * @param inventory
     * @param bank
     * @return
     * 
     * If the buyer has enough money, the price will be deducted from the money bank and the method will return true.
     * Otherwise, the method will return false and no money will be deducted.
     */
    boolean buy(Inventory inventory, MoneyBank bank){
        if (bank.payMoney(price)) {
            Logger.logLine("BUYABLE [" + id + "] BOUGHT BY [" + inventory.id + "] FOR [" + price + "]");
            return true;
        }

        Logger.logLine("BUYABLE [" + id + "] COULD NOT BE BOUGHT BY [" + inventory.id + "] FOR [" + price + "]");
        return false;
    }
}
