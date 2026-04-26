/**
 * An item that can be bought if the buyer's inventory has enough money to purchase it.
 */
public class Buyable {
    private int price;

    boolean buy(Inventory inventory, MoneyBank bank){
        return bank.payMoney(price);
    }
}
