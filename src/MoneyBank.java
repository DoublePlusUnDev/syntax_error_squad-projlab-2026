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
    public String inspect() {
        StringBuilder output = new StringBuilder("MoneyBank " + id + " details:\n");
        output.append("Money: " + money + "\n");
        return output.toString();
    }
}
