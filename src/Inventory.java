public class Inventory{
    private int money;

    

    public void pay(int amount) {
        TestUtil.enterFunction("Inventory:pay(amount)");
    
        TestUtil.exitFunction("payed");
    }

    public void addHead(PlowHead plowHead) {
        TestUtil.enterFunction("Inventory:addHead(plowHead)");

        TestUtil.exitFunction("head added");
    }
}