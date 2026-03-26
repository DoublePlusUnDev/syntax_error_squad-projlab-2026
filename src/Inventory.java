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

    public boolean tryConsumeKerosene() {
        TestUtil.enterFunction("Inventory:tryConsumeKerosene()");
    
        boolean canConsume = TestUtil.askUserYesNo("Is there enough kerosene?");

        if (!canConsume) {
            TestUtil.exitFunction("no kerosene to consume");
            return false;
        }

        TestUtil.exitFunction("kerosene consumed");
        return true;
    }

    public boolean tryConsumeSalt() {
        TestUtil.enterFunction("Inventory:tryConsumeSalt()");
    
        boolean canConsume = TestUtil.askUserYesNo("Is there enough salt?");

        if (!canConsume) {
            TestUtil.exitFunction("no salt to consume");
            return false;
        }

        TestUtil.exitFunction("salt consumed");
        return true;
    }

    public void addKerosene(int amount) {
        TestUtil.enterFunction("Inventory:addKerosene(amount)");
    
        TestUtil.exitFunction("kerosene added");
    }

    public void addSalt(int amount) {
        TestUtil.enterFunction("Inventory:addSalt(amount)");
    
        TestUtil.exitFunction("salt added");
    }
}