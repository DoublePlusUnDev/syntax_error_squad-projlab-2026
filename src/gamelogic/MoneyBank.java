package gamelogic;

import utils.Logger;
import utils.ObjectRegistry;

/**
 * Represents a money bank in the game, which can hold a certain amount of money.
 * Players can use the money in the bank to buy items, and the bank can be inspected to see how much money it currently holds.
 */
public class MoneyBank implements Inspectable {
    String id;
    private int money;

    public MoneyBank(String id, int initialMoney) {
        this.id = id;
        ObjectRegistry.register(id, this);
        this.money = initialMoney;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int amount) {
        this.money = amount;
    }

    public void addMoney(int amount) {
        money += amount;
    }

    public boolean payMoney(int amount) {
        if (money >= amount) {
            money -= amount;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void inspect() {
        Logger.logLine("MoneyBank " + id + " details:");
        Logger.logLine("Money: " + money);
    }
}
