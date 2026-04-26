public class MoneyBank {
    private int money;

    public MoneyBank(int initialMoney) {
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
    
}
